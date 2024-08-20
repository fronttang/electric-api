package com.rosenzest.electric.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.model.base.entity.BaseEntity;
import com.rosenzest.server.base.controller.ServerBaseController;

public class ElectricBaseController extends ServerBaseController {

	@Autowired
	private IProjectWorkerService projectWorkerService;

	/**
	 * 验证权限
	 * 
	 * @param createBy
	 * @param ownerUnit
	 */
	@SuppressWarnings("rawtypes")
	protected void checkPermission(BaseEntity entity, OwnerUnit ownerUnit) {

		Long userId = getUserId();

		if (!String.valueOf(userId).equalsIgnoreCase(entity.getCreateBy())) {
			
			if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, userId, ProjectWorkerAreaRoleType.EDIT)) {
				throw new BusinessException("无操作权限");
			}
		}
	}
}
