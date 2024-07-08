package com.rosenzest.electric.service;

import com.rosenzest.electric.dto.UnitReportDto;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.enums.ReExaminationStatus;
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

	OwnerUnitReport getReportByUnitIdAndBuildingIdAndType(Long unitId, Long buildingId, UnitReportType type);

	boolean saveOrUpdateReport(UnitReportDto data);

	ReExaminationStatus getReportDetectStatus(Long unitId);

}
