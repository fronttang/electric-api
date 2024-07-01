package com.rosenzest.server.base.context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.server.base.enums.RequestParamType;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;

/**
 * 请求上下文对象
 * 
 * @author fronttang
 * @date 2021/07/29
 */
public class RequestContextHolder implements IRequestContext {

    private static final long serialVersionUID = -6720417776296635784L;

    private LoginUser user;

    private String userIp;

    private Map<String, Object> items = new ConcurrentHashMap<String, Object>();

    private HttpServletRequest request;

    private Map<RequestParamType, Object> param = new ConcurrentHashMap<RequestParamType, Object>();

    private String requestNo;

    private Long start;

    private Long end;

    protected static final ThreadLocal<? extends IRequestContext> threadLocal =
        new ThreadLocal<RequestContextHolder>() {

            @Override
            protected RequestContextHolder initialValue() {
                try {
                    return ReflectUtil.newInstanceIfPossible(RequestContextHolder.class);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        };

    /**
     * 取当前的上下文对象
     * 
     * @return
     */
    public static IRequestContext getCurrent() {
        return threadLocal.get();
    }

    @Override
    public LoginUser getLoginUser() {
        return this.user;
    }

    @Override
    public String getUserIp() {
        return this.userIp;
    }

    @Override
    public void setItem(String key, Object value) {
        this.items.put(key, value);
    }

    @Override
    public Object getItem(String key) {
        return this.items.get(key);
    }

    @Override
    public Map<String, Object> getItems() {
        return this.items;
    }

    @Override
    public HttpServletRequest getRequest() {
        return this.request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getRequestNo() {
        return this.requestNo;
    }

    public void setUser(LoginUser user) {
        this.user = user;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    @Override
    public void destroy() {
        threadLocal.remove();
    }

    @Override
    public Long getUserId() {
        if (Objects.isNull(this.user)) {
            throw new BusinessException(ResultEnum.UNAUTHOZIED);
        }
        return this.user.getUserId();
    }

    @Override
    public Map<RequestParamType, Object> getParam() {
        return this.param;
    }

    public void setParam(RequestParamType key, Object value) {
        this.param.put(key, value);
    }

    @Override
    public long getStart() {
        return this.start;
    }

    @Override
    public long getEnd() {
        return this.end;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    @Override
    public Map<String, Object> getParam(RequestParamType type) {
        Object object = param.get(type);
        return BeanUtil.beanToMap(object);
    }

}
