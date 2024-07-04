package com.rosenzest.electric.service;

import com.rosenzest.electric.entity.OwnerUnitReport;
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

	OwnerUnitReport getReportByUnitIdAndBuildingId(Long id, Long buildingId);

}
