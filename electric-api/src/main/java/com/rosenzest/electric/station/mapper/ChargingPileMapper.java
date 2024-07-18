package com.rosenzest.electric.station.mapper;

import java.util.List;

import com.rosenzest.electric.station.dto.ChargingPileQuery;
import com.rosenzest.electric.station.entity.ChargingPile;
import com.rosenzest.electric.station.vo.ChargingPileVo;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-07-18
 */
public interface ChargingPileMapper extends ModelBaseMapper<ChargingPile> {

	List<ChargingPileVo> queryList(ChargingPileQuery query);

}
