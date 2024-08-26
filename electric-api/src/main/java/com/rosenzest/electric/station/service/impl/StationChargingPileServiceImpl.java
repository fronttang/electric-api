package com.rosenzest.electric.station.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.station.entity.StationChargingPile;
import com.rosenzest.electric.station.mapper.StationChargingPileMapper;
import com.rosenzest.electric.station.service.IStationChargingPileService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 充电站充电桩 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-08-26
 */
@Service
public class StationChargingPileServiceImpl extends ModelBaseServiceImpl<StationChargingPileMapper, StationChargingPile>
		implements IStationChargingPileService {

	@Override
	public List<StationChargingPile> queryByUnitId(Long unitId) {
		LambdaQueryWrapper<StationChargingPile> queryWrapper = new LambdaQueryWrapper<StationChargingPile>();
		queryWrapper.eq(StationChargingPile::getUnitId, unitId);
		return this.baseMapper.selectList(queryWrapper);
	}

}
