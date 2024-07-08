package com.rosenzest.electric.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.dto.ReportDetectStatusDto;
import com.rosenzest.electric.dto.UnitReportDto;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.enums.ReExaminationStatus;
import com.rosenzest.electric.enums.UnitReportType;
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
	public OwnerUnitReport getReportByUnitIdAndBuildingIdAndType(Long unitId, Long buildingId, UnitReportType type) {
		LambdaQueryWrapper<OwnerUnitReport> queryWrapper = new LambdaQueryWrapper<OwnerUnitReport>();
		queryWrapper.eq(OwnerUnitReport::getUnitId, unitId);
		queryWrapper.eq(OwnerUnitReport::getType, type.code());
		if (buildingId != null) {
			queryWrapper.eq(OwnerUnitReport::getBuildingId, buildingId);
		}

		queryWrapper.last(" LIMIT 1 ");
		return this.baseMapper.selectOne(queryWrapper);
	}

	@Override
	public boolean saveOrUpdateReport(UnitReportDto data) {

		return false;
	}

	@Override
	public ReExaminationStatus getReportDetectStatus(Long unitId) {
		ReportDetectStatusDto reportDetectStatus = this.baseMapper.selectReportDetectStatus(unitId);

		if (reportDetectStatus.getDangers() > 0 && reportDetectStatus.getDangers() == reportDetectStatus.getFinishs()) {
			return ReExaminationStatus.FINISH;
		} else if (reportDetectStatus.getRectifications() > 0) {
			return ReExaminationStatus.RECTIFIED;
		} else if (reportDetectStatus.getReexaminations() > 0) {
			return ReExaminationStatus.RE_EXAMINATION;
		} else {
			return ReExaminationStatus.RECTIFIED;
		}
	}

}
