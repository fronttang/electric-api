package com.rosenzest.base.constant;

/**
 * 抽象业务错误枚举
 * 
 * @author fronttang
 * @date 2021/08/05
 */
public interface IBusinessResultEnum extends IResultEnum {

    /**
     * 获取返回码
     * 
     * @return 返回码
     */
    @Override
    default Integer getCode() {
        return ResultCodeConstants.BUSINESS_ERROR_CODE;
    }

}
