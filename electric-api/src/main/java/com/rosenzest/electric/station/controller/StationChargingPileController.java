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
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.controller.ElectricBaseController;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.station.dto.StationChargingPileDto;
import com.rosenzest.electric.station.entity.StationChargingPile;
import com.rosenzest.electric.station.service.IStationChargingPileService;
import com.rosenzest.electric.station.vo.StationChargingPileVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 充电站充电桩 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-08-26
 */
@Api(tags = "充电站充电桩")
@RestController
@RequestMapping("/unit/station/pile")
public class StationChargingPileController extends ElectricBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IStationChargingPileService chargingPileService;

	@ApiOperation(tags = "充电站充电桩", value = "添加/修改充电桩")
	@PostMapping("")
	public Result<StationChargingPileVo> saveStationChargingPile(@RequestBody @Valid StationChargingPileDto data) {

		LoginUser loginUser = getLoginUser();

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(data.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!ProjectType.CHARGING_STATION.code().equalsIgnoreCase(ownerUnit.getType())) {
			return Result.ERROR(400, "非充电站类型项目不能添加充电桩");
		}

		if (data.getId() != null) {

			StationChargingPile chargingPile = chargingPileService.getById(data.getId());

			if (chargingPile == null) {
				return Result.ERROR(400, "无操作权限");
			}

			checkPermission(chargingPile, ownerUnit);
		}

		StationChargingPile chargingPile = new StationChargingPile();
		BeanUtils.copyProperties(data, chargingPile);

		if (chargingPile.getId() == null) {
			chargingPile.setCreateBy(String.valueOf(loginUser.getUserId()));
		}

		if (chargingPileService.saveOrUpdate(chargingPile)) {
			StationChargingPileVo vo = new StationChargingPileVo();
			BeanUtils.copyProperties(chargingPile, vo);
			return Result.SUCCESS(vo);
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "充电站充电桩", value = "删除充电桩")
	@DeleteMapping("/{pileId}")
	public Result<?> deleteChargingPile(@PathVariable Long pileId) {

		// LoginUser loginUser = getLoginUser();

		StationChargingPile chargingPile = chargingPileService.getById(pileId);

		if (chargingPile == null) {
			return Result.ERROR(400, "无操作权限");
		}

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(chargingPile.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		checkPermission(chargingPile, ownerUnit);

		if (chargingPileService.removeById(pileId)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "充电站充电桩", value = "充电桩列表")
	@GetMapping("/list/{unitId}")
	public Result<List<StationChargingPileVo>> changePileList(@PathVariable Long unitId) {

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit == null) {
			return Result.SUCCESS();
		}

		List<StationChargingPile> pileList = chargingPileService.queryByUnitId(unitId);
		List<StationChargingPileVo> voList = BeanUtils.copyList(pileList, StationChargingPileVo.class);

		return Result.SUCCESS(voList);

	}
}
