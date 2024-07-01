package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rosenzest.electric.dto.ProjectAreaDto;
import com.rosenzest.electric.entity.ProjectArea;
import com.rosenzest.electric.mapper.ProjectAreaMapper;
import com.rosenzest.electric.service.IProjectAreaService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 项目区域 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
@Service
public class ProjectAreaServiceImpl extends ModelBaseServiceImpl<ProjectAreaMapper, ProjectArea> implements IProjectAreaService {

	@Override
	public List<ProjectAreaDto> getProjectAreas(String projectId) {
		return this.baseMapper.getProjectAreas(projectId);
	}

}
