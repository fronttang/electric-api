package com.rosenzest.electric.high.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.high.dto.FireStationQuery;
import com.rosenzest.electric.high.entity.FireStation;
import com.rosenzest.electric.high.mapper.FireStationMapper;
import com.rosenzest.electric.high.service.IFireStationService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 消防站 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Service
public class FireStationServiceImpl extends ModelBaseServiceImpl<FireStationMapper, FireStation>
		implements IFireStationService {

	@Override
	public List<FireStation> fireStationList(@Valid FireStationQuery query) {
		LambdaQueryWrapper<FireStation> queryWrapper = new LambdaQueryWrapper<FireStation>();
		queryWrapper.eq(FireStation::getProjectId, query.getProjectId());
		queryWrapper.eq(FireStation::getDistrict, query.getDistrict());
		queryWrapper.eq(FireStation::getStreet, query.getStreet());
		queryWrapper.eq(FireStation::getCommunity, query.getCommunity());
		if (StrUtil.isNotBlank(query.getType())) {
			queryWrapper.eq(FireStation::getType, query.getType());
		}
		if (StrUtil.isNotBlank(query.getName())) {
			queryWrapper.like(FireStation::getName, query.getName());
		}

		return this.baseMapper.selectList(queryWrapper);
	}

}
