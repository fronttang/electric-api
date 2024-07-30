package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.mapper.ProjectMapper;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Service
public class ProjectServiceImpl extends ModelBaseServiceImpl<ProjectMapper, Project> implements IProjectService {

	@Override
	public List<Project> getProjectByDetectId(Long detectId) {
		LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<Project>();
		queryWrapper.eq(Project::getDetectId, detectId);
		return this.baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<Project> getProjectByWorkerId(Long userId) {
		return this.baseMapper.getProjectByWorkerId(userId);
	}

	@Override
	public Project getProjectByWorkerId(Long userId, Long projectId) {
		return this.baseMapper.getProjectByWorkerIdAndProjectId(userId, projectId);
	}

}
