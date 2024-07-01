package com.rosenzest.base;

import org.slf4j.MDC;

import com.rosenzest.base.constant.ResultCodeConstants;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.constant.ResultMsgConstants;
import com.rosenzest.base.constant.SystemConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回数据
 * 
 * @author fronttang
 * @date 2021/08/05
 */
public class Result<T> implements IResult {

    private static final long serialVersionUID = -333864139251853473L;

    @Setter
    @Getter
    protected Integer code = ResultCodeConstants.SUCCESS_CODE;

    @Setter
    protected String msg = ResultMsgConstants.SUCCESS;

    protected String serNo;

    protected T data;

    public Result() {}

    public Result(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public Result(IResult result) {
        super();
        this.code = result.code();
        this.msg = result.msg();
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    @Override
    public String getMsg() {
        // String msg = MessageSourceUtil.getResultMessage(this.code, this.msg);
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getSerNo() {
        return MDC.get(SystemConstants.REQUEST_NO_HEADER_NAME);
    }

    public static <T> Result<T> ERROR() {
        return new Result<T>(ResultEnum.BUSINESS_ERROR);
    }

    public static <T> Result<T> SUCCESS() {
        return new Result<T>(ResultEnum.SUCCESS);
    }

    public static <T> Result<T> SUCCESS(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<T> ERROR(IResult result) {
        return new Result<>(result);
    }

    public static <T> Result<T> ERROR(Integer code, String msg) {
        return new Result<T>(code, msg);
    }

    public static <T> Result<T> BUSINESS_ERROR(String msg) {
        return new Result<T>(ResultCodeConstants.BUSINESS_ERROR_CODE, msg);
    }

    public static <T> Result<T> BUSINESS_ERROR() {
        return new Result<T>(ResultEnum.BUSINESS_ERROR);
    }
}
