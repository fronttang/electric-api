package com.rosenzest.api;

import java.util.Objects;

import com.rosenzest.api.enums.ApiResultEnum;
import com.rosenzest.base.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**
 * 抽象接口
 * 
 * @author fronttang
 * @date 2021/08/27
 */
@Slf4j
public abstract class AbstractApi<P extends IApiRequest, R extends IApiResponse> implements IRetryApi<P, R> {

    @Override
    public R execute(P request, boolean retry) throws BusinessException {

        try {

            if (Objects.isNull(request)) {
                throw new BusinessException(ApiResultEnum.API_PARAM_NOT_NULL);
            }

            // 前置处理
            request = beforeExecute(request);

            // 执行调用
            R result = doExecute(request, retry);

            // 后置处理
            return afterExecute(request, result);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ApiResultEnum.API_CALL_FAIL);
        }
    }

    /**
     * 执行调用
     * 
     * @param param
     * @param retry
     * @return
     */
    protected abstract R doExecute(P request, boolean retry) throws BusinessException;

}
