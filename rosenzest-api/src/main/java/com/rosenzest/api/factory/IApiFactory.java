package com.rosenzest.api.factory;

import org.springframework.core.annotation.AnnotatedElementUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rosenzest.api.IApi;
import com.rosenzest.api.IApiRequest;
import com.rosenzest.api.IApiResponse;
import com.rosenzest.api.annotation.ApiFactory;
import com.rosenzest.api.util.ApiUtils;
import com.rosenzest.base.exception.BusinessException;

/**
 * 定义API factory
 * 
 * @author fronttang
 * @date 2021/08/25
 */
public interface IApiFactory<R extends IApiResponse> {

    /**
     * 执行调用
     * 
     * @return
     */
    R execute() throws BusinessException;

    /**
     * 获取API实现类
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @JsonIgnore
    @JSONField(serialize = false)
    default IApi<IApiRequest, R> getApi() throws BusinessException {
        return ApiUtils.getApi(getApiClass());
    }

    /**
     * 获取API实现类 类型
     * 
     * @return
     */
    @SuppressWarnings({"rawtypes"})
    @JsonIgnore
    @JSONField(serialize = false)
    default Class<? extends IApi> getApiClass() {
        ApiFactory annotation = AnnotatedElementUtils.findMergedAnnotation(getClass(), ApiFactory.class);
        Class<? extends IApi> executer = annotation.exec();
        return executer;
    }
}
