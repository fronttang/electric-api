package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rosenzest.electric.entity.ProjectWorkerArea;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.mapper.ProjectWorkerAreaMapper;
import com.rosenzest.electric.service.IProjectWorkerAreaService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 项目工作人员区域 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Service
public class ProjectWorkerAreaServiceImpl extends ModelBaseServiceImpl<ProjectWorkerAreaMapper, ProjectWorkerArea>
		implements IProjectWorkerAreaService {

	@Override
	public List<ProjectWorkerArea> getProjectWorkerArea(Long workerId, ProjectWorkerAreaRoleType type) {
		return this.baseMapper.getProjectWorkerArea(workerId, type.code());
	}

}
