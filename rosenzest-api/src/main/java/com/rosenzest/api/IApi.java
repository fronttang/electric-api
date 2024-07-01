/**
 * 
 */
package com.rosenzest.api;

import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;

import com.rosenzest.base.exception.BusinessException;

/**
 * 对外接口定义
 * 
 * @author fronttang
 * @date 2021/08/25
 */
public interface IApi<P extends IApiRequest, R extends IApiResponse> {

    /**
     * 执行前处理逻辑,可以对请求参数进行包装处理
     * 
     * @param param
     * @return
     * @throws BusinessException
     */
    default P beforeExecute(P request) throws BusinessException {
        return request;
    }

    /**
     * 执行调用
     * 
     * @param param
     * @return
     * @throws BusinessException
     */
    R execute(P request) throws BusinessException;

    /**
     * 执行后处理逻辑，可以对返回结果进行再处理
     * 
     * @param param
     * @param result
     * @return
     * @throws BusinessException
     */
    default R afterExecute(P request, R response) throws BusinessException {
        return response;
    }

    /**
     * 获取返回结果的类型
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    default Class<R> getResponseType() {
        Class<R> clazz = (Class<R>)getGenericType(1);
        return clazz;
    }

    /**
     * 获取入参的类型
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    default Class<P> getRequestType() {
        Class<P> clazz = (Class<P>)getGenericType(0);
        return clazz;
    }

    /**
     * 获取泛型类型
     * 
     * @param index
     * @return
     */
    default Class<?> getGenericType(int index) {
        Class<?> userClass = ClassUtils.getUserClass(getClass());
        ResolvableType resolvableType = ResolvableType.forClass(userClass);

        return resolvableType.as(IApi.class).getGeneric(index).resolve();
    }
}
