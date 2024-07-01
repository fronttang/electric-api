package com.rosenzest.server.base.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.rosenzest.base.Result;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.exception.BusinessException;

/**
 * 自定义错误页面
 * 
 * @author fronttang
 * @date 2021/08/03
 */
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController implements ErrorController {

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping
    public Result<?> error(HttpServletRequest request) {

        WebRequest webRequest = new ServletWebRequest(request);

        // log.info("请求头:{}", HttpRequestUtil.getHeader(request));

        HttpStatus httpStatus = getStatus(request);
        if (httpStatus == HttpStatus.NO_CONTENT) {
            return Result.SUCCESS();
        }

        // 1.先获取spring默认的返回内容
        Map<String, Object> defaultErrorAttributes = errorAttributes.getErrorAttributes(webRequest, false);

        // 2.如果返回的异常是ServiceException，则按ServiceException响应的内容进行返回
        Throwable throwable = errorAttributes.getError(webRequest);
        if (throwable instanceof BusinessException) {
            BusinessException businessException = (BusinessException)throwable;
            return businessException.toResult();
        }

        // 3.如果返回的是404 http状态码
        Integer status = (Integer)defaultErrorAttributes.get("status");
        if (HttpStatus.NOT_FOUND.value() == status) {
            return ResultEnum.RESOURCE_NOT_EXIST.toResult();
        }
        // 4.无法确定的返回服务器异常
        return ResultEnum.SYSTEM_ERROR.toResult();
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public String getErrorPath() {
        return serverProperties.getError().getPath();
    }

}
