package com.rosenzest.electric.office.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.base.Result;
import com.rosenzest.electric.constant.ElectricConstant;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.office.dto.FileUploadDto;
import com.rosenzest.electric.office.vo.FileDownloadVo;
import com.rosenzest.electric.office.vo.FileInfoVo;
import com.rosenzest.electric.office.vo.FilePermissionVo;
import com.rosenzest.electric.properties.SystemProperties;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v3/3rd")
public class WebOfficeController extends ServerBaseController {

	@Autowired
	private IOwnerUnitReportService unitReportService;

	@Autowired
	private SystemProperties systemProperties;

	@GetMapping("/files/{fileId}")
	public Result<FileInfoVo> fileInfo(@PathVariable String fileId) {

		List<String> split = StrUtil.split(fileId, "_");
		if (CollUtil.isEmpty(split) || split.size() != 2) {
			return Result.ERROR(40004, "文档不存在");
		}

		Long reportId = Long.valueOf(split.get(0));
		String type = split.get(1);

		OwnerUnitReport report = unitReportService.getById(reportId);

		if (report == null) {
			return Result.ERROR(40004, "文档不存在");
		}

		String docName = "初检报告";
		String fileName = null;

		if ("3".equalsIgnoreCase(type)) {
			docName = "归档Pdf报告";
			fileName = report.getArchivedPdf();
		} else if ("2".equalsIgnoreCase(type)) {
			docName = "归档Word报告";
			fileName = report.getArchivedWord();
		} else {
			docName = "制式Word报告";
			fileName = report.getWordFile();
		}

		if (StrUtil.isBlank(fileName)) {
			return Result.ERROR(40004, "文档不存在");
		}

		// 本地资源路径
		String localPath = SystemProperties.getProfile();
		// 数据库资源地址
		String filePath = localPath + StrUtil.subAfter(fileName, ElectricConstant.RESOURCE_PREFIX, false);

		File file = new File(filePath);
		if (!file.exists()) {
			return Result.ERROR(40004, "文档不存在");
		}

		FileInfoVo vo = new FileInfoVo();
		vo.setId(String.valueOf(fileId));
		vo.setName(StrUtil.format("{}.{}", docName, FilenameUtils.getExtension(file.getName())));
		vo.setCreatorId("1");
		vo.setModifierId("1");

		vo.setCreateTime(Objects.nonNull(report.getCreateTime()) ? report.getCreateTime().getTime() / 1000
				: DateUtil.currentSeconds());
		vo.setModifyTime(Objects.nonNull(report.getUpdateTime()) ? report.getUpdateTime().getTime() / 1000
				: DateUtil.currentSeconds());
		vo.setVersion(Objects.nonNull(report.getWordFileVersion()) ? report.getWordFileVersion() : 1);
		if ("2".equalsIgnoreCase(type)) {
			vo.setVersion(Objects.nonNull(report.getArchivedWordVersion()) ? report.getArchivedWordVersion() : 1);
		} else if ("3".equalsIgnoreCase(type)) {
			vo.setVersion(1);
		}
		vo.setSize(file.length());

		return Result.SUCCESS(vo);
	}

	@GetMapping("/files/{fileId}/download")
	public Result<FileDownloadVo> fileDownload(@PathVariable String fileId) {

		List<String> split = StrUtil.split(fileId, "_");
		if (CollUtil.isEmpty(split) || split.size() != 2) {
			return Result.ERROR(40004, "文档不存在");
		}

		Long reportId = Long.valueOf(split.get(0));
		String type = split.get(1);

		OwnerUnitReport report = unitReportService.getById(reportId);

		if (report == null) {
			return Result.ERROR(40004, "文档不存在");
		}

		String fileName = report.getWordFile();

		if ("2".equalsIgnoreCase(type)) {
			fileName = report.getArchivedWord();
		} else if ("3".equalsIgnoreCase(type)) {
			fileName = report.getArchivedPdf();
		}

		if (StrUtil.isBlank(fileName)) {
			return Result.ERROR(40004, "文档不存在");
		}

		// 本地资源路径
		String localPath = SystemProperties.getProfile();
		// 数据库资源地址
		String filePath = localPath + StrUtil.subAfter(fileName, ElectricConstant.RESOURCE_PREFIX, false);

		File file = new File(filePath);
		if (!file.exists()) {
			return Result.ERROR(40004, "文档不存在");
		}

		String fileMD5 = SecureUtil.md5(file);

		FileDownloadVo vo = new FileDownloadVo();
		vo.setUrl(systemProperties.getDomain() + fileName);
		vo.setDigest_type("md5");
		vo.setDigest(fileMD5);

		return Result.SUCCESS(vo);
	}

	@GetMapping("/files/{fileId}/permission")
	public Result<FilePermissionVo> permission(@PathVariable String fileId) {

		return Result.SUCCESS(new FilePermissionVo());
	}

	@GetMapping("/users")
	public Result<List<JSONObject>> users(String[] user_ids) {

		List<JSONObject> users = new ArrayList<JSONObject>();

		JSONObject user = new JSONObject();
		user.put("id", "1");
		user.put("name", "电安通");

		users.add(user);

		return Result.SUCCESS(users);
	}

	@PostMapping("/files/{fileId}/upload")
	public Result<?> upload(@PathVariable String fileId, MultipartFile file, FileUploadDto data)
			throws IllegalStateException, IOException {

		List<String> split = StrUtil.split(fileId, "_");
		if (CollUtil.isEmpty(split) || split.size() != 2) {
			return Result.ERROR(40004, "文档不存在");
		}

		Long reportId = Long.valueOf(split.get(0));
		String type = split.get(1);

		log.info("FileUploadDto:" + data);
		if (!data.getIs_manual()) {
			return Result.SUCCESS();
		}

		OwnerUnitReport report = unitReportService.getById(reportId);

		if (report == null) {
			return Result.ERROR(40004, "文档不存在");
		}

		String docName = "制式Word报告";
		String fileName = report.getWordFile();
		Integer version = Objects.nonNull(report.getWordFileVersion()) ? report.getWordFileVersion() + 1 : 1;
		// 归档WORD
		if ("2".equalsIgnoreCase(type)) {
			docName = "归档Word报告";
			fileName = report.getArchivedWord();
			version = Objects.nonNull(report.getArchivedWordVersion()) ? report.getArchivedWordVersion() + 1 : 1;
		} else if ("3".equalsIgnoreCase(type)) {
			return Result.SUCCESS();
		}

		if (StrUtil.isBlank(fileName)) {
			return Result.ERROR(40004, "文档不存在");
		}

		// 本地资源路径
		String localPath = SystemProperties.getProfile();
		// 数据库资源地址
		String filePath = localPath + StrUtil.subAfter(fileName, ElectricConstant.RESOURCE_PREFIX, false);

		File dbfile = new File(filePath);
		if (!dbfile.exists()) {
			return Result.ERROR(40004, "文档不存在");
		}

		file.transferTo(dbfile);

		OwnerUnitReport update = new OwnerUnitReport();
		update.setId(reportId);
		update.setArchivedWord(fileName);
		if ("2".equalsIgnoreCase(type)) {
			update.setArchivedWordVersion(version);
		} else if ("3".equalsIgnoreCase(type)) {
			return Result.SUCCESS();
		} else {
			update.setWordFileVersion(version);
		}
		unitReportService.saveOrUpdate(update);

		FileInfoVo vo = new FileInfoVo();
		vo.setId(String.valueOf(fileId));
		vo.setName(StrUtil.format("{}.{}", docName, FilenameUtils.getExtension(file.getName())));
		vo.setCreatorId("1");
		vo.setModifierId("1");

		vo.setCreateTime(Objects.nonNull(report.getCreateTime()) ? report.getCreateTime().getTime() / 1000
				: DateUtil.currentSeconds());
		vo.setModifyTime(DateUtil.currentSeconds());
		vo.setVersion(version);
		vo.setSize(file.getSize());

		return Result.SUCCESS(vo);
	}

}
