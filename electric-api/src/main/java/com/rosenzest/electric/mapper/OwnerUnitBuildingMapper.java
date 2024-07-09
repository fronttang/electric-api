package com.rosenzest.electric.mapper;

import java.util.List;

import com.rosenzest.electric.dto.OwnerUnitBuildingQuery;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.vo.InitialOwnerUnitBuildingVo;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
public interface OwnerUnitBuildingMapper extends ModelBaseMapper<OwnerUnitBuilding> {

	List<InitialOwnerUnitBuildingVo> queryInitialList(OwnerUnitBuildingQuery query);

}
