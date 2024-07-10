package com.rosenzest.electric.controller;

import java.util.ArrayList;
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
import com.rosenzest.base.PageList;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.InitialOwnerUnitBuildingSettingDto;
import com.rosenzest.electric.dto.OwnerUnitBuildingDto;
import com.rosenzest.electric.dto.OwnerUnitBuildingQuery;
import com.rosenzest.electric.dto.OwnerUnitBuildingReivewQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.entity.ProjectWorker;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.enums.ProjectWorkerType;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.electric.vo.InitialOwnerUnitBuildingVo;
import com.rosenzest.electric.vo.OwnerUnitBuildingReviewVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Api(tags = "楼栋(工业园)")
@RestController
@RequestMapping("/unit/building")
public class OwnerUnitBuildingController extends ServerBaseController {

	@Autowired
	private IOwnerUnitBuildingService unitBuildingService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IProjectWorkerService projectWorkerService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IOwnerUnitDangerService unitDangerService;

	@ApiOperation(tags = "楼栋(工业园)", value = "楼栋列表(初检)")
	@PostMapping("/list")
	public ListResult<InitialOwnerUnitBuildingVo> building(@RequestBody @Valid OwnerUnitBuildingQuery query) {

		List<InitialOwnerUnitBuildingVo> unitList = new ArrayList<InitialOwnerUnitBuildingVo>();

		OwnerUnit ownerUnit = ownerUnitService.getById(query.getUnitId());
		if (ownerUnit == null) {
			return ListResult.SUCCESS(0L, unitList);
		}

		Project project = projectService.getById(ownerUnit.getProjectId());
		if (project == null || !ProjectType.INDUSTRIAL_AREA.code().equalsIgnoreCase(project.getType())) {
			return ListResult.SUCCESS(0L, unitList);
		}
		query.setType(project.getType());

		ProjectWorker projectWorker = projectWorkerService.getProjectWorker(ownerUnit.getProjectId(), getUserId(),
				ProjectWorkerType.INSPECTOR.code());
		if (projectWorker == null) {
			return ListResult.SUCCESS(0L, unitList);
		}

		query.setWorkerId(projectWorker.getId());

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		unitList = unitBuildingService.queryInitialList(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), unitList);
	}

	@ApiOperation(tags = "楼栋(工业园)", value = "楼栋列表(复检)")
	@PostMapping("/review/list")
	public ListResult<OwnerUnitBuildingReviewVo> reviewBuilding(
			@RequestBody @Valid OwnerUnitBuildingReivewQuery query) {

		List<OwnerUnitBuildingReviewVo> unitList = new ArrayList<OwnerUnitBuildingReviewVo>();

		OwnerUnit ownerUnit = ownerUnitService.getById(query.getUnitId());
		if (ownerUnit == null) {
			return ListResult.SUCCESS(0L, unitList);
		}

		Project project = projectService.getById(ownerUnit.getProjectId());
		if (project == null || !ProjectType.INDUSTRIAL_AREA.code().equalsIgnoreCase(project.getType())) {
			return ListResult.SUCCESS(0L, unitList);
		}
		query.setType(project.getType());

		ProjectWorker projectWorker = projectWorkerService.getProjectWorker(ownerUnit.getProjectId(), getUserId(),
				ProjectWorkerType.INSPECTOR.code());
		if (projectWorker == null) {
			return ListResult.SUCCESS(0L, unitList);
		}

		query.setWorkerId(projectWorker.getId());

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		unitList = unitBuildingService.queryReviewList(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), unitList);
	}

	@ApiOperation(tags = "楼栋(工业园)", value = "添加/修改楼栋")
	@PostMapping("")
	public Result<?> saveBuilding(@RequestBody @Valid OwnerUnitBuildingDto data) {

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(data.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!ProjectType.INDUSTRIAL_AREA.code().equalsIgnoreCase(ownerUnit.getType())) {
			return Result.ERROR(400, "非工业园类型项目不能添加楼栋");
		}
		if (data.getId() != null) {
			OwnerUnitBuilding unitBuilding = unitBuildingService.getById(data.getId());

			if (unitBuilding == null) {
				return Result.ERROR(400, "无操作权限");
			}

			if (InitialInspectionStatus.FINISH.code().equalsIgnoreCase(unitBuilding.getStatus())) {
				return Result.ERROR(400, "楼栋已完成初检");
			}
		}

		OwnerUnitBuilding building = new OwnerUnitBuilding();

		BeanUtils.copyProperties(data, building);

		if (unitBuildingService.saveOrUpdate(building)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "楼栋(工业园)", value = "设置初检状态")
	@PostMapping("/setting")
	public Result<?> buildingSetting(@RequestBody @Valid InitialOwnerUnitBuildingSettingDto data) {

		OwnerUnitBuilding unitBuilding = unitBuildingService.getById(data.getBuildingId());

		if (unitBuilding == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (InitialInspectionStatus.FINISH.code().equalsIgnoreCase(unitBuilding.getStatus())) {
			return Result.ERROR(400, "楼栋已完成初检");
		}

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(unitBuilding.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		unitBuilding.setIsHouseholdRate(data.getIsHouseholdRate());
		unitBuilding.setIsTest(data.getIsTest());
		unitBuilding.setIsTestReason(data.getIsTestReason());

		if ("1".equalsIgnoreCase(data.getIsHouseholdRate())) {
			unitBuilding.setStatus(InitialInspectionStatus.FINISH.code());
		} else if ("1".equalsIgnoreCase(data.getIsTest())) {
			unitBuilding.setStatus(InitialInspectionStatus.UNABLE_TO_DETECT.code());
		} else {
			unitBuilding.setStatus(InitialInspectionStatus.CHECKING.code());
		}

		if (unitBuildingService.saveOrUpdate(unitBuilding)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "楼栋(工业园)", value = "删除楼栋")
	@DeleteMapping("/{buildingId}")
	public Result<?> deleteBuilding(@PathVariable Long buildingId) {

		OwnerUnitBuilding unitBuilding = unitBuildingService.getById(buildingId);

		if (unitBuilding == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (InitialInspectionStatus.FINISH.code().equalsIgnoreCase(unitBuilding.getStatus())) {
			return Result.ERROR(400, "楼栋已完成初检");
		}

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(unitBuilding.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		// 检查是否有隐患数据
		Integer dangers = unitDangerService.countByBuildingId(unitBuilding.getId());
		if (dangers > 0) {
			return Result.ERROR(400, "请先删除隐患数据！");
		}

		if (unitBuildingService.removeById(buildingId)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}
}
