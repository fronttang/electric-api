package com.rosenzest.electric.high.service;

import java.util.List;

import com.rosenzest.electric.high.entity.OwnerUnitMissDevice;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 缺失设备 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
public interface IOwnerUnitMissDeviceService extends IModelBaseService<OwnerUnitMissDevice> {

	List<OwnerUnitMissDevice> findByUnitId(Long unitId);

}
