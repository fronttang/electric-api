/**
 * 
 */
package com.rosenzest.server.base.service;

import javax.servlet.http.HttpServletRequest;

import com.rosenzest.base.LoginUser;

/**
 * @author fronttang
 */
public interface AuthService {

    /**
     * 从request header获取token
     *
     * @param request
     *            request
     * @return token
     */
    String getTokenFromRequestHeader(HttpServletRequest request);

    /**
     * 从request attr 获取token
     * 
     * @param request
     * @return
     */
    String getTokenFromRequestAttr(HttpServletRequest request);

    /**
     * 根据token获取登录用户信息
     *
     * @param token
     *            token
     * @return 当前登陆的用户信息
     */
    LoginUser getLoginUserByToken(String token);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 校验token是否正确
     *
     * @param token
     *            token
     */
    void checkToken(String token);

}
