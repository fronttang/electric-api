package com.rosenzest.api.factory;

import com.rosenzest.api.IApiRequest;
import com.rosenzest.api.IApiResponse;
import com.rosenzest.base.exception.BusinessException;

/**
 * 抽象接口factory
 * 
 * @author fronttang
 * @date 2021/08/25
 */
public abstract class AbstractApiFactory<R extends IApiResponse> implements IApiFactory<R> {

    /**
     * 接口入参
     */
    protected IApiRequest param;

    protected AbstractApiFactory(IApiRequest param) {
        this.param = param;
    }

    @Override
    public R execute() throws BusinessException {
        return getApi().execute(this.param);
    }
}
