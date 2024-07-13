package com.rosenzest.electric.high.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.high.dto.BaseHighDto;
import com.rosenzest.electric.high.service.BaseHighConfigService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.server.base.controller.ServerBaseController;

public abstract class BaseHighController<T, DTO extends BaseHighDto, VO> extends ServerBaseController {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IProjectWorkerService projectWorkerService;

	public abstract BaseHighConfigService<T, DTO, VO> getConfigService();

	public Result<VO> save(DTO data) throws Exception {

		LoginUser loginUser = getLoginUser();
		OwnerUnit unit = new OwnerUnit();
		BeanUtils.copyProperties(data, unit);

		Project project = projectService.getById(data.getProjectId());
		if (project == null || !ProjectType.HIGH_RISK.code().equalsIgnoreCase(project.getType())) {
			return Result.ERROR(400, "无操作权限");
		}

		// 工作人员权限检查
		if (!projectWorkerService.checkWorkerAreaRole(unit, loginUser.getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
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