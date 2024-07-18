package com.rosenzest.electric.station.service;

import java.util.List;

import com.rosenzest.base.PageList;
import com.rosenzest.electric.station.dto.ChargingPileQuery;
import com.rosenzest.electric.station.entity.ChargingPile;
import com.rosenzest.electric.station.vo.ChargingPileVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-18
 */
public interface IChargingPileService extends IModelBaseService<ChargingPile> {

	List<ChargingPileVo> queryList(ChargingPileQuery query, PageList pageList);

}
