package com.rosenzest.electric.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.ListResult;
import com.rosenzest.base.PageList;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.DangerNotPassDto;
import com.rosenzest.electric.dto.DangerPassDto;
import com.rosenzest.electric.dto.OwnerUnitReviewQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.entity.OwnerUnitDangerLog;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.entity.ProjectWorker;
import com.rosenzest.electric.enums.ProjectWorkerType;
import com.rosenzest.electric.service.IOwnerUnitDangerLogService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.electric.vo.OwnerUnitDangerLogVo;
import com.rosenzest.electric.vo.OwnerUnitReviewVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "业主单元复检(城中村/工业园)")
@RestController
@RequestMapping("/unit/review")
public class OwnerUnitReviewController extends ElectricBaseController {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IProjectWorkerService projectWorkerService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@Autowired
	private IOwnerUnitDangerLogService dangerLogService;

	@ApiOperation(tags = "业主单元复检(城中村/工业园)", value = "业主单元列表(复检)")
	@PostMapping("/list")
	public ListResult<OwnerUnitReviewVo> listReivew(@RequestBody @Valid OwnerUnitReviewQuery query) {

		List<OwnerUnitReviewVo> unitList = new ArrayList<OwnerUnitReviewVo>();
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
		unitList = ownerUnitService.queryReviewList(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), unitList);
	}

	@ApiOperation(tags = "业主单元复检(城中村/工业园)", value = "通过")
	@PostMapping("/danger/pass")
	public Result<?> pass(@RequestBody @Valid DangerPassDto data) {

		// LoginUser loginUser = getLoginUser();

		OwnerUnitDanger danger = ownerUnitDangerService.getById(data.getDangerId());
		if (danger == null) {
			return Result.ERROR();
		}

//		if (ReviewStatus.FINISH.code().equalsIgnoreCase(danger.getStatus())) {
//			return Result.SUCCESS();
//		}

		OwnerUnit ownerUnit = ownerUnitService.getById(danger.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR();
		}

		checkPermission(danger, ownerUnit);

		// 工作人员权限检查
//		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, loginUser.getUserId(),
//				ProjectWorkerAreaRoleType.EDIT)) {
//			return Result.ERROR(400, "无操作权限");
//		}

		if (ownerUnitDangerService.pass(data)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元复检(城中村/工业园)", value = "不通过/无法检测")
	@PostMapping("/danger/notPass")
	public Result<?> notPass(@RequestBody @Valid DangerNotPassDto data) {

		// LoginUser loginUser = getLoginUser();

		OwnerUnitDanger danger = ownerUnitDangerService.getById(data.getDangerId());
		if (danger == null) {
			return Result.ERROR();
		}

//		if (ReviewStatus.FINISH.code().equalsIgnoreCase(danger.getStatus())) {
//			return Result.SUCCESS();
//		}

		OwnerUnit ownerUnit = ownerUnitService.getById(danger.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR();
		}

		checkPermission(danger, ownerUnit);

		// 工作人员权限检查
//		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, loginUser.getUserId(),
//				ProjectWorkerAreaRoleType.EDIT)) {
//			return Result.ERROR(400, "无操作权限");
//		}

		if (ownerUnitDangerService.notPass(data)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元复检(城中村/工业园)", value = "检测记录")
	@GetMapping("/danger/log/{dangerId}")
	public Result<List<OwnerUnitDangerLogVo>> dangerLogs(@PathVariable Long dangerId) {

		List<OwnerUnitDangerLog> dangerLogs = dangerLogService.listByDangerId(dangerId);

		List<OwnerUnitDangerLogVo> result = BeanUtils.copyList(dangerLogs, OwnerUnitDangerLogVo.class);
		return Result.SUCCESS(result);
	}

}
