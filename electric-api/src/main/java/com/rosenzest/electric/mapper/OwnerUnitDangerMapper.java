package com.rosenzest.electric.mapper;

import java.util.List;

import com.rosenzest.electric.dto.OwnerUnitAgainDangerQuery;
import com.rosenzest.electric.dto.OwnerUnitDangerQuery;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.vo.OwnerUnitAgainDangerVo;
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

	List<OwnerUnitAgainDangerVo> queryOwnerUnitAgainDanger(OwnerUnitAgainDangerQuery query);

}
