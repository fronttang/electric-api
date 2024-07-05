package com.rosenzest.electric.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.LoginDto;
import com.rosenzest.electric.dto.UserDeviceDto;
import com.rosenzest.electric.entity.DetectDevice;
import com.rosenzest.electric.service.IDetectDeviceService;
import com.rosenzest.electric.service.ISysUserService;
import com.rosenzest.electric.service.IUserDeviceService;
import com.rosenzest.electric.vo.DeviceVo;
import com.rosenzest.electric.vo.LoginVo;
import com.rosenzest.server.base.controller.ServerBaseController;

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

	@ApiOperation(tags = "用户相关", value = "用户仪器列表")
	@GetMapping("/device/list")
	public Result<List<DeviceVo>> userDeviceList() {
		LoginUser loginUser = getLoginUser();

		List<DetectDevice> userDevices = userDeviceService.getUserDeviceList(loginUser.getUserId());
		List<DeviceVo> devices = BeanUtils.copyList(userDevices, DeviceVo.class);

		return Result.SUCCESS(devices);
	}

	@ApiOperation(tags = "用户相关", value = "用户仪器设置")
	@PostMapping("/device/setting")
	public Result<?> userDeviceSet(@RequestBody @Valid UserDeviceDto data) {
		LoginUser loginUser = getLoginUser();

		DetectDevice device = detectDeviceService.getById(data.getDeviceId());
		if (device == null || device.getDetectId() != loginUser.getDetectId()) {
			throw new BusinessException(400, "仪器不存在");
		}
		userDeviceService.saveUserDevice(loginUser.getUserId(), data);

		return Result.SUCCESS();
	}
}
