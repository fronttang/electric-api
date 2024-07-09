package com.rosenzest.electric.service;

import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
public interface IOwnerUnitReportService extends IModelBaseService<OwnerUnitReport> {

	OwnerUnitReport getReportByUnitIdAndType(Long unitId, UnitReportType type);

	boolean updateUnitReportStatus(Long unitId, UnitReportType type, String status);

	boolean updateUnitReportStatus(Long unitId, UnitReportType type);

	ReviewStatus getReportDetectStatus(Long unitId);

}
