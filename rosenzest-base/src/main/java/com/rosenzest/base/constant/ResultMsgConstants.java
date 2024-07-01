/**
 * 
 */
package com.rosenzest.base.constant;

/**
 * 基础返回信息常量，不要在此处定义业务上的错误错误
 * 
 * @author fronttang
 * @date 2021/07/26
 */
public interface ResultMsgConstants {

    /**
     * success
     */
    String SUCCESS = "success";

    /**
     * error
     */
    String ERROR = "error";

    /**
     * 操作失败
     */
    String BUSINESS_ERROR = "操作失败";

    /**
     * status
     */
    String STATUS = "status";

    /**
     * message
     */
    String MESSAGE = "message";

    /**
     * 服务器异常
     */
    String SYSTEM_ERROR = "服务器异常";

    /**
     * 参数错误
     */
    String PARAM_BIND_ERROR = "参数错误";

    /**
     * 参数错误
     */
    String UPLOAD_SIZE_EXCEEDED_ERROR ="文件上传大小不能超过3M，请压缩图片或者降低图片质量再上传！";

    /**
     * 未授权
     */
    String UNAUTHOZIED = "未授权";

    /**
     * 没有权限
     */
    String FORBIDDEN = "没有权限";

    /**
     * 资源未找到
     */
    String RESOURCE_NOT_FOUND = "资源未找到";

}
