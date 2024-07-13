package com.rosenzest.electric.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rosenzest.base.Result;
import com.rosenzest.electric.exception.FileSizeLimitExceededException;
import com.rosenzest.electric.exception.InvalidExtensionException;
import com.rosenzest.electric.properties.SystemProperties;
import com.rosenzest.electric.util.FileUploadUtils;
import com.rosenzest.electric.util.MimeTypeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用请求处理
 */
@Api(tags = "系统管理")
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

	/**
	 * 通用上传请求（单个）
	 */
	@ApiOperation(tags = "系统管理", value = "文件上传")
	@PostMapping("/upload")
	public Result<?> uploadFile(MultipartFile file) throws Exception {
		try {
			// 上传文件路径
			String filePath = SystemProperties.getUploadPath();
			// 上传并返回新文件名称
			String fileName = FileUploadUtils.upload(filePath, file, MimeTypeUtils.IMAGE_EXTENSION);
			return Result.SUCCESS(fileName);
		} catch (InvalidExtensionException | FileSizeLimitExceededException e) {
			log.error("", e);
			return Result.BUSINESS_ERROR(e.getMessage());
		} catch (Exception e) {
			log.error("", e);
			return Result.ERROR();
		}
	}
}