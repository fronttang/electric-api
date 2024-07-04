package com.rosenzest.electric.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.mapper.OwnerUnitReportMapper;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Service
public class OwnerUnitReportServiceImpl extends ModelBaseServiceImpl<OwnerUnitReportMapper, OwnerUnitReport>
		implements IOwnerUnitReportService {

	@Override
	public OwnerUnitReport getReportByUnitIdAndBuildingId(Long unitId, Long buildingId) {
		LambdaQueryWrapper<OwnerUnitReport> queryWrapper = new LambdaQueryWrapper<OwnerUnitReport>();
		queryWrapper.eq(OwnerUnitReport::getUnitId, unitId);
		if (buildingId != null) {
			queryWrapper.eq(OwnerUnitReport::getBuildingId, buildingId);
		}
		queryWrapper.last(" LIMIT 1 ");
		return this.baseMapper.selectOne(queryWrapper);
	}

}
