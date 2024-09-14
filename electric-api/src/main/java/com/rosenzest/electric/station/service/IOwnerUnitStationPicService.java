package com.rosenzest.electric.station.service;

import java.util.List;

import com.rosenzest.electric.station.dto.ChargingStationPictureDto;
import com.rosenzest.electric.station.entity.OwnerUnitStationPic;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 充电站合格照片 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-08-26
 */
public interface IOwnerUnitStationPicService extends IModelBaseService<OwnerUnitStationPic> {

	OwnerUnitStationPic getStationPic(ChargingStationPictureDto data);

	List<OwnerUnitStationPic> getStationPicsByUnitId(Long unitId, Integer rounds);

}
