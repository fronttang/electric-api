package com.rosenzest.electric.storage.service;

import java.util.List;

import com.rosenzest.electric.storage.entity.EnergyStorageMonth;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 储能月数据 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
public interface IEnergyStorageMonthService extends IModelBaseService<EnergyStorageMonth> {

	List<EnergyStorageMonth> getListByEnergyStorageId(Long storageId);
}
