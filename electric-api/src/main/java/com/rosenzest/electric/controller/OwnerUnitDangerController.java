package com.rosenzest.electric.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.enums.EnumUtils;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.UnitAreaDangerDto;
import com.rosenzest.electric.entity.IntuitiveDetectDanger;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.service.IIntuitiveDetectDangerService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "隐患(城中村/工业园)")
@RestController
@RequestMapping("/unit/danger")
public class OwnerUnitDangerController extends ServerBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IProjectWorkerService projectWorkerService;

	@Autowired
	private IOwnerUnitReportService unitReportService;

	@Autowired
	private IOwnerUnitDangerService unitDangerService;

	@Autowired
	private IIntuitiveDetectDangerService detectDangerService;

	@ApiOperation(tags = "隐患(城中村/工业园)", value = "添加/修改隐患")
	@PostMapping("")
	public Result<?> saveAreaDanger(@RequestBody @Valid UnitAreaDangerDto danger) {

		LoginUser loginUser = getLoginUser();

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(danger.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		if (danger.getId() != null) {
			OwnerUnitDanger dbDanger = unitDangerService.getById(danger.getId());
			if (ReviewStatus.FINISH.code().equalsIgnoreCase(dbDanger.getStatus())) {
				return Result.ERROR(400, "隐患已整改完成");
			}
		}

		// 检测业主单元报告状态
		OwnerUnitReport report = unitReportService.getReportByUnitIdAndType(danger.getUnitId(), UnitReportType.INITIAL);
		if (report != null && InitialInspectionStatus.FINISH.code().equalsIgnoreCase(report.getDetectStatus())) {
			return Result.ERROR(400, "已完成初检");
		}

		if (danger.getDangerId() != null) {
			IntuitiveDetectDanger detectDanger = detectDangerService.getById(danger.getDangerId());
			if (detectDanger != null) {
				danger.setLevel(detectDanger.getLevel());
				danger.setDescription(detectDanger.getDescription());
				danger.setSuggestions(detectDanger.getSuggestions());
			}
		}

		if ("B".equalsIgnoreCase(danger.getType())) {
			if (StrUtil.isBlank(danger.getFormCode())) {
				return Result.ERROR(400, "B类表CODE不能为空");
			}
			// 查询B类表
			String description = EnumUtils.init(DetectFormB.class).getNamefromCode(danger.getFormCode());
			danger.setDescription(description);
		} else {
			if (danger.getFormId() == null) {
				return Result.ERROR(400, "A/C类表ID不能为空");
			}
		}

		OwnerUnitDanger areaDanger = new OwnerUnitDanger();
		BeanUtils.copyProperties(danger, areaDanger);

		areaDanger.setInspector(loginUser.getName());
		areaDanger.setInspectorId(loginUser.getUserId());
		areaDanger.setInitialTime(new Date());

		if (unitDangerService.saveOrUpdateDanger(areaDanger)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "隐患(城中村/工业园)", value = "删除隐患")
	@DeleteMapping("/{dangerId}")
	public Result<?> deleteAreaDanger(@PathVariable Long dangerId) {

		OwnerUnitDanger danger = unitDangerService.getById(dangerId);
		if (danger == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (ReviewStatus.FINISH.code().equalsIgnoreCase(danger.getStatus())) {
			return Result.ERROR(400, "隐患已整改完成");
		}

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(danger.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		if (unitDangerService.removeDanger(danger)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

}