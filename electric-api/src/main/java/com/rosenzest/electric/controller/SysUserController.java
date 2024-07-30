package com.rosenzest.electric.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.constant.ResultCodeConstants;
import com.rosenzest.base.enums.TerminalType;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.base.util.RedisUtil;
import com.rosenzest.electric.dto.LoginDto;
import com.rosenzest.electric.dto.ModifyPasswordDto;
import com.rosenzest.electric.dto.UserDeviceDto;
import com.rosenzest.electric.entity.DetectDevice;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.entity.SysUser;
import com.rosenzest.electric.service.IDetectDeviceService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.ISysUserService;
import com.rosenzest.electric.service.IUserDeviceService;
import com.rosenzest.electric.vo.DeviceVo;
import com.rosenzest.electric.vo.LoginVo;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.cache.CacheKeyBuilder;
import com.rosenzest.server.base.controller.ServerBaseController;
import com.rosenzest.server.base.properties.TokenProperties;
import com.rosenzest.server.base.util.JwtTokenUtil;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关")
public class SysUserController extends ServerBaseController {

	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private IUserDeviceService userDeviceService;

	@Autowired
	private IDetectDeviceService detectDeviceService;

	@Autowired
	private TokenProperties tokenProperties;

	@Autowired
	private IProjectService projectService;

	/**
	 * 切换项目
	 * 
	 * @return
	 */
	@TokenRule(project = false)
	@ApiOperation(tags = "用户相关", value = "切换项目")
	@GetMapping("/change/{projectId}")
	public Result<String> changeProject(@PathVariable Long projectId) {

		LoginUser loginUser = getLoginUser();
		Long oldProjectId = loginUser.getProjectId();

		Project project = projectService.getProjectByWorkerId(loginUser.getUserId(), projectId);
		if (project == null) {
			return Result.ERROR(400, "无操作权限");
		}

		// 判断新项目是否有人选择

		String custProjectKey = CacheKeyBuilder.getCustProjectKey(loginUser.getUserId(), projectId);
		String projectCust = RedisUtil.get(custProjectKey);

		if (StrUtil.isNotBlank(projectCust)) {
			if (!projectCust.equalsIgnoreCase(loginUser.getUuid())) {
				throw new BusinessException(ResultCodeConstants.FORBIDDEN, "该账号已在其他设备登录该项目");
			}
		}

		if (Objects.nonNull(oldProjectId)) {
			// 删除旧项目
			RedisUtil.del(CacheKeyBuilder.getCustProjectKey(loginUser.getUserId(), oldProjectId));
		}

		// 生成新项目token
		String tokenKey = CacheKeyBuilder.getCustTokenKey(loginUser.getTerminal(), loginUser.getUuid());

		loginUser.setProjectId(projectId);
		String token = JwtTokenUtil.generateToken(loginUser);
		// 用户token及用户信息缓存
		RedisUtil.set(tokenKey, token, tokenProperties.getExpire());

		RedisUtil.set(CacheKeyBuilder.getCustProjectKey(loginUser.getUserId(), projectId), loginUser.getUuid(),
				tokenProperties.getExpire());

		return Result.SUCCESS(token);
	}

	@ApiOperation(tags = "用户相关", value = "退出登录")
	@GetMapping("/logout")
	public Result<?> logout() {
		LoginUser loginUser = getLoginUser();

		String tokenKey = CacheKeyBuilder.getCustTokenKey(loginUser.getTerminal(), loginUser.getUuid());
		RedisUtil.del(tokenKey);

		String projectKey = CacheKeyBuilder.getCustProjectKey(loginUser.getUserId(), loginUser.getProjectId());
		RedisUtil.del(projectKey);

		return Result.SUCCESS();
	}

	/**
	 * 账号密码登录
	 *
	 * @return
	 */
	@ApiOperation(tags = "用户相关", value = "用户登录")
	@PostMapping("/login")
	public Result<LoginVo> login(@RequestBody @Valid LoginDto login) {
		LoginVo loginVo = sysUserService.login(login);
		if (loginVo == null) {
			return Result.ERROR();
		}
		return Result.SUCCESS(loginVo);
	}

	@TokenRule(project = false)
	@ApiOperation(tags = "用户相关", value = "修改密码")
	@PutMapping("/password")
	public Result<LoginVo> modifyPassword(@RequestBody @Valid ModifyPasswordDto data) {
		LoginUser loginUser = getLoginUser();
		Long userId = loginUser.getUserId();

		SysUser user = sysUserService.getById(userId);
		if (user == null) {
			throw new BusinessException(ResultCodeConstants.UNAUTHOZIED, "无token信息");
		}

		// 验证旧密码
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// 匹配密码
		if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
			throw new BusinessException(ResultCodeConstants.BUSINESS_ERROR_CODE, "密码错误");
		}

		if (data.getPassword().equalsIgnoreCase(data.getNewPassword())) {
			throw new BusinessException(ResultCodeConstants.BUSINESS_ERROR_CODE, "新旧密码不能相同");
		}

		SysUser update = new SysUser();
		update.setUserId(userId);
		update.setPassword(passwordEncoder.encode(data.getNewPassword()));

		if (sysUserService.saveOrUpdate(update)) {

			// 删除token缓存,重新登录
			String custTokenKey = CacheKeyBuilder.getCustTokenKey(TerminalType.APP.code(), loginUser.getUuid());
			RedisUtil.del(custTokenKey);
			RedisUtil.del(CacheKeyBuilder.getCustProjectKey(loginUser.getUserId(), loginUser.getProjectId()));

			return Result.SUCCESS();
		}
		return Result.ERROR();
	}

	@TokenRule(project = false)
	@ApiOperation(tags = "用户相关", value = "用户仪器列表")
	@GetMapping("/device/list")
	public Result<List<DeviceVo>> userDeviceList() {
		LoginUser loginUser = getLoginUser();

		List<DetectDevice> userDevices = userDeviceService.getUserDeviceList(loginUser.getUserId());
		List<DeviceVo> devices = BeanUtils.copyList(userDevices, DeviceVo.class);

		return Result.SUCCESS(devices);
	}

	@TokenRule(project = false)
	@ApiOperation(tags = "用户相关", value = "用户仪器设置")
	@PostMapping("/device/setting")
	public Result<?> userDeviceSet(@RequestBody @Valid UserDeviceDto data) {
		LoginUser loginUser = getLoginUser();

		DetectDevice device = detectDeviceService.getById(data.getDeviceId());
		if (device == null || device.getDetectId() != loginUser.getDetectId()) {
			throw new BusinessException(400, "仪器不存在");
		}
		data.setDeviceType(device.getType());
		userDeviceService.saveUserDevice(loginUser.getUserId(), data);

		return Result.SUCCESS();
	}
}
