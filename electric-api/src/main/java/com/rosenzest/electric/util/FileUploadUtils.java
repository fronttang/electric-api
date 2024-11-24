package com.rosenzest.electric.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rosenzest.base.util.SnowFlakeUtil;
import com.rosenzest.electric.constant.ElectricConstant;
import com.rosenzest.electric.exception.FileNameLengthLimitExceededException;
import com.rosenzest.electric.exception.FileSizeLimitExceededException;
import com.rosenzest.electric.exception.InvalidExtensionException;
import com.rosenzest.electric.properties.SystemProperties;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * 文件上传工具类
 *
 * @author ruoyi
 */
public class FileUploadUtils {
	/**
	 * 默认大小 1000M
	 */
	public static final long DEFAULT_MAX_SIZE = 1000 * 1024 * 1024;

	/**
	 * 默认的文件名最大长度 100
	 */
	public static final int DEFAULT_FILE_NAME_LENGTH = 100;

	/**
	 * 默认上传的地址
	 */
	private static String defaultBaseDir = SystemProperties.getProfile();

	public static void setDefaultBaseDir(String defaultBaseDir) {
		FileUploadUtils.defaultBaseDir = defaultBaseDir;
	}

	public static String getDefaultBaseDir() {
		return defaultBaseDir;
	}

	/**
	 * 以默认配置进行文件上传
	 *
	 * @param file 上传的文件
	 * @return 文件名称
	 * @throws Exception
	 */
	public static final String upload(MultipartFile file) throws IOException {
		try {
			return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	/**
	 * 根据文件路径上传
	 *
	 * @param baseDir 相对应用的基目录
	 * @param file    上传的文件
	 * @return 文件名称
	 * @throws IOException
	 */
	public static final String upload(String baseDir, MultipartFile file) throws IOException {
		try {
			return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	/**
	 * 文件上传
	 *
	 * @param baseDir          相对应用的基目录
	 * @param file             上传的文件
	 * @param allowedExtension 上传文件类型
	 * @return 返回上传成功的文件名
	 * @throws FileSizeLimitExceededException       如果超出最大大小
	 * @throws FileNameLengthLimitExceededException 文件名太长
	 * @throws IOException                          比如读写文件出错时
	 * @throws InvalidExtensionException            文件校验异常
	 */
	public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
			throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
			InvalidExtensionException {

		assertAllowed(file, allowedExtension);

		String fileName = extractFilename(file);

		String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
		file.transferTo(Paths.get(absPath));

		return getPathFileName(baseDir, fileName);
	}

	/**
	 * 编码文件名
	 * 
	 * @throws IOException
	 */
	public static final String extractFilename(MultipartFile file) throws IOException {
		LocalDateTime now = LocalDateTime.now();
		String timestamp = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
		String fileName = timestamp + SnowFlakeUtil.uniqueString();

		String datePath = DateUtil.format(now, "yyyy/MM/dd");

		String fileMD5 = SecureUtil.md5(file.getInputStream());

		return StrUtil.format("{}/{}_{}.{}", datePath, fileName, fileMD5, getExtension(file));
	}

	public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
		File desc = new File(uploadDir + File.separator + fileName);

		if (!desc.exists()) {
			if (!desc.getParentFile().exists()) {
				desc.getParentFile().mkdirs();
			}
		}
		return desc;
	}

	public static final String getPathFileName(String uploadDir, String fileName) throws IOException {
		int dirLastIndex = SystemProperties.getProfile().length() + 1;
		String currentDir = StrUtil.subSuf(uploadDir, dirLastIndex);
		return ElectricConstant.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
	}

	/**
	 * 文件大小校验
	 *
	 * @param file 上传的文件
	 * @return
	 * @throws FileSizeLimitExceededException 如果超出最大大小
	 * @throws InvalidExtensionException
	 */
	public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
			throws FileSizeLimitExceededException, InvalidExtensionException {
		long size = file.getSize();
		if (size > DEFAULT_MAX_SIZE) {
			throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
		}

		String fileName = file.getOriginalFilename();
		String extension = getExtension(file);
		if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
			if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
				throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
						fileName);
			} else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
				throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
						fileName);
			} else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
				throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
						fileName);
			} else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
				throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
						fileName);
			} else {
				throw new InvalidExtensionException(allowedExtension, extension, fileName);
			}
		}
	}

	/**
	 * 判断MIME类型是否是允许的MIME类型
	 *
	 * @param extension
	 * @param allowedExtension
	 * @return
	 */
	public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
		for (String str : allowedExtension) {
			if (str.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文件名的后缀
	 *
	 * @param file 表单文件
	 * @return 后缀名
	 */
	public static final String getExtension(MultipartFile file) {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (StrUtil.isEmpty(extension)) {
			extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
		}
		return extension;
	}

}
