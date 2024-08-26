package com.rosenzest.electric.station.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.station.dto.ChargingStationDto;
import com.rosenzest.electric.station.dto.ConstructionDetailsDto;
import com.rosenzest.electric.station.service.IChargingStationService;
import com.rosenzest.electric.station.vo.ChargingStationVo;
import com.rosenzest.electric.station.vo.ConstructionDetailsVo;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 充电场站
 */
@Api(tags = "业主单元(充电站)")
@RestController
@RequestMapping("/unit/charging/station")
public class ChargingStationController extends ElectricBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IProjectService projectService;

	// @Autowired
	// private IProjectWorkerService projectWorkerService;

	@Autowired
	private IChargingStationService stationUnitService;

	@ApiOperation(tags = "业主单元(充电站)", value = "修改/添加业主单元")
	@PostMapping("")
	public Result<ChargingStationVo> saveChargingStationUnit(@RequestBody @Valid ChargingStationDto data) {

		LoginUser loginUser = getLoginUser();
		OwnerUnit unit = new OwnerUnit();
		BeanUtils.copyProperties(data, unit);

		Project project = projectService.getById(data.getProjectId());
		if (project == null || !ProjectType.CHARGING_STATION.code().equalsIgnoreCase(project.getType())) {
			return Result.ERROR(400, "无操作权限");
		}

		if (data.getId() != null) {
			OwnerUnit dbUnit = ownerUnitService.getById(data.getId());

			if (dbUnit == null) {
				return Result.ERROR(400, "无操作权限");
			}

			data.setCreateBy(dbUnit.getCreateBy());

			// 导入数据不做检查
			if (!"admin".equalsIgnoreCase(dbUnit.getCreateBy())) {
				// 非admin数据检查编辑权限 工作人员权限检查
				checkPermission(dbUnit, dbUnit);
			}
		}

		// 工作人员权限检查
//		if (!projectWorkerService.checkWorkerAreaRole(unit, loginUser.getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
//			return Result.ERROR(400, "无操作权限");
//		}

		if (ownerUnitService.checkOwnerUnitName(unit)) {
			return Result.ERROR(400, StrUtil.format("该项目区域下已存在名为[{}]的充电场站", unit.getName()));
		}

		if (unit.getId() == null) {
			unit.setCreateBy(String.valueOf(loginUser.getUserId()));
		}

		if (stationUnitService.saveChargingStation(data)) {
			return unitInfo(data.getId());
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元(充电站)", value = "查询业主单元信息")
	@GetMapping("/{unitId}")
	public Result<ChargingStationVo> unitInfo(@PathVariable Long unitId) {

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);

		if (ownerUnit != null) {
			if (ProjectType.CHARGING_STATION.code().equalsIgnoreCase(ownerUnit.getType())) {
				ChargingStationVo vo = new ChargingStationVo();
				BeanUtils.copyProperties(ownerUnit, vo);

				return Result.SUCCESS(vo);
			}
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "业主单元(充电站)", value = "查询充电站建设明细")
	@GetMapping("/details/{unitId}")
	public Result<ConstructionDetailsVo> getConstructionDetailsDto(@PathVariable Long unitId) {

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit != null) {

			if (ProjectType.CHARGING_STATION.code().equalsIgnoreCase(ownerUnit.getType())) {
				ConstructionDetailsVo vo = new ConstructionDetailsVo();
				vo.setUnitId(unitId);
				vo.setDetails(ownerUnit.getDetails());
				return Result.SUCCESS(vo);
			}
		}
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "业主单元(充电站)", value = "添加/修改充电站建设明细")
	@PostMapping("/details")
	public Result<ConstructionDetailsVo> saveConstructionDetailsDto(@RequestBody @Valid ConstructionDetailsDto data) {

		OwnerUnit ownerUnit = ownerUnitService.getById(data.getUnitId());

		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!ProjectType.CHARGING_STATION.code().equalsIgnoreCase(ownerUnit.getType())) {
			return Result.ERROR(400, "无操作权限");
		}

		// 导入数据不做检查
		if (!"admin".equalsIgnoreCase(ownerUnit.getCreateBy())) {
			// 非admin数据检查编辑权限 工作人员权限检查
			checkPermission(ownerUnit, ownerUnit);
		}

		OwnerUnit update = new OwnerUnit();
		update.setId(data.getUnitId());
		update.setDetails(data.getDetails());
		ownerUnitService.saveOrUpdate(update);

		ConstructionDetailsVo vo = new ConstructionDetailsVo();
		BeanUtils.copyProperties(data, vo);

		return Result.SUCCESS(vo);
	}

}
