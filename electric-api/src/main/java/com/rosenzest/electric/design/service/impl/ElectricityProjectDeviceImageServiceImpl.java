package com.rosenzest.electric.design.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.electric.design.dto.ElectricityProjectDeviceImageQuery;
import com.rosenzest.electric.design.entity.ElectricityProjectDeviceImage;
import com.rosenzest.electric.design.mapper.ElectricityProjectDeviceImageMapper;
import com.rosenzest.electric.design.service.IElectricityProjectDeviceImageService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 设备照片集 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@Service
public class ElectricityProjectDeviceImageServiceImpl
		extends ModelBaseServiceImpl<ElectricityProjectDeviceImageMapper, ElectricityProjectDeviceImage>
		implements IElectricityProjectDeviceImageService {

	@SuppressWarnings("unchecked")
	@Override
	public Page<ElectricityProjectDeviceImage> queryList(ElectricityProjectDeviceImageQuery query) {

		LambdaQueryWrapper<ElectricityProjectDeviceImage> queryWrapper = new LambdaQueryWrapper<ElectricityProjectDeviceImage>();
		queryWrapper.eq(ElectricityProjectDeviceImage::getProjectId, query.getProjectId());
		queryWrapper.eq(ElectricityProjectDeviceImage::getDeviceId, query.getDeviceId());
		if (StrUtil.isNotBlank(query.getKeyword())) {
			queryWrapper.like(ElectricityProjectDeviceImage::getImageName, query.getKeyword());
		}

		queryWrapper.orderByDesc(ElectricityProjectDeviceImage::getId);

		Page<ElectricityProjectDeviceImage> page = this
				.page(new Page<ElectricityProjectDeviceImage>(query.getPage(), query.getPageSize()), queryWrapper);

		return page;
	}

}
