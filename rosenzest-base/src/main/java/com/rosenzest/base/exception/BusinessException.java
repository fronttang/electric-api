/**
 * 
 */
package com.rosenzest.base.exception;

import com.rosenzest.base.IResult;
import com.rosenzest.base.constant.ResultCodeConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务操作异常
 * 
 * @author fronttang
 */
public class BusinessException extends RuntimeException implements IResult {

    private static final long serialVersionUID = -2227602720161158282L;

    @Setter
    @Getter
    private Integer code = ResultCodeConstants.BUSINESS_ERROR_CODE;

    public BusinessException() {
        super();
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(IResult result) {
        super(result.msg());
        this.code = result.code();
    }

    public BusinessException(Integer code, String msg) {
        this(msg);
        this.code = code;
    }

    @Override
    public String getMsg() {
        return this.getMessage();
    }

}
