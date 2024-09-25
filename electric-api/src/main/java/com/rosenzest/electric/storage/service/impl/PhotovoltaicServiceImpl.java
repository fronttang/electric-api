package com.rosenzest.electric.storage.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.base.LoginUser;
import com.rosenzest.electric.enums.AccountType;
import com.rosenzest.electric.storage.dto.PhotovoltaicQuery;
import com.rosenzest.electric.storage.entity.Photovoltaic;
import com.rosenzest.electric.storage.mapper.PhotovoltaicMapper;
import com.rosenzest.electric.storage.service.IPhotovoltaicService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

/**
 * <p>
 * 光伏 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
@Service
public class PhotovoltaicServiceImpl extends ModelBaseServiceImpl<PhotovoltaicMapper, Photovoltaic>
		implements IPhotovoltaicService {

	@SuppressWarnings("unchecked")
	@Override
	public Page<Photovoltaic> queryList(PhotovoltaicQuery query) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		LambdaQueryWrapper<Photovoltaic> queryWrapper = new LambdaQueryWrapper<Photovoltaic>();
		queryWrapper.and((wrapper) -> wrapper.like(Photovoltaic::getName, query.getKeyword()).or()
				.like(Photovoltaic::getAddress, query.getKeyword()));

		// 操作员只能看自己的数据
		if (AccountType.OPERATOR.code().equalsIgnoreCase(loginUser.getAccountType())) {
			queryWrapper.eq(Photovoltaic::getCreateBy, loginUser.getUserId());
		}

		queryWrapper.orderByDesc(Photovoltaic::getId);

		Page<Photovoltaic> page = this.page(new Page<Photovoltaic>(query.getPage(), query.getPageSize()), queryWrapper);

		return page;
	}

}
