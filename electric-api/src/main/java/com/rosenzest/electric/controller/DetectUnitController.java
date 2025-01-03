package com.rosenzest.electric.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.BrandDto;
import com.rosenzest.electric.entity.DetectDevice;
import com.rosenzest.electric.entity.SysDictData;
import com.rosenzest.electric.service.IDetectDeviceService;
import com.rosenzest.electric.service.ISysDictDataService;
import com.rosenzest.electric.vo.DeviceVo;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 检测单位表 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Api(tags = "检测单位")
@RestController
@RequestMapping("/detect/unit")
public class DetectUnitController extends ServerBaseController {

	@Autowired
	private IDetectDeviceService detectDeviceService;

	@Autowired
	private ISysDictDataService dictDataService;

	/**
	 * 检测单位所有检测仪器
	 * 
	 * @return
	 */
	@TokenRule(project = false)
	@ApiOperation(tags = "检测单位", value = "检测仪器查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", required = false, value = "仪器类型,见字典:detect_device_type") })
	@GetMapping("/device/list")
	public Result<List<DeviceVo>> list(@RequestParam(required = false) String type) {
		LoginUser loginUser = getLoginUser();
		Long detectId = loginUser.getDetectId();
		if (detectId == null) {
			return Result.SUCCESS();
		}

		List<DetectDevice> detectDevices = detectDeviceService.getByDetectId(detectId, type);
		List<DeviceVo> devices = BeanUtils.copyList(detectDevices, DeviceVo.class);

		return Result.SUCCESS(devices);
	}

	@ApiOperation(tags = "检测单位", value = "生产厂家/品牌列表")
	@GetMapping("/brand/list")
	public Result<List<BrandDto>> listBrand(String keyword) {
		LoginUser loginUser = getLoginUser();
		Long detectId = loginUser.getDetectId();
		if (detectId == null) {
			return Result.SUCCESS();
		}

		List<SysDictData> brandDict = dictDataService.getBrandDict(detectId, keyword);

		List<BrandDto> result = new ArrayList<BrandDto>();

		if (CollUtil.isNotEmpty(brandDict)) {
			result = BeanUtils.copyList(brandDict, (data) -> {
				BrandDto brand = new BrandDto();
				brand.setId(data.getDictValue());
				brand.setName(data.getDictLabel());
				return brand;
			});
		}
		return Result.SUCCESS(result);
	}

}
