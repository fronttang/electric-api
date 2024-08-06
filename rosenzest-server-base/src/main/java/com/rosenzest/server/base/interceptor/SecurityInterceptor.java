/**
 *
 */
package com.rosenzest.server.base.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.alibaba.fastjson.JSON;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.constant.ResultCodeConstants;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.constant.SystemConstants;
import com.rosenzest.base.enums.EnumUtils;
import com.rosenzest.base.enums.TerminalType;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.AnnotationUtils;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;
import com.rosenzest.server.base.enums.UserType;
import com.rosenzest.server.base.properties.TokenProperties;
import com.rosenzest.server.base.properties.TokenProperties.TokenSM;
import com.rosenzest.server.base.service.AuthService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * token验证</br>
 * 注意interceptor不要返回false,不然请求日志会不完整
 * 
 * @author fronttang
 */
@Slf4j
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TokenProperties tokenProperties;

	@Resource
	private AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {

			// 不处理静态资源请求
			if (handler instanceof ResourceHttpRequestHandler) {
				return true;
			}

			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			TokenRule tokenRole = AnnotationUtils.findAnnotationAndDeclaringAnnotation(method, TokenRule.class);

			// 是否必须选择项目
			boolean projectRequired = true;
			// 不是必须就是跳过token
			if (tokenRole != null && !tokenRole.required()) {
				return true;
			}

			if (tokenRole != null) {
				projectRequired = tokenRole.project();
			}

			List<TerminalType> allowTerminal = Arrays.asList(TerminalType.APP);
			if (tokenRole != null && tokenRole.terminal() != null) {
				allowTerminal = Arrays.asList(tokenRole.terminal());
			}

			List<UserType> allowUserType = new ArrayList<UserType>();
			if (tokenRole != null && tokenRole.userType() != null && tokenRole.userType().length > 0) {
				allowUserType = Arrays.asList(tokenRole.userType());
			}

			String token = authService.getTokenFromRequestHeader(request);
			if (StrUtil.isNotBlank(token)) {

				// token加密是否开启
				if (TokenSM.SM_ON.equals(tokenProperties.getSm().getSts())) {
					token = checkSecurity(request, token, tokenRole);
				}

				// 不需要验证登录信息
				if (tokenRole != null && !tokenRole.login()) {
					return true;
				}

				LoginUser loginUser = authService.getLoginUserByToken(token);
				if (loginUser == null) {
					throw new BusinessException(ResultCodeConstants.UNAUTHOZIED, "无效用户信息");
				}

				TerminalType terminal = loginUser.getTerminal();
				if (!allowTerminal.contains(terminal)) {
					throw new BusinessException(ResultEnum.FORBIDDEN);
				}

				String userTypeCode = loginUser.getType();
				UserType userType = EnumUtils.init(UserType.class).fromCode(userTypeCode);

				if (CollUtil.isNotEmpty(allowUserType) && !allowUserType.contains(userType)) {
					throw new BusinessException(ResultEnum.FORBIDDEN);
				}

				// 必须选择项目
				if (projectRequired && Objects.isNull(loginUser.getProjectId())) {
					throw new BusinessException(ResultCodeConstants.FORBIDDEN, "请先选择项目");
				}

				// 将token信息存储到request中，供后续接口调用
				request.setAttribute(SystemConstants.REQUEST_TOKEN, token);

				// 将用户信息存储到request中，供后续接口调用
				request.setAttribute(SystemConstants.REQUEST_USER, JSON.toJSONString(loginUser));

				// 将用户信息放在线程上下文中
				IRequestContext current = RequestContextHolder.getCurrent();
				RequestContextHolder requestContext = (RequestContextHolder) current;
				requestContext.setUser(loginUser);

				log.info("用户信息:{}", JSON.toJSONString(loginUser));

			} else {
				throw new BusinessException(ResultCodeConstants.UNAUTHOZIED, "无token信息");
			}
			return super.preHandle(request, response, handler);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			log.error("", e);
			throw new BusinessException(ResultCodeConstants.UNAUTHOZIED, "无token信息");
		}
	}

	private String checkSecurity(HttpServletRequest request, String token, TokenRule tokenRole) {

		// 如果非必须加密则直接返回token
//        if (tokenRole != null && !tokenRole.security()) {
//            return token;
//        }
//
//        String decrypt;
//        try {
//
//            sm2 = initSm2();
//
//            // 需要自己加上04的前缀否则会解密出错
//            decrypt = StrUtil.utf8Str(sm2.decryptFromBcd("04" + token, KeyType.PrivateKey));
//        } catch (Exception e) {
//            throw new BusinessException(ResultCodeConstants.FORBIDDEN, "token解密失败");
//        }
//        JSONObject jsonObject = JSONObject.parseObject(decrypt);
//        // 获取时间，查看接口时间是否已经超过一分钟
//        long time = jsonObject.getLongValue("time");
//        long betweenTimes = DateUtil.between(DateUtil.date(time), DateUtil.date(), DateUnit.SECOND);
//        if (betweenTimes > 60 || betweenTimes < 0) {
//            throw new BusinessException(ResultCodeConstants.FORBIDDEN, "请求已过时");
//        }
//        // 获取请求url，查看现在请求的url和加密中的url是否一致
//        if (!request.getServletPath().equals(jsonObject.getString("method"))) {
//            throw new BusinessException(ResultCodeConstants.FORBIDDEN, "请求URL地址不匹配");
//        }
//        // 获取jwt token值
//        token = jsonObject.getString(tokenProperties.getHeader());
		return token;
	}

//    private SM2 initSm2() throws Exception {
//        return SmUtil.sm2(getPrivateKey(), null);
//    }
//
//    /**
//     * 获取私钥,如果缓存中存在直接缓存中取，如果没有那么直接在OSS上获取文件转换
//     *
//     * @return
//     */
//    private String getPrivateKey() throws Exception {
//        String privateKey = RedisUtil.get(CacheConstants.LOGIN_SM_PRIVATE_KEY);
//        if (StrUtil.isBlank(privateKey)) {
//            privateKey = ByteUtils.toHexString(HttpUtil.downloadBytes(tokenProperties.getSm().getPrivateKeyResource()));
//            RedisUtil.set(CacheConstants.LOGIN_SM_PRIVATE_KEY, privateKey, SystemConstants.ONE_DAY_OF_SECONDS);
//        }
//        return privateKey;
//    }
}
