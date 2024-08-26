package com.rosenzest.electric.miniapp.danger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.enums.TerminalType;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.base.util.RedisUtil;
import com.rosenzest.base.util.SnowFlakeUtil;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.miniapp.vo.OwnerUnitOverviewVo;
import com.rosenzest.electric.properties.SystemProperties;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.vo.LoginVo;
import com.rosenzest.electric.vo.ProjectVo;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.cache.CacheKeyBuilder;
import com.rosenzest.server.base.enums.UserType;
import com.rosenzest.server.base.properties.TokenProperties;
import com.rosenzest.server.base.util.JwtTokenUtil;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "小程序")
@TokenRule(terminal = TerminalType.MINIAPP)
@RestController
@RequestMapping("/miniapp/login")
public class MiniAppLoginController {

	@Autowired
	private SystemProperties properties;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private TokenProperties tokenProperties;

	@Autowired
	private IProjectService projectService;

	@ApiOperation(tags = "小程序", value = "游客登录")
	@GetMapping("/visitor")
	public Result<LoginVo> visitorLogin(String key) {

		DES des = SecureUtil.des(properties.getOwnerUnitDesKey().getBytes());
		String decryptStr = des.decryptStr(key);

//		if (!JSONUtil.isTypeJSON(decryptStr)) {
//			throw new BusinessException(ResultEnum.FORBIDDEN);
//		}

		Long unitId = Long.valueOf(decryptStr);

//		VisitorKeyDto keyDto = JSON.parseObject(decryptStr, VisitorKeyDto.class);
//		if (keyDto == null) {
//			throw new BusinessException(ResultEnum.FORBIDDEN);
//		}
//
//		if (keyDto.getId() == null) {
//			throw new BusinessException(ResultEnum.FORBIDDEN);
//		}

		OwnerUnitOverviewVo ownerUnit = ownerUnitService.getOwnerUnitInfoById(unitId);
		if (ownerUnit == null) {
			throw new BusinessException(ResultEnum.FORBIDDEN);
		}
		ownerUnit.setUserName("业主");
		Project project = projectService.getById(ownerUnit.getProjectId());
		if (project == null) {
			throw new BusinessException(ResultEnum.FORBIDDEN);
		}

		String uuid = SnowFlakeUtil.uniqueString();
		// 构建TOKEN
		LoginUser payload = new LoginUser();
		payload.setUserId(0L);
		payload.setUserName("业主");
		payload.setType(UserType.VISITOR.code());
		payload.setTerminal(TerminalType.MINIAPP);
		payload.setName("业主");
		payload.setUuid(uuid);
		payload.setDetectId(project.getDetectId());
		payload.setUnitId(ownerUnit.getId());
		payload.setProjectId(ownerUnit.getProjectId());

		String token = JwtTokenUtil.generateToken(payload);

		String tokenKey = CacheKeyBuilder.getCustTokenKey(TerminalType.MINIAPP.code(), 0L, uuid);
		// 用户token及用户信息缓存
		RedisUtil.set(tokenKey, token, tokenProperties.getExpire());

		LoginVo loginVo = new LoginVo();
		loginVo.setUserId(0L);
		loginVo.setUserName("业主");
		loginVo.setType(UserType.VISITOR.code());
		loginVo.setToken(token);
		loginVo.setNickName("业主");

		ProjectVo projectVo = new ProjectVo();
		BeanUtils.copyProperties(project, projectVo);
		loginVo.setProject(projectVo);
		loginVo.setUnit(ownerUnit);
		return Result.SUCCESS(loginVo);
	}
}
