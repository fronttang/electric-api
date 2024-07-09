package com.rosenzest.electric.controller;

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
import com.rosenzest.electric.dto.OwnerUnitAreaDto;
import com.rosenzest.electric.dto.UnitAreaDangerQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitArea;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.service.IOwnerUnitAreaService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.electric.vo.OwnerUnitAreaVo;
import com.rosenzest.electric.vo.UnitAreaDangerVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "公共区域/户(城中村/工业园)")
@RestController
@RequestMapping("/unit/area")
public class OwnerUnitAreaController extends ServerBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitAreaService ownerUnitAreaService;

	@Autowired
	private IOwnerUnitDangerService unitDangerService;

	@Autowired
	private IProjectWorkerService projectWorkerService;

	@ApiOperation(tags = "公共区域/户(城中村/工业园)", value = "公共区域/户列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", required = false, value = "类型,见字典:owner_unit_area_type"),
			@ApiImplicitParam(name = "unitId", required = true, value = "业主单元ID"),
			@ApiImplicitParam(name = "buildingId", required = false, value = "楼栋ID"),
			@ApiImplicitParam(name = "keyword", required = false, value = "搜索关键字") })
	@GetMapping("list")
	public Result<List<OwnerUnitAreaVo>> listArea(@RequestParam(name = "unitId") Long unitId,
			@RequestParam(name = "buildingId", required = false) Long buildingId,
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "keyword", required = false) String keyword) {

		List<OwnerUnitAreaVo> unitAreas = ownerUnitAreaService.queryUnitAreaByType(unitId, buildingId, type, keyword);

		return Result.SUCCESS(unitAreas);
	}

	@ApiOperation(tags = "公共区域/户(城中村/工业园)", value = "新增/修改公共区域/户")
	@PostMapping("")
	public Result<?> saveUnitArea(@RequestBody @Valid OwnerUnitAreaDto data) {

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(data.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		OwnerUnitArea entity = new OwnerUnitArea();
		BeanUtils.copyProperties(data, entity);

		if (ownerUnitAreaService.saveOrUpdate(entity)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "公共区域/户(城中村/工业园)", value = "删除公共区域/户")
	@DeleteMapping("/{unitAreaId}")
	public Result<?> deleteArea(@PathVariable Long unitAreaId) {

		OwnerUnitArea unitArea = ownerUnitAreaService.getById(unitAreaId);
		if (unitArea == null) {
			return Result.ERROR(400, "无操作权限");
		}

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(unitArea.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		// 检查是否有隐患数据
		Integer dangers = unitDangerService.countByUnitAreaId(unitAreaId);
		if (dangers > 0) {
			return Result.ERROR(400, "请先删除隐患数据！");
		}

		if (ownerUnitAreaService.removeById(unitAreaId)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "公共区域/户(城中村/工业园)", value = "隐患列表")
	@GetMapping("/danger")
	public Result<List<UnitAreaDangerVo>> areaDangerList(UnitAreaDangerQuery query) {

		List<OwnerUnitDanger> areaDangers = unitDangerService.selectByCondition(query);
		List<UnitAreaDangerVo> results = BeanUtils.copyList(areaDangers, UnitAreaDangerVo.class);

		return Result.SUCCESS(results);
	}

}
