package com.rosenzest.electric.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.ListResult;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.OwnerUnitAreaDto;
import com.rosenzest.electric.dto.OwnerUnitAreaQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitArea;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.enums.IndustrialAreaBuildingType;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.service.IOwnerUnitAreaService;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.vo.OwnerUnitAreaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "公共区域/户(城中村/工业园)")
@RestController
@RequestMapping("/unit/area")
public class OwnerUnitAreaController extends ElectricBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitAreaService ownerUnitAreaService;

	@Autowired
	private IOwnerUnitDangerService unitDangerService;

	// @Autowired
	// private IProjectWorkerService projectWorkerService;

	@Autowired
	private IOwnerUnitBuildingService unitBuildingService;

	// @Autowired
	// private IOwnerUnitReportService unitReportService;

	@ApiOperation(tags = "公共区域/户(城中村/工业园)", value = "公共区域/户列表")
	@PostMapping("list")
	public ListResult<OwnerUnitAreaVo> listArea(@RequestBody @Valid OwnerUnitAreaQuery query) {

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		List<OwnerUnitAreaVo> unitAreas = ownerUnitAreaService.queryUnitAreaByType(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), unitAreas);
	}

	@ApiOperation(tags = "公共区域/户(城中村/工业园)", value = "新增/修改公共区域/户")
	@PostMapping("")
	public Result<?> saveUnitArea(@RequestBody @Valid OwnerUnitAreaDto data) {

		LoginUser loginUser = getLoginUser();

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(data.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		// 修改
		if (data.getId() != null) {

			OwnerUnitArea unitArea = ownerUnitAreaService.getById(data.getId());
			if (unitArea == null) {
				return Result.ERROR(400, "无操作权限");
			}

			checkPermission(unitArea, ownerUnit);

//			if (!String.valueOf(loginUser.getUserId()).equalsIgnoreCase(unitArea.getCreateBy())) {
//
//				if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, loginUser.getUserId(),
//						ProjectWorkerAreaRoleType.EDIT)) {
//					return Result.ERROR(400, "无操作权限");
//				}
//			}
		}

		// 工业园
		if (ProjectType.INDUSTRIAL_AREA.code().equalsIgnoreCase(ownerUnit.getType())) {
			if (data.getBuildingId() == null) {
				return Result.ERROR(400, "楼栋ID不能为空");
			}

			OwnerUnitBuilding building = unitBuildingService.getById(data.getBuildingId());
			if (building == null || building.getUnitId() != ownerUnit.getId()) {
				return Result.ERROR(400, "无操作权限");
			}

			checkPermission(building, ownerUnit);

//			if (!String.valueOf(loginUser.getUserId()).equalsIgnoreCase(building.getCreateBy())) {
//
//				if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, loginUser.getUserId(),
//						ProjectWorkerAreaRoleType.EDIT)) {
//					return Result.ERROR(400, "无操作权限");
//				}
//			}

			// if
			// (InitialInspectionStatus.FINISH.code().equalsIgnoreCase(building.getStatus()))
			// {
			// return Result.ERROR(400, "楼栋已完成初检");
			// }

			if (IndustrialAreaBuildingType.POWER_ROOM.code().equalsIgnoreCase(building.getType())) {
				return Result.ERROR(400, "配电房无需添加公共区域或户");
			}
		}

//		OwnerUnitReport unitReport = unitReportService.getReportByUnitIdAndType(ownerUnit.getId(),
//				UnitReportType.INITIAL);
//		if (unitReport != null
//				&& InitialInspectionStatus.FINISH.code().equalsIgnoreCase(unitReport.getDetectStatus())) {
//			return Result.ERROR(400, "业主单元已完成初检");
//		}

		OwnerUnitArea entity = new OwnerUnitArea();
		BeanUtils.copyProperties(data, entity);

		if (data.getId() == null) {
			entity.setCreateBy(String.valueOf(loginUser.getUserId()));
		}

		if (ownerUnitAreaService.saveOrUpdate(entity)) {

			OwnerUnitAreaVo vo = new OwnerUnitAreaVo();
			BeanUtils.copyProperties(entity, vo);

			return Result.SUCCESS(vo);
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "公共区域/户(城中村/工业园)", value = "删除公共区域/户")
	@DeleteMapping("/{unitAreaId}")
	public Result<?> deleteArea(@PathVariable Long unitAreaId) {

		// LoginUser loginUser = getLoginUser();

		OwnerUnitArea unitArea = ownerUnitAreaService.getById(unitAreaId);
		if (unitArea == null) {
			return Result.ERROR(400, "无操作权限");
		}

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(unitArea.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		checkPermission(unitArea, ownerUnit);

//		if (!String.valueOf(loginUser.getUserId()).equalsIgnoreCase(unitArea.getCreateBy())) {
//			if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
//				return Result.ERROR(400, "无操作权限");
//			}
//		}

//		if (unitArea.getBuildingId() != null) {
//
//			OwnerUnitBuilding building = unitBuildingService.getById(unitArea.getBuildingId());
//
//			if (building != null && InitialInspectionStatus.FINISH.code().equalsIgnoreCase(building.getStatus())) {
//				return Result.ERROR(400, "楼栋已完成初检");
//			}
//		}
//
//		OwnerUnitReport unitReport = unitReportService.getReportByUnitIdAndType(ownerUnit.getId(),
//				UnitReportType.INITIAL);
//		if (unitReport != null
//				&& InitialInspectionStatus.FINISH.code().equalsIgnoreCase(unitReport.getDetectStatus())) {
//			return Result.ERROR(400, "业主单元已完成初检");
//		}

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

}
