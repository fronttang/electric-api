package com.rosenzest.electric.station.service;

import java.util.List;

import com.rosenzest.electric.station.entity.OwnerUnitStationPeprePic;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 代表照片 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-09-14
 */
public interface IOwnerUnitStationPeprePicService extends IModelBaseService<OwnerUnitStationPeprePic> {

	List<OwnerUnitStationPeprePic> getStationPeprePics(Long unitId, Integer rounds);

}
