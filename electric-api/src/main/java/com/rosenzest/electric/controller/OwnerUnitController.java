package com.rosenzest.electric.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.rosenzest.base.ListResult;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.base.Result;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.base.util.SnowFlakeUtil;
import com.rosenzest.electric.dto.InitialOwnerUnitSettingDto;
import com.rosenzest.electric.dto.OwnerUnitDto;
import com.rosenzest.electric.dto.OwnerUnitQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.entity.ProjectWorker;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.enums.ProjectWorkerType;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "业主单元")
@RestController
@RequestMapping("/unit")
public class OwnerUnitController extends ServerBaseController {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IProjectWorkerService projectWorkerService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@ApiOperation(tags = "业主单元", value = "业主单元列表(初检)")
	@PostMapping("/initial/list")
	public ListResult<InitialOwnerUnitVo> list(@RequestBody @Valid OwnerUnitQuery query) {

		query.setProjectId(getProjectId());

		List<InitialOwnerUnitVo> unitList = new ArrayList<InitialOwnerUnitVo>();
		Project project = projectService.getById(query.getProjectId());
		if (project == null) {
			return ListResult.SUCCESS(0L, unitList);
		}
		query.setType(project.getType());

		ProjectWorker projectWorker = projectWorkerService.getProjectWorker(query.getProjectId(), getUserId(),
				ProjectWorkerType.INSPECTOR.code());
		if (projectWorker == null) {
			return ListResult.SUCCESS(0L, unitList);
		}

		query.setWorkerId(projectWorker.getId());

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		unitList = ownerUnitService.queryInitialList(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), unitList);
	}

	@ApiOperation(tags = "业主单元", value = "设置初检状态")
	@PostMapping("/setting")
	public Result<?> setting(@RequestBody @Valid InitialOwnerUnitSettingDto data) {

		LoginUser loginUser = getLoginUser();

		OwnerUnit ownerUnit = ownerUnitService.getById(data.getId());
		if (ownerUnit == null) {
			throw new BusinessException(400, "业主单元不存在");
		}

		if (!String.valueOf(loginUser.getUserId()).equalsIgnoreCase(ownerUnit.getCreateBy())) {
			// 工作人员权限检查
			if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, loginUser.getUserId(),
					ProjectWorkerAreaRoleType.EDIT)) {
				return Result.ERROR(400, "无操作权限");
			}
		}

		OwnerUnitReport report = ownerUnitReportService.getReportByUnitIdAndType(data.getId(), UnitReportType.INITIAL);

		// if (report != null &&
		// InitialInspectionStatus.FINISH.code().equalsIgnoreCase(report.getDetectStatus()))
		// {
		// return Result.ERROR(400, "已初检完成");
		// }

		if (report == null) {
			report = new OwnerUnitReport();
		}
		report.setType(UnitReportType.INITIAL.code());
		report.setInspector(loginUser.getName());
		report.setInspectorId(loginUser.getUserId());
		report.setUnitId(data.getId());
		report.setIsDangerNotice(data.getIsDangerNotice());
		report.setIsComplete(data.getIsComplete());
		report.setIsTest(data.getIsTest());
		report.setIsTestReason(data.getIsTestReason());
		report.setDetectData(new Date());
		if ("1".equalsIgnoreCase(data.getIsComplete())) {
			report.setDetectStatus(InitialInspectionStatus.FINISH.code());
		} else if ("1".equalsIgnoreCase(data.getIsTest())) {
			report.setDetectStatus(InitialInspectionStatus.UNABLE_TO_DETECT.code());
		} else {
			report.setDetectStatus(InitialInspectionStatus.CHECKING.code());
		}

		// 没有初检编号的随机生成一个
		if (StrUtil.isBlank(report.getCode())) {
			report.setCode(SnowFlakeUtil.uniqueString());
		}

		if (ownerUnitReportService.saveOrUpdate(report)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元", value = "查询业主单元信息(城中村/工业园)")
	@GetMapping("/{unitId}")
	public Result<OwnerUnitVo> unitInfo(@PathVariable Long unitId) {

		OwnerUnitVo ownerUnit = ownerUnitService.getOwnerUnitById(unitId);

		if (ownerUnit != null) {
			if (ProjectType.URBAN_VILLAGE.code().equalsIgnoreCase(ownerUnit.getType())
					|| ProjectType.INDUSTRIAL_AREA.code().equalsIgnoreCase(ownerUnit.getType())) {
				return Result.SUCCESS(ownerUnit);
			}
		}
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "业主单元", value = "添加/修改业主单元(城中村/工业园)")
	@PostMapping("")
	public Result<OwnerUnitVo> saveUnit(@RequestBody @Valid OwnerUnitDto data) {

		LoginUser loginUser = getLoginUser();

		data.setProjectId(loginUser.getProjectId());

		OwnerUnit unit = new OwnerUnit();
		BeanUtils.copyProperties(data, unit);

		Project project = projectService.getById(data.getProjectId());
		if (project == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!ProjectType.URBAN_VILLAGE.code().equalsIgnoreCase(project.getType())
				&& !ProjectType.INDUSTRIAL_AREA.code().equalsIgnoreCase(project.getType())) {
			return Result.ERROR(400, "非城中村/工业园项目");
		}

		if (StrUtil.isBlank(unit.getDistrict())) {
			return Result.ERROR(400, "区ID不能为空");
		} else if (StrUtil.isBlank(unit.getStreet())) {
			return Result.ERROR(400, "街道ID不能为空");
		}
		if (ProjectType.URBAN_VILLAGE.code().equalsIgnoreCase(project.getType())) {
			if (StrUtil.isBlank(unit.getCommunity())) {
				return Result.ERROR(400, "社区ID不能为空");
			} else if (StrUtil.isBlank(unit.getHamlet())) {
				return Result.ERROR(400, "村ID不能为空");
			}
		}

		// 工作人员权限检查
		if (!projectWorkerService.checkWorkerAreaRole(unit, loginUser.getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		if (ownerUnitService.checkOwnerUnitName(unit)) {
			return Result.ERROR(400, StrUtil.format("该项目区域下已存在名为[{}]的业主单元", unit.getName()));
		}

		data.setProjectName(project.getName());
		data.setDetectId(project.getDetectId());
		data.setType(project.getType());

		if (ownerUnitService.saveOwnerUnit(data)) {
			return this.unitInfo(data.getId());
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元", value = "删除业主单元")
	@DeleteMapping("/{unitId}")
	public Result<?> deleteUnit(@PathVariable Long unitId) {

		LoginUser loginUser = getLoginUser();
		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		// 工作人员权限检查
		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, loginUser.getUserId(),
				ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		// 检查是否有隐患
		if (ownerUnitDangerService.countByUnitId(unitId) > 0) {
			return Result.ERROR(400, "请先删除隐患数据！");
		}

		if (ProjectType.CHARGING_STATION.code().equalsIgnoreCase(ownerUnit.getType())) {
			// 充电站
		}

		if (ownerUnitService.removeById(unitId)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

}
