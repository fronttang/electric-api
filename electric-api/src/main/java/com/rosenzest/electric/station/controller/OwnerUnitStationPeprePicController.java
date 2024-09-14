package com.rosenzest.electric.station.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.station.dto.OwnerUnitStationPeprePicDto;
import com.rosenzest.electric.station.entity.OwnerUnitStationPeprePic;
import com.rosenzest.electric.station.service.IOwnerUnitStationPeprePicService;
import com.rosenzest.electric.station.vo.OwnerUnitStationPeprePicVo;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 代表照片 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-09-14
 */
@Api(tags = "业主单元(充电站)")
@RestController
@RequestMapping("/unit/charging/station/pepre/pic")
public class OwnerUnitStationPeprePicController {

	@Autowired
	private IOwnerUnitStationPeprePicService stationPeprePicService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	/**
	 * 代表照片列表
	 * 
	 * @return
	 */
	@ApiOperation(tags = "业主单元(充电站)", value = "充电站代表照片列表")
	@GetMapping("/{unitId}")
	public Result<List<OwnerUnitStationPeprePicVo>> list(@PathVariable Long unitId,
			@RequestParam(required = false) Integer rounds) {

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (rounds == null) {
			rounds = ownerUnit.getRounds();
		}

		List<OwnerUnitStationPeprePic> pictures = stationPeprePicService.getStationPeprePics(unitId, rounds);

		if (CollUtil.isNotEmpty(pictures)) {
			List<OwnerUnitStationPeprePicVo> result = BeanUtils.copyList(pictures, OwnerUnitStationPeprePicVo.class);
			return Result.SUCCESS(result);
		}

		return Result.SUCCESS();
	}

	@ApiOperation(tags = "业主单元(充电站)", value = "添加/修改充电站代表照片")
	@PostMapping("/{unitId}")
	public Result<?> save(@RequestBody @Valid OwnerUnitStationPeprePicDto data) {

		OwnerUnit ownerUnit = ownerUnitService.getById(data.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (data.getId() != null) {
			OwnerUnitStationPeprePic dbPic = stationPeprePicService.getById(data.getId());
			if (dbPic != null) {
				if (!dbPic.getRounds().equals(ownerUnit.getRounds())) {
					// 轮次不一样不能修改
					return Result.ERROR(400, "非当前轮次数据不能修改");
				}
			}
		}

		OwnerUnitStationPeprePic pic = new OwnerUnitStationPeprePic();
		BeanUtils.copyProperties(data, pic);
		pic.setRounds(ownerUnit.getRounds());

		stationPeprePicService.saveOrUpdate(pic);

		return Result.SUCCESS();
	}

	@ApiOperation(tags = "业主单元(充电站)", value = "添加/修改充电站代表照片")
	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable Long id) {

		OwnerUnitStationPeprePic pic = stationPeprePicService.getById(id);
		if (pic == null) {
			return Result.ERROR(400, "无操作权限");
		}

		OwnerUnit ownerUnit = ownerUnitService.getById(pic.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!pic.getRounds().equals(ownerUnit.getRounds())) {
			// 轮次不一样不能修改
			return Result.ERROR(400, "非当前轮次数据不能删除");
		}

		stationPeprePicService.removeById(id);

		return Result.SUCCESS();
	}
}
