package com.rosenzest.electric.mapper;

import org.apache.ibatis.annotations.Param;

import com.rosenzest.electric.entity.IntuitiveDetect;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * 直观检测表标题 Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
public interface IntuitiveDetectMapper extends ModelBaseMapper<IntuitiveDetect> {

	Integer getFormDangers(@Param("formId") Long formId, @Param("unitId") Long unitId,
			@Param("unitAreaId") Long unitAreaId, @Param("buildingId") Long buildingId);

	Integer getFormbDangers(@Param("code") String code, @Param("unitId") Long unitId,
			@Param("unitAreaId") Long unitAreaId, @Param("buildingId") Long buildingId);

}
