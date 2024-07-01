package com.rosenzest.api.enums;

import com.rosenzest.base.constant.IResultEnum;
import com.rosenzest.base.constant.ResultCodeConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API接口返回枚举
 * 
 * @author fronttang
 * @date 2021/08/25
 */
@Getter
@AllArgsConstructor
public enum ApiResultEnum implements IResultEnum {

    /**
     * 接口调用失败
     */
    API_CALL_FAIL(ResultCodeConstants.SYSTEM_ERROR_CODE, "接口调用失败"),

    /**
     * 请求参数不能为空
     */
    API_PARAM_NOT_NULL(ResultCodeConstants.SYSTEM_ERROR_CODE, "请求参数不能为空"),

    ;

    private Integer code;

    private String msg;

}
