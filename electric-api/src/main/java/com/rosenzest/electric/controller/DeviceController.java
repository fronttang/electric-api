package com.rosenzest.electric.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.entity.DetectDevice;
import com.rosenzest.electric.service.IDetectDeviceService;
import com.rosenzest.electric.vo.DeviceVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fronttang
 * @since 2024-06-27
 */
@Api(tags = "检测仪器")
@RestController
@RequestMapping("/device")
public class DeviceController extends ServerBaseController {
	
	@Autowired
	private IDetectDeviceService detectDeviceService;

	/**
	 * 检测单位所有检测仪器
	 * @return
	 */
	@ApiOperation(tags = "检测仪器", value = "检测单位所有检测仪器")
	@GetMapping("/list")
	public Result<List<DeviceVo>> list() {
		LoginUser loginUser = getLoginUser();
		Long detectId = loginUser.getDetectId();
		if(detectId == null) {
			return Result.SUCCESS();
		}
		
		List<DetectDevice> detectDevices = detectDeviceService.getByDetectId(detectId);
		List<DeviceVo> devices = BeanUtils.copyList(detectDevices, DeviceVo.class);
		
		return Result.SUCCESS(devices);
	}
	
	@ApiOperation(tags = "检测仪器", value = "根据仪器类型查询检测单位检测仪器")
	@GetMapping("/list/{type}")
	public Result<List<DeviceVo>> listByType(@PathVariable String type) {
		LoginUser loginUser = getLoginUser();
		Long detectId = loginUser.getDetectId();
		if(detectId == null) {
			return Result.SUCCESS();
		}
		
		List<DetectDevice> detectDevices = detectDeviceService.getByDetectIdAndType(detectId, type);
		List<DeviceVo> devices = BeanUtils.copyList(detectDevices, DeviceVo.class);
		
		return Result.SUCCESS(devices);
	}
	
}
