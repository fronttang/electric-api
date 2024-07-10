package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.ProjectWorkerArea;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 项目工作人员区域 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
public interface IProjectWorkerAreaService extends IModelBaseService<ProjectWorkerArea> {

	List<ProjectWorkerArea> getProjectWorkerArea(Long workerId, ProjectWorkerAreaRoleType type);

}
