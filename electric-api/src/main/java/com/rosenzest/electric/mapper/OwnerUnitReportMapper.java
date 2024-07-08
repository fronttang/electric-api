package com.rosenzest.electric.mapper;

import org.apache.ibatis.annotations.Param;

import com.rosenzest.electric.dto.ReportDetectStatusDto;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
public interface OwnerUnitReportMapper extends ModelBaseMapper<OwnerUnitReport> {

	ReportDetectStatusDto selectReportDetectStatus(@Param("unitId") Long unitId);

}
