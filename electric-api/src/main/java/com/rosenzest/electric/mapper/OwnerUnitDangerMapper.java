package com.rosenzest.electric.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rosenzest.electric.dto.OwnerUnitDangerQuery;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
public interface OwnerUnitDangerMapper extends ModelBaseMapper<OwnerUnitDanger> {

	List<OwnerUnitDangerVo> queryOwnerUnitDanger(OwnerUnitDangerQuery query);

	Integer countByChargingPileId(@Param("pileId") Long pileId);

	Integer countFormDangers(@Param("formId") Long formId, @Param("unitId") Long unitId,
			@Param("unitAreaId") Long unitAreaId, @Param("buildingId") Long buildingId);

	Integer countFormbDangers(@Param("code") String code, @Param("unitId") Long unitId,
			@Param("unitAreaId") Long unitAreaId, @Param("buildingId") Long buildingId);

	Integer countByDataIdAndPileId(@Param("dataId") Long dataId, @Param("pileId") Long pileId);

}
