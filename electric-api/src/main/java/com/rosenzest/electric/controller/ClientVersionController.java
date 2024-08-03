package com.rosenzest.electric.controller;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.ClientVersionVo;
import com.rosenzest.electric.entity.ClientVersion;
import com.rosenzest.electric.service.IClientVersionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 版本更新 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-31
 */
@Api(tags = "版本更新")
@RestController
@RequestMapping("/client/version")
public class ClientVersionController {

	@Autowired
	private IClientVersionService clientVersionService;

	@ApiOperation(tags = "版本更新", value = "版本更新")
	@GetMapping("")
	public Result<ClientVersionVo> update(@RequestParam String client, @RequestParam String version) {

		// 取最新的版本数据

		ClientVersion latestClientVersion = clientVersionService.getLatestClientVersion(client);
		if (latestClientVersion != null) {

			DefaultArtifactVersion latestVersion = new DefaultArtifactVersion(latestClientVersion.getVersion());
			DefaultArtifactVersion currentVersion = new DefaultArtifactVersion(version);

			if (currentVersion.compareTo(latestVersion) < 0) {
				ClientVersionVo vo = new ClientVersionVo();
				BeanUtils.copyProperties(latestClientVersion, vo);
				return Result.SUCCESS(vo);
			}
		}
		return Result.BUSINESS_ERROR("无新版本更新");

	}
}
