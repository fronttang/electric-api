package com.rosenzest.server.base.context;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rosenzest.base.LoginUser;
import com.rosenzest.server.base.enums.RequestParamType;

/**
 * 请求上下文接口
 * 
 * @author fronttang
 * @date 2021/07/29
 */
public interface IRequestContext extends Serializable {

    /**
     * 获取当前用户
     * 
     * @return
     */
    LoginUser getLoginUser();

    /**
     * 获取当前用户的ID
     * 
     * @return
     */
    Long getUserId();

    /**
     * 获取用户IP
     * 
     * @return
     */
    String getUserIp();

    /**
     * 向当前请求上下文中加入自定义对象
     * 
     * @param key
     * @param value
     */
    void setItem(String key, Object value);

    /**
     * 从当前请求上下文中获取自定义对象
     * 
     * @param key
     * @return
     */
    Object getItem(String key);

    /**
     * 获取请求上下文中的所有自定义对象
     * 
     * @return
     */
    Map<String, Object> getItems();

    /**
     * 获取请求上下文中的Request对象
     * 
     * @return
     */
    HttpServletRequest getRequest();

    /**
     * 所有获取请求参数
     * 
     * @return
     */
    Map<RequestParamType, Object> getParam();

    /**
     * 获取指定类型的请求参数
     * 
     * @param type
     * @return
     */
    Map<String, Object> getParam(RequestParamType type);

    /**
     * 获取请求号
     * 
     * @return
     */
    String getRequestNo();

    /**
     * 请求开始时间
     * 
     * @return
     */
    long getStart();

    /**
     * 请求结束时间
     * 
     * @return
     */
    long getEnd();

    /**
     * 销毁当前上下文对象
     */
    void destroy();

}
