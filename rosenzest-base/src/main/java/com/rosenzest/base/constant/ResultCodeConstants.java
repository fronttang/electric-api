/**
 * 
 */
package com.rosenzest.base.constant;

/**
 * 基础错误码，不要在此处定义业务上的错误码
 * 
 * @author fronttang
 * @date 2021/07/26
 */
public interface ResultCodeConstants {

    Integer ZH = 0;

    Integer EN = 1;

    /**
     * 执行成功code
     */
    Integer SUCCESS_CODE = 0;

    /**
     * 业务错误code(业务执行出错，比如验证失败)
     */
    Integer BUSINESS_ERROR_CODE = 400;

    /**
     * 系统错误code(系统运行时，抛出异常使用的code)
     */
    Integer SYSTEM_ERROR_CODE = 500;

    /**
     * 参数检验错误
     */
    Integer PARAM_BIND_ERROR_CODE = 400;

    /**
     * 文件过大错误
     */
    Integer UPLOAD_SIZE_EXCEEDED_ERROR_CODE = 400;

    /**
     * 身份验证失败
     */
    Integer AUTHENTICATION_FAIL_CODE = 401;

    /**
     * 未授权
     */
    Integer UNAUTHOZIED = 401;

    /**
     * 没有权限
     */
    Integer FORBIDDEN = 403;

    /**
     * 未找到
     */
    Integer NOT_FOUD_FAIL_CODE = 404;
}
