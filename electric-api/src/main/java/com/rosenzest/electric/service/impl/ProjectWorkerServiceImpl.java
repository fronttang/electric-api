package com.rosenzest.electric.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.dto.AreaDto;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.ProjectWorker;
import com.rosenzest.electric.entity.ProjectWorkerArea;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.enums.ProjectWorkerType;
import com.rosenzest.electric.mapper.ProjectWorkerMapper;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectWorkerAreaService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 项目工作人员 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Service
public class ProjectWorkerServiceImpl extends ModelBaseServiceImpl<ProjectWorkerMapper, ProjectWorker>
		implements IProjectWorkerService {

	@Autowired
	private IProjectWorkerAreaService workerAreaService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Override
	public ProjectWorker getProjectWorker(Long projectId, Long userId, String bindType) {
		LambdaQueryWrapper<ProjectWorker> queryWrapper = new LambdaQueryWrapper<ProjectWorker>();
		queryWrapper.eq(ProjectWorker::getProjectId, projectId);
		queryWrapper.eq(ProjectWorker::getUserId, userId);
		queryWrapper.eq(ProjectWorker::getBindType, bindType);
		queryWrapper.last(" LIMIT 1 ");
		return this.baseMapper.selectOne(queryWrapper);
	}

	@Override
	public boolean checkWorkerAreaRole(Long projectId, Long userId, ProjectWorkerAreaRoleType type, AreaDto area) {

		// 查workerId;
		ProjectWorker projectWorker = this.getProjectWorker(projectId, userId, ProjectWorkerType.INSPECTOR.code());

		if (projectWorker != null) {

			LambdaQueryWrapper<ProjectWorkerArea> queryWrapper = new LambdaQueryWrapper<ProjectWorkerArea>();
			queryWrapper.eq(ProjectWorkerArea::getWorkerId, projectWorker.getId());
			queryWrapper.eq(ProjectWorkerArea::getType, type.code());
			queryWrapper.eq(ProjectWorkerArea::getDistrict, area.getDistrict());
			queryWrapper.eq(ProjectWorkerArea::getStreet, area.getStreet());
			queryWrapper.eq(ProjectWorkerArea::getCommunity, area.getCommunity());
			queryWrapper.eq(ProjectWorkerArea::getHamlet, area.getHamlet());

			return workerAreaService.count(queryWrapper) > 0;
		}
		return false;
	}

	@Override
	public boolean checkWorkerAreaRole(Long projectId, Long userId, ProjectWorkerAreaRoleType type, Long unitId) {

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);

		return checkWorkerAreaRole(ownerUnit, userId, type);
	}

	@Override
	public boolean checkWorkerAreaRole(OwnerUnit unit, Long userId, ProjectWorkerAreaRoleType type) {
		if (unit != null) {
			AreaDto area = new AreaDto();
			area.setDistrict(unit.getDistrict());
			area.setStreet(unit.getStreet());
			area.setCommunity(unit.getCommunity());
			area.setHamlet(unit.getHamlet());

			return this.checkWorkerAreaRole(unit.getProjectId(), userId, type, area);
		}

		return false;
	}

}
