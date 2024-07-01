package com.rosenzest.base.constant;

import com.rosenzest.base.IResult;
import com.rosenzest.base.exception.BusinessException;

/**
 * 返回信息的枚举接口
 * 
 * @author fronttang
 * @date 2021/07/21
 */
public interface IResultEnum extends IResult {

    /**
     * 将枚举类型转换BusinessException
     * 
     * @return
     */
    default BusinessException toException() {
        return new BusinessException(this);
    }

}
