package com.rosenzest.server.base.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;
import com.rosenzest.server.base.service.AuthService;

/**
 * 服务基础controller类
 * 
 * @author fronttang
 * @date 2021/06/30
 */
public class ServerBaseController {

    @Autowired
    protected AuthService authService;

    /**
     * 获取登录用户信息
     * 
     * @return
     */
    protected LoginUser getLoginUser() {

        IRequestContext current = RequestContextHolder.getCurrent();
        LoginUser loginUser = current.getLoginUser();
        if (loginUser == null) {
            // 如果loginUser为空，则显示未授权
            throw new BusinessException(ResultEnum.UNAUTHOZIED);
        }
        return loginUser;
    }

    /**
     * 获取登录的客户号
     * 
     * @return
     */
    protected Long getUserId() {
        LoginUser loginUser = getLoginUser();
        return loginUser.getUserId();
    }

}
