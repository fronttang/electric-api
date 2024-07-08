package com.rosenzest.electric.controller;

import java.util.ArrayList;
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
import com.rosenzest.electric.dto.DangerNotPassDto;
import com.rosenzest.electric.dto.DangerPassDto;
import com.rosenzest.electric.dto.InitialOwnerUnitSettingDto;
import com.rosenzest.electric.dto.OwnerUnitAgainDangerQuery;
import com.rosenzest.electric.dto.OwnerUnitAgainQueryDto;
import com.rosenzest.electric.dto.OwnerUnitDangerQuery;
import com.rosenzest.electric.dto.OwnerUnitDto;
import com.rosenzest.electric.dto.OwnerUnitQueryDto;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.entity.OwnerUnitDangerLog;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.entity.ProjectWorker;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.enums.ProjectWorkerType;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitDangerLogService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.electric.vo.AgainOwnerUnitVo;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitAgainDangerVo;
import com.rosenzest.electric.vo.OwnerUnitDangerLogVo;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;
import com.rosenzest.electric.vo.OwnerUnitVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "业主单元(城中村/工业园)")
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
	private IOwnerUnitBuildingService ownerUnitBuildingService;

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@Autowired
	private IOwnerUnitDangerLogService dangerLogService;

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "业主单元列表(初检)")
	@PostMapping("/initial/list")
	public ListResult<InitialOwnerUnitVo> list(@RequestBody @Valid OwnerUnitQueryDto query) {

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

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "业主单元列表(复检)")
	@PostMapping("/again/list")
	public ListResult<AgainOwnerUnitVo> listAgain(@RequestBody @Valid OwnerUnitAgainQueryDto query) {

		List<AgainOwnerUnitVo> unitList = new ArrayList<AgainOwnerUnitVo>();
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
		unitList = ownerUnitService.queryAginList(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), unitList);
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "设置初检状态")
	@PostMapping("/setting")
	public Result<?> setting(@RequestBody @Valid InitialOwnerUnitSettingDto data) {

		LoginUser loginUser = getLoginUser();

		OwnerUnit ownerUnit = ownerUnitService.getById(data.getId());
		if (ownerUnit == null) {
			throw new BusinessException(400, "业主单元不存在");
		}

		// 工作人员权限检查
		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, loginUser.getUserId(),
				ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		if (data.getBuildingId() != null) {
			OwnerUnitBuilding building = ownerUnitBuildingService.getById(data.getBuildingId());
			if (building == null) {
				throw new BusinessException(400, "楼栋不存在");
			}
		}

		OwnerUnitReport report = ownerUnitReportService.getReportByUnitIdAndBuildingIdAndType(data.getId(),
				data.getBuildingId(), UnitReportType.INITIAL);

		if (report == null) {
			report = new OwnerUnitReport();
		}
		report.setType(UnitReportType.INITIAL.code());
		report.setInspector(loginUser.getName());
		report.setInspectorId(loginUser.getUserId());
		report.setUnitId(data.getId());
		report.setBuildingId(data.getBuildingId());
		report.setIsDangerNotice(data.getIsDangerNotice());
		report.setIsHouseholdRate(data.getIsHouseholdRate());
		report.setIsTest(data.getIsTest());
		report.setIsTestReason(data.getIsTestReason());

		if ("1".equalsIgnoreCase(data.getIsHouseholdRate())) {
			report.setDetectStatus(InitialInspectionStatus.FINISH.code());
		} else if ("1".equalsIgnoreCase(data.getIsTest())) {
			report.setDetectStatus(InitialInspectionStatus.UNABLE_TO_DETECT.code());
		} else {
			report.setDetectStatus(InitialInspectionStatus.CHECKING.code());
		}

		// 没有初检编号的随机生成一个 TODO
		if (StrUtil.isBlank(report.getCode())) {
			report.setCode(SnowFlakeUtil.uniqueString());
		}

		if (ownerUnitReportService.saveOrUpdate(report)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "查询业主单元信息")
	@GetMapping("/{unitId}")
	public Result<OwnerUnitVo> unitInfo(@PathVariable Long unitId) {

		OwnerUnitVo ownerUnit = ownerUnitService.getOwnerUnitById(unitId);

		return Result.SUCCESS(ownerUnit);
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "添加/修改业主单元")
	@PostMapping("")
	public Result<?> saveUnit(@RequestBody @Valid OwnerUnitDto data) {

		LoginUser loginUser = getLoginUser();
		OwnerUnit unit = new OwnerUnit();
		BeanUtils.copyProperties(data, unit);
		//
		// 工作人员权限检查
		if (!projectWorkerService.checkWorkerAreaRole(unit, loginUser.getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		if (ownerUnitService.saveOwnerUnit(data)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "删除业主单元")
	@DeleteMapping("/{unitId}")
	public Result<?> deleteUnit(@PathVariable Long unitId) {

		LoginUser loginUser = getLoginUser();
		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit == null) {
			return Result.ERROR(400, "业主单元不存在");
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

		if (ownerUnitService.removeById(unitId)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "隐患汇总列表")
	@PostMapping("/danger")
	public Result<List<OwnerUnitDangerVo>> dangerList(@RequestBody @Valid OwnerUnitDangerQuery query) {

		List<OwnerUnitDangerVo> dangers = ownerUnitDangerService.queryOwnerUnitDanger(query);

		return Result.SUCCESS(dangers);
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "隐患列表(复检)")
	@PostMapping("/again/danger")
	public Result<List<OwnerUnitAgainDangerVo>> againDangerList(@RequestBody @Valid OwnerUnitAgainDangerQuery query) {

		List<OwnerUnitAgainDangerVo> dangers = ownerUnitDangerService.queryOwnerUnitAgainDanger(query);

		return Result.SUCCESS(dangers);
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "通过")
	@PostMapping("/danger/pass")
	public Result<?> pass(@RequestBody @Valid DangerPassDto data) {

		if (ownerUnitDangerService.pass(data)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "不通过/无法检测")
	@PostMapping("/danger/notPass")
	public Result<?> notPass(@RequestBody @Valid DangerNotPassDto data) {

		if (ownerUnitDangerService.notPass(data)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "检测记录")
	@GetMapping("/danger/log/{dangerId}")
	public Result<List<OwnerUnitDangerLogVo>> dangerLogs(@PathVariable Long dangerId) {

		List<OwnerUnitDangerLog> dangerLogs = dangerLogService.listByDangerId(dangerId);

		List<OwnerUnitDangerLogVo> result = BeanUtils.copyList(dangerLogs, OwnerUnitDangerLogVo.class);
		return Result.SUCCESS(result);
	}
}
