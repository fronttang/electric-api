package com.rosenzest.electric.storage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.base.LoginUser;
import com.rosenzest.electric.enums.AccountType;
import com.rosenzest.electric.storage.dto.EnergyStorageQuery;
import com.rosenzest.electric.storage.entity.EnergyStorage;
import com.rosenzest.electric.storage.entity.EnergyStorageMonth;
import com.rosenzest.electric.storage.mapper.EnergyStorageMapper;
import com.rosenzest.electric.storage.service.IEnergyStorageMonthService;
import com.rosenzest.electric.storage.service.IEnergyStorageService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

/**
 * <p>
 * 储能 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
@Service
public class EnergyStorageServiceImpl extends ModelBaseServiceImpl<EnergyStorageMapper, EnergyStorage>
		implements IEnergyStorageService {

	@Autowired
	private IEnergyStorageMonthService energyStorageMonthService;

	@SuppressWarnings("unchecked")
	@Override
	public Page<EnergyStorage> queryList(EnergyStorageQuery query) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		LambdaQueryWrapper<EnergyStorage> queryWrapper = new LambdaQueryWrapper<EnergyStorage>();

		queryWrapper.and((wrapper) -> wrapper.like(EnergyStorage::getName, query.getKeyword()).or()
				.like(EnergyStorage::getAddress, query.getKeyword()));

		// 操作员只能看自己的数据
		if (AccountType.OPERATOR.code().equalsIgnoreCase(loginUser.getAccountType())) {
			queryWrapper.eq(EnergyStorage::getCreateBy, loginUser.getUserId());
		}

		queryWrapper.orderByDesc(EnergyStorage::getId);

		Page<EnergyStorage> page = this.page(new Page<EnergyStorage>(query.getPage(), query.getPageSize()),
				queryWrapper);

		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<EnergyStorageMonth> queryMonthList(Long storageId, EnergyStorageQuery query) {

		LambdaQueryWrapper<EnergyStorageMonth> queryWrapper = new LambdaQueryWrapper<EnergyStorageMonth>();
		queryWrapper.eq(EnergyStorageMonth::getStorageId, storageId);
		queryWrapper.orderByDesc(EnergyStorageMonth::getMonth);

		Page<EnergyStorageMonth> page = energyStorageMonthService
				.page(new Page<EnergyStorageMonth>(query.getPage(), query.getPageSize()), queryWrapper);

		return page;
	}

	@Override
	@Transactional
	public boolean remove(Long id) {
		this.removeById(id);

		LambdaQueryWrapper<EnergyStorageMonth> queryWrapper = new LambdaQueryWrapper<EnergyStorageMonth>();
		queryWrapper.eq(EnergyStorageMonth::getStorageId, id);

		energyStorageMonthService.remove(queryWrapper);

		return true;
	}

}
