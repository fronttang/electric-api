/**
 * 
 */
package com.rosenzest.base.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 基础返回结果枚举,不要在此处定义业务上的返回结果枚举
 * 
 * @author fronttang
 */
@Getter
@AllArgsConstructor
public enum ResultEnum implements IResultEnum {

    // 成功
    SUCCESS(ResultCodeConstants.SUCCESS_CODE, ResultMsgConstants.SUCCESS),
    // 业务失败
    BUSINESS_ERROR(ResultCodeConstants.BUSINESS_ERROR_CODE, ResultMsgConstants.BUSINESS_ERROR),
    // 系统异常
    SYSTEM_ERROR(ResultCodeConstants.SYSTEM_ERROR_CODE, ResultMsgConstants.SYSTEM_ERROR),
    // 资源未找到
    RESOURCE_NOT_EXIST(ResultCodeConstants.NOT_FOUD_FAIL_CODE, ResultMsgConstants.RESOURCE_NOT_FOUND),
    // 未授权
    UNAUTHOZIED(ResultCodeConstants.UNAUTHOZIED, ResultMsgConstants.UNAUTHOZIED),
    // 没有权限
    FORBIDDEN(ResultCodeConstants.FORBIDDEN, ResultMsgConstants.FORBIDDEN),
    // 参数错误
    PARAM_BIND_ERROR(ResultCodeConstants.PARAM_BIND_ERROR_CODE, ResultMsgConstants.PARAM_BIND_ERROR),
    // 文件过大错误
    UPLOAD_SIZE_EXCEEDED_ERROR(ResultCodeConstants.UPLOAD_SIZE_EXCEEDED_ERROR_CODE, ResultMsgConstants.UPLOAD_SIZE_EXCEEDED_ERROR),
    ;

    final Integer code;

    final String msg;
}
