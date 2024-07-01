/**
 * 
 */
package com.rosenzest.electric.constant;

import com.rosenzest.base.constant.IResultEnum;
import com.rosenzest.base.constant.ResultCodeConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码定义
 * 
 * @author fronttang
 */
@Getter
@AllArgsConstructor
public enum ElectricErrorCode implements IResultEnum {

    VERIFICATION_SUCCESS(ResultCodeConstants.SUCCESS_CODE, "验证成功"),

    VERIFICATION_ERROR(ResultCodeConstants.AUTHENTICATION_FAIL_CODE, "用户名或密码错误,请重新输入"),

    REGISTERED(ResultCodeConstants.AUTHENTICATION_FAIL_CODE, "该用户未注册"),

    VERIFICATION_CODE_ERROR(ResultCodeConstants.PARAM_BIND_ERROR_CODE, "验证码错误"),
    
    USER_TYPE_REJECT(ResultCodeConstants.AUTHENTICATION_FAIL_CODE, "平台用户不允许登录APP"),
    
    
    

    ;

    final Integer code;

    final String msg;
}
