package com.rosenzest.electric.storage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.electric.storage.dto.EnergyStorageQuery;
import com.rosenzest.electric.storage.entity.EnergyStorage;
import com.rosenzest.electric.storage.entity.EnergyStorageMonth;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 储能 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
public interface IEnergyStorageService extends IModelBaseService<EnergyStorage> {

	Page<EnergyStorage> queryList(EnergyStorageQuery query);

	Page<EnergyStorageMonth> queryMonthList(Long storageId, EnergyStorageQuery query);

	boolean remove(Long id);
}
