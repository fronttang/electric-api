/**
 * 
 */
package com.rosenzest.server.base.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.constant.SystemConstants;
import com.rosenzest.base.enums.TerminalType;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.RedisUtil;
import com.rosenzest.server.base.cache.CacheKeyBuilder;
import com.rosenzest.server.base.properties.TokenProperties;
import com.rosenzest.server.base.service.AuthService;
import com.rosenzest.server.base.util.HttpServletUtil;
import com.rosenzest.server.base.util.JwtTokenUtil;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author fronttang
 */
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private TokenProperties tokenProperties;

	@Override
	public String getTokenFromRequestHeader(HttpServletRequest request) {
		String authToken = request.getHeader(tokenProperties.getHeader());
		if (StrUtil.isBlank(authToken)) {
			return null;
		}
		return authToken;
		// return
		// "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpblR5cGUiOiIyIiwidXNlcklkIjoiMTM1NjQ0MjM1NjYiLCJ1dWlkIjoiMjI3MTc5MTIwNTk2NzUwMzM2Iiwic3ViIjoiMTM1NjQ0MjM1NjYiLCJpYXQiOjE2MjM5MjUwNDcsImV4cCI6MTYyNDAxMTQ0N30.VGcb2WpukzSmdK93VoPq4SwnO3aJL_WHuroaKMD1-ck5wp_AGX0u8xc-QBCQfRC434WCnd-YUP7p9ns7O4W2HQ";
	}

	@Override
	public String getTokenFromRequestAttr(HttpServletRequest request) {
		String authToken = (String) request.getAttribute(SystemConstants.REQUEST_TOKEN);
		if (StrUtil.isBlank(authToken)) {
			return null;
		}
		return authToken;
		// return
		// "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpblR5cGUiOiIyIiwidXNlcklkIjoiMTM1NjQ0MjM1NjYiLCJ1dWlkIjoiMjI3MTc5MTIwNTk2NzUwMzM2Iiwic3ViIjoiMTM1NjQ0MjM1NjYiLCJpYXQiOjE2MjM5MjUwNDcsImV4cCI6MTYyNDAxMTQ0N30.VGcb2WpukzSmdK93VoPq4SwnO3aJL_WHuroaKMD1-ck5wp_AGX0u8xc-QBCQfRC434WCnd-YUP7p9ns7O4W2HQ";
	}

	@Override
	public LoginUser getLoginUserByToken(String token) {
		// 校验token，错误则抛异常
		this.checkToken(token);

		LoginUser loginUser = JwtTokenUtil.getJwtPayLoad(token, LoginUser.class);
		if (loginUser == null) {
			throw new BusinessException(ResultEnum.FORBIDDEN);
		}

		String tokenKey = getTokenKey(loginUser);
		// redis检查当前token是否有效
		String cacheToken = RedisUtil.get(tokenKey);
		if (cacheToken == null) {
			throw new BusinessException(ResultEnum.UNAUTHOZIED);
		}
		if (!String.valueOf(cacheToken).equals(token)) {
			throw new BusinessException(ResultEnum.UNAUTHOZIED);
		}
		return loginUser;
	}

	private String getTokenKey(LoginUser loginUser) {

		// 做了兼容
		TerminalType terminal = loginUser.getTerminal();
		String uuid = loginUser.getUuid();

		if (terminal != null) {
			return CacheKeyBuilder.getCustTokenKey(terminal.code(), uuid);
		}
		return CacheKeyBuilder.getCustTokenKey(uuid);
	}

	@Override
	public void logout() {
		HttpServletRequest request = HttpServletUtil.getRequest();

		if (ObjectUtil.isNotNull(request)) {

			// 获取token
			String token = this.getTokenFromRequestHeader(request);

			// 如果token为空直接返回
			if (ObjectUtil.isEmpty(token)) {
				return;
			}

			// 校验token，错误则抛异常，待确定
			this.checkToken(token);

			// 根据token获取JwtPayLoad部分
			LoginUser payload = JwtTokenUtil.getJwtPayLoad(token, LoginUser.class);
			if (payload != null) {
				// 删除缓存
				String tokenKey = getTokenKey(payload);
				String projectKey = CacheKeyBuilder.getCustProjectKey(payload.getUserId(), payload.getProjectId());
				RedisUtil.del(tokenKey);
				RedisUtil.del(projectKey);
			}
		} else {
			throw new BusinessException(ResultEnum.BUSINESS_ERROR);
		}
	}

	@Override
	public void checkToken(String token) {

		// 校验token是否正确
		Boolean tokenCorrect = JwtTokenUtil.checkToken(token);
		if (!tokenCorrect) {
			throw new BusinessException(ResultEnum.UNAUTHOZIED);
		}
		// 校验token是否失效
		Boolean tokenExpired = JwtTokenUtil.isTokenExpired(token);
		if (tokenExpired) {
			throw new BusinessException(ResultEnum.UNAUTHOZIED);
		}
	}

}
