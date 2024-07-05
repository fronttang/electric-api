package com.rosenzest.electric.service;

import com.rosenzest.electric.dto.AreaDto;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.ProjectWorker;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 项目工作人员 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
public interface IProjectWorkerService extends IModelBaseService<ProjectWorker> {

	ProjectWorker getProjectWorker(Long projectId, Long userId, String bindType);

	/**
	 * 检查工作人员区域权限
	 * 
	 * @param area
	 * @return
	 */
	boolean checkWorkerAreaRole(Long projectId, Long userId, ProjectWorkerAreaRoleType type, AreaDto area);

	/**
	 * 检查工作人员区域权限
	 * 
	 * @param area
	 * @return
	 */
	boolean checkWorkerAreaRole(Long projectId, Long userId, ProjectWorkerAreaRoleType type, Long unitId);

	/**
	 * 检查工作人员区域权限
	 * 
	 * @param area
	 * @return
	 */
	boolean checkWorkerAreaRole(OwnerUnit unit, Long userId, ProjectWorkerAreaRoleType type);
}
