package com.rosenzest.electric.station.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.station.entity.OwnerUnitStationPeprePic;
import com.rosenzest.electric.station.mapper.OwnerUnitStationPeprePicMapper;
import com.rosenzest.electric.station.service.IOwnerUnitStationPeprePicService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 代表照片 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-09-14
 */
@Service
public class OwnerUnitStationPeprePicServiceImpl
		extends ModelBaseServiceImpl<OwnerUnitStationPeprePicMapper, OwnerUnitStationPeprePic>
		implements IOwnerUnitStationPeprePicService {

	@Override
	public List<OwnerUnitStationPeprePic> getStationPeprePics(Long unitId, Integer rounds) {
		LambdaQueryWrapper<OwnerUnitStationPeprePic> queryWrapper = new LambdaQueryWrapper<OwnerUnitStationPeprePic>();
		queryWrapper.eq(OwnerUnitStationPeprePic::getUnitId, unitId);
		queryWrapper.eq(OwnerUnitStationPeprePic::getRounds, rounds);
		return this.baseMapper.selectList(queryWrapper);
	}

}
