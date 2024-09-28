package com.rosenzest.electric.storage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.storage.entity.EnergyStorageMonth;
import com.rosenzest.electric.storage.mapper.EnergyStorageMonthMapper;
import com.rosenzest.electric.storage.service.IEnergyStorageMonthService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 储能月数据 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
@Service
public class EnergyStorageMonthServiceImpl extends ModelBaseServiceImpl<EnergyStorageMonthMapper, EnergyStorageMonth>
		implements IEnergyStorageMonthService {

	@SuppressWarnings("unchecked")
	@Override
	public List<EnergyStorageMonth> getListByEnergyStorageId(Long storageId) {
		LambdaQueryWrapper<EnergyStorageMonth> queryWrapper = new LambdaQueryWrapper<EnergyStorageMonth>();

		queryWrapper.eq(EnergyStorageMonth::getStorageId, storageId);
		queryWrapper.orderByAsc(EnergyStorageMonth::getMonth);

		return this.list(queryWrapper);
	}

}
