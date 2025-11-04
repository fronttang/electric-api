package com.rosenzest.electric.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.util.SnowFlakeUtil;
import com.rosenzest.electric.dto.ReportDetectStatusDto;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.mapper.OwnerUnitReportMapper;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

import cn.hutool.core.util.StrUtil;

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
	public OwnerUnitReport getReportByUnitIdAndType(Long unitId, UnitReportType type) {
		LambdaQueryWrapper<OwnerUnitReport> queryWrapper = new LambdaQueryWrapper<OwnerUnitReport>();
		queryWrapper.eq(OwnerUnitReport::getUnitId, unitId);
		queryWrapper.eq(OwnerUnitReport::getType, type.code());
		queryWrapper.last(" LIMIT 1 ");
		return this.baseMapper.selectOne(queryWrapper);
	}

	@Override
	public boolean updateUnitReportStatus(Long unitId, UnitReportType type) {
		return updateUnitReportStatus(unitId, type, null);
	}

	@Override
	public boolean updateUnitReportStatus(Long unitId, UnitReportType type, String status) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		OwnerUnitReport unitReport = getReportByUnitIdAndType(unitId, type);
		if (unitReport == null) {
			unitReport = new OwnerUnitReport();
			unitReport.setType(type.code());
			unitReport.setInspector(loginUser.getName());
			unitReport.setInspectorId(loginUser.getUserId());
			unitReport.setUnitId(unitId);
			
			if (UnitReportType.INITIAL == type) {
				unitReport.setDetectData(new Date());
			}

			// 没有编号的随机生成一个
			unitReport.setCode(SnowFlakeUtil.uniqueString());
		}

		if (StrUtil.isNotBlank(status)) {
			unitReport.setDetectStatus(status);
		} else if (UnitReportType.REVIEW == type) {
			unitReport.setDetectStatus(getReportDetectStatus(unitId).code());
		} else if (UnitReportType.INITIAL == type) {
			unitReport.setDetectStatus(InitialInspectionStatus.CHECKING.code());
		}

		return this.saveOrUpdate(unitReport);
	}

	@Override
	public ReviewStatus getReportDetectStatus(Long unitId) {
		ReportDetectStatusDto reportDetectStatus = this.baseMapper.selectReportDetectStatus(unitId);

		if (reportDetectStatus.getDangers() > 0 && reportDetectStatus.getDangers() == reportDetectStatus.getFinishs()) {
			return ReviewStatus.FINISH;
		} else if (reportDetectStatus.getRectifications() > 0) {
			return ReviewStatus.RECTIFIED;
		} else if (reportDetectStatus.getReexaminations() > 0) {
			return ReviewStatus.RE_EXAMINATION;
		} else {
			return ReviewStatus.RECTIFIED;
		}
	}

}
