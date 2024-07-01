/**
 * 
 */
package com.rosenzest.api;

import com.rosenzest.base.exception.BusinessException;

/**
 * 可重试接口定义
 * 
 * @author fronttang
 * @date 2021/08/25
 */
public interface IRetryApi<P extends IApiRequest, R extends IApiResponse> extends IApi<P, R> {

    @Override
    default R execute(P param) throws BusinessException {
        return execute(param, true);
    }

    /**
     * 执行调用
     * 
     * @param param
     * @param retry
     * @return
     * @throws BusinessException
     */
    R execute(P param, boolean retry) throws BusinessException;
}
