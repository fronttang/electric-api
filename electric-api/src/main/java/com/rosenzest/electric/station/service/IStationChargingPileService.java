package com.rosenzest.electric.station.service;

import java.util.List;

import com.rosenzest.electric.station.entity.StationChargingPile;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 充电站充电桩 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-08-26
 */
public interface IStationChargingPileService extends IModelBaseService<StationChargingPile> {

	List<StationChargingPile> queryByUnitId(Long unitId);

}
