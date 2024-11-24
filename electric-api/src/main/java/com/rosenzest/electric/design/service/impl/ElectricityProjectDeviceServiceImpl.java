package com.rosenzest.electric.design.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.electric.design.dto.ElectricityProjectDeviceQuery;
import com.rosenzest.electric.design.entity.ElectricityProjectDevice;
import com.rosenzest.electric.design.mapper.ElectricityProjectDeviceMapper;
import com.rosenzest.electric.design.service.IElectricityProjectDeviceService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 电力设计项目设备 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@Service
public class ElectricityProjectDeviceServiceImpl
		extends ModelBaseServiceImpl<ElectricityProjectDeviceMapper, ElectricityProjectDevice>
		implements IElectricityProjectDeviceService {

	@SuppressWarnings("unchecked")
	@Override
	public Page<ElectricityProjectDevice> queryList(ElectricityProjectDeviceQuery query) {

		LambdaQueryWrapper<ElectricityProjectDevice> queryWrapper = new LambdaQueryWrapper<ElectricityProjectDevice>();
		queryWrapper.eq(ElectricityProjectDevice::getProjectId, query.getProjectId());

		if (StrUtil.isNotBlank(query.getKeyword())) {
			queryWrapper.like(ElectricityProjectDevice::getDeviceName, query.getKeyword());
		}

		queryWrapper.orderByDesc(ElectricityProjectDevice::getId);

		Page<ElectricityProjectDevice> page = this
				.page(new Page<ElectricityProjectDevice>(query.getPage(), query.getPageSize()), queryWrapper);

		return page;
	}

}
