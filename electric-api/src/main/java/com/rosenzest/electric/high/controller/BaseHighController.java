package com.rosenzest.electric.high.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.controller.ElectricBaseController;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.high.dto.BaseHighDto;
import com.rosenzest.electric.high.service.BaseHighConfigService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;

import cn.hutool.core.util.StrUtil;

public abstract class BaseHighController<C, DTO extends BaseHighDto, VO> extends ElectricBaseController {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	// @Autowired
	// private IProjectWorkerService projectWorkerService;

	public abstract BaseHighConfigService<C, DTO, VO> getConfigService();

	public Result<VO> save(DTO data) throws Exception {

		LoginUser loginUser = getLoginUser();
		OwnerUnit unit = new OwnerUnit();
		BeanUtils.copyProperties(data, unit);

		Project project = projectService.getById(data.getProjectId());
		if (project == null || !ProjectType.HIGH_RISK.code().equalsIgnoreCase(project.getType())) {
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
				checkPermission(unit, unit);
			}
		}

		// 工作人员权限检查
//		if (!projectWorkerService.checkWorkerAreaRole(unit, loginUser.getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
//			return Result.ERROR(400, "无操作权限");
//		}

		if (ownerUnitService.checkOwnerUnitName(unit)) {
			return Result.ERROR(400, StrUtil.format("该项目区域下已存在名为[{}]的业主单元", unit.getName()));
		}

		if (unit.getId() == null) {
			unit.setCreateBy(String.valueOf(loginUser.getUserId()));
		}

		if (getConfigService().saveOwnerUnit(data)) {
			return getById(data.getId());
		} else {
			return Result.ERROR();
		}
	}

	public Result<VO> getById(@PathVariable Long unitId) throws Exception {

		VO industrial = getConfigService().getOwnerUnitById(unitId);
		return Result.SUCCESS(industrial);
	}

}
