package com.rosenzest.electric.station.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.controller.ElectricBaseController;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.station.dto.ChargingStationPictureDto;
import com.rosenzest.electric.station.entity.OwnerUnitStationPic;
import com.rosenzest.electric.station.service.IOwnerUnitStationPicService;
import com.rosenzest.electric.station.vo.ChargingStationPictureVo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 充电站合格照片 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-08-26
 */
@Api(tags = "业主单元(充电站)")
@RestController
@RequestMapping("/unit/charging/station/pic")
public class OwnerUnitStationPicController extends ElectricBaseController {

	@Autowired
	private IOwnerUnitStationPicService stationPicService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@ApiOperation(tags = "业主单元(充电站)", value = "修改/添加充电站/充电桩合格/原始照片")
	@PostMapping("/")
	public Result<ChargingStationPictureVo> saveStationPic(@RequestBody @Valid ChargingStationPictureDto data) {

		OwnerUnit ownerUnit = ownerUnitService.getById(data.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		data.setRounds(ownerUnit.getRounds());

		OwnerUnitStationPic stationPic = stationPicService.getStationPic(data);
		if (stationPic == null) {
			stationPic = new OwnerUnitStationPic();
			stationPic.setUnitId(data.getUnitId());
			stationPic.setModule(data.getModule());
			stationPic.setRounds(data.getRounds());
			stationPic.setType(data.getType());
			if (data.getPileId() != null) {
				stationPic.setPileIds(Arrays.asList(data.getPileId()));
			}
			stationPic.setCreateBy(String.valueOf(getUserId()));
		}

		stationPic.setPictures(data.getPictures());

		stationPicService.saveOrUpdate(stationPic);

		ChargingStationPictureVo vo = new ChargingStationPictureVo();
		BeanUtils.copyProperties(stationPic, vo);
		return Result.SUCCESS(vo);
	}

	@ApiOperation(tags = "业主单元(充电站)", value = "查询充电站/充电桩合格/原始照片")
	@PostMapping("/list")
	public Result<ChargingStationPictureVo> stationPic(@RequestBody @Valid ChargingStationPictureDto data) {

		OwnerUnit ownerUnit = ownerUnitService.getById(data.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		OwnerUnitStationPic stationPic = stationPicService.getStationPic(data);

		if (stationPic != null) {

			ChargingStationPictureVo vo = new ChargingStationPictureVo();
			BeanUtils.copyProperties(stationPic, vo);
			return Result.SUCCESS(vo);
		}

		return Result.SUCCESS();
	}

	@ApiOperation(tags = "业主单元(充电站)", value = "查询充电站所有合格/原始照片")
	@GetMapping("/list/{unitId}/{type}")
	public Result<List<String>> stationPics(@PathVariable Long unitId, @PathVariable String type) {

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		List<OwnerUnitStationPic> stationPics = stationPicService.getStationPicsByUnitId(unitId, ownerUnit.getRounds(),
				type);

		if (CollUtil.isNotEmpty(stationPics)) {

			List<String> pictures = stationPics.stream().filter((d) -> StrUtil.isNotBlank(d.getPictures()))
					.map((d) -> d.getPictures()).flatMap(str -> Arrays.stream(str.split(",")))
					.collect(Collectors.toList());

			return Result.SUCCESS(pictures);

		}

		return Result.SUCCESS();
	}
}
