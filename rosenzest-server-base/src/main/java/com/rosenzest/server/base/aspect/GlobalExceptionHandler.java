package com.rosenzest.server.base.aspect;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.rosenzest.base.Result;
import com.rosenzest.base.constant.ResultCodeConstants;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.constant.SymbolConstant;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.LoggerHelper;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;

/**
 * 全局异常处理
 *
 * @author L.D.J
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public Result<?> businessException(HttpServletRequest request, BusinessException e) {
        logger.error(LoggerHelper.printTop10StackTrace(e));
        return Result.ERROR(e);
    }

    /**
     * 参数错误异常处理
     * 
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public Result<?> bindException(HttpServletRequest request, BindException e) {
        logger.error("", e);
        String message = getArgNotValidMessage(e.getBindingResult());// 异常内容
        logger.info(message);// 打印日志
        return Result.ERROR(ResultCodeConstants.PARAM_BIND_ERROR_CODE, message);
    }

    /**
     * 参数错误异常处理
     * 
     * @param request
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        logger.error("", ex);
        String message = getArgNotValidMessage(ex.getBindingResult());// 异常内容
        logger.info(message);// 打印日志
        return Result.ERROR(ResultCodeConstants.PARAM_BIND_ERROR_CODE, message);
    }

    /**
     * 请求参数缺失异常
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Result<?> missParamException(HttpServletRequest request, MissingServletRequestParameterException e) {
        logger.error("", e);
        String parameterType = e.getParameterType();
        String parameterName = e.getParameterName();
        String message = StrUtil.format("缺少请求的参数{}，类型为{}", parameterName, parameterType);
        return Result.ERROR(ResultCodeConstants.PARAM_BIND_ERROR_CODE, message);
    }

    /**
     * 拦截参数格式传递异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> httpMessageNotReadable(HttpServletRequest request, HttpMessageNotReadableException e) {
        logger.error("", e);
        return Result.ERROR(ResultEnum.PARAM_BIND_ERROR);
    }

    /**
     * 拦截不支持媒体类型异常
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public Result<?> httpMediaTypeNotSupport(HttpServletRequest request, HttpMediaTypeNotSupportedException e) {
        logger.error("", e);
        return Result.ERROR(ResultEnum.PARAM_BIND_ERROR);
    }

    /**
     * 拦截请求方法的异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> methodNotSupport(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        if (ServletUtil.isPostMethod(request)) {
            logger.error("", e);
            return Result.ERROR(ResultEnum.PARAM_BIND_ERROR);
        }
        if (ServletUtil.isGetMethod(request)) {
            logger.error("", e);
            return Result.ERROR(ResultEnum.PARAM_BIND_ERROR);
        }
        return Result.ERROR(ResultEnum.PARAM_BIND_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Result<?> notFound(NoHandlerFoundException e) {
        logger.error("", e);
        return Result.ERROR(ResultEnum.RESOURCE_NOT_EXIST);
    }

    /**
     * 拦截mybatis数据库操作的异常
     */
    @ExceptionHandler(MyBatisSystemException.class)
    public Result<?> persistenceException(MyBatisSystemException e) {
        logger.error("", e);
        return Result.ERROR(ResultEnum.SYSTEM_ERROR);
    }

    private String getArgNotValidMessage(BindingResult bindingResult) {
        if (bindingResult == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        // 多个错误用逗号分隔
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError error : fieldErrors) {
            stringBuilder.append(SymbolConstant.COMMA).append(error.getField()).append(" ")
                .append(error.getDefaultMessage());
        }

        // 最终把首部的逗号去掉
        return StrUtil.removePrefix(stringBuilder.toString(), SymbolConstant.COMMA);
    }

    /**
     * 处理异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = {NullPointerException.class, RuntimeException.class, Exception.class})
    public Result<?> exceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("", e);
        return Result.ERROR(ResultEnum.SYSTEM_ERROR);
    }

    /**
     * @Author L.D.J
     * @Description 增加上传文件超限异常处理
     * @Date 2021/8/3 17:43
     * @Param [ex]
     * @return com.xmzy.base.Result<?>
     **/
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<?> handlerMaxUploadFile(MaxUploadSizeExceededException ex) {
        return Result.ERROR(ResultEnum.UPLOAD_SIZE_EXCEEDED_ERROR);
    }

}
