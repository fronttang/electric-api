package com.rosenzest.electric.design.controller;

import static cn.hutool.core.bean.BeanUtil.copyProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.base.ListResult;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.controller.ElectricBaseController;
import com.rosenzest.electric.design.dto.ElectricityProjectDeviceDto;
import com.rosenzest.electric.design.dto.ElectricityProjectDeviceQuery;
import com.rosenzest.electric.design.entity.ElectricityProjectDevice;
import com.rosenzest.electric.design.service.IElectricityProjectDeviceService;
import com.rosenzest.electric.design.vo.ElectricityProjectDeviceVo;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.enums.UserType;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 电力设计项目设备 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@TokenRule(project = false, userType = UserType.ELECTRIC)
@Api(tags = "电力设计")
@RestController
@RequestMapping("/electricity/project/device")
public class ElectricityProjectDeviceController extends ElectricBaseController {

	@Autowired
	private IElectricityProjectDeviceService deviceService;

	@ApiOperation(tags = "电力设计", value = "项目设备列表")
	@PostMapping("/list")
	public ListResult<ElectricityProjectDeviceVo> list(@RequestBody ElectricityProjectDeviceQuery query) {

		Page<ElectricityProjectDevice> queryList = deviceService.queryList(query);

		List<ElectricityProjectDevice> records = queryList.getRecords();
		if (CollUtil.isNotEmpty(records)) {
			List<ElectricityProjectDeviceVo> datas = BeanUtils.copyList(records, ElectricityProjectDeviceVo.class);
			return ListResult.SUCCESS(queryList.getTotal(), datas);
		}

		return ListResult.SUCCESS(0L, new ArrayList<ElectricityProjectDeviceVo>());
	}

	@ApiOperation(tags = "电力设计", value = "添加/修改项目设备")
	@PostMapping("")
	public Result<ElectricityProjectDeviceVo> add(@RequestBody @Valid ElectricityProjectDeviceDto data) {

		ElectricityProjectDevice entity = new ElectricityProjectDevice();
		BeanUtils.copyProperties(data, entity);

		if (Objects.isNull(data.getId())) {
			entity.setCreateBy(String.valueOf(getUserId()));
		}

		if (deviceService.saveOrUpdate(entity)) {
			ElectricityProjectDeviceVo vo = copyProperties(entity, ElectricityProjectDeviceVo.class);
			return Result.SUCCESS(vo);
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "电力设计", value = "删除项目设备")
	@DeleteMapping("/{deviceId}")
	public Result<?> delete(@PathVariable Long deviceId) {

		if (deviceService.removeById(deviceId)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}

}
