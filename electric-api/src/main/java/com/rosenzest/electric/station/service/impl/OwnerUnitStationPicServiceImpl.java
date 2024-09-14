package com.rosenzest.electric.station.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.station.dto.ChargingStationPictureDto;
import com.rosenzest.electric.station.entity.OwnerUnitStationPic;
import com.rosenzest.electric.station.mapper.OwnerUnitStationPicMapper;
import com.rosenzest.electric.station.service.IOwnerUnitStationPicService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 充电站合格照片 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-08-26
 */
@Service
public class OwnerUnitStationPicServiceImpl extends ModelBaseServiceImpl<OwnerUnitStationPicMapper, OwnerUnitStationPic>
		implements IOwnerUnitStationPicService {

	@Override
	public OwnerUnitStationPic getStationPic(ChargingStationPictureDto data) {
		LambdaQueryWrapper<OwnerUnitStationPic> queryWrapper = new LambdaQueryWrapper<OwnerUnitStationPic>();
		queryWrapper.eq(OwnerUnitStationPic::getUnitId, data.getUnitId());
		queryWrapper.eq(OwnerUnitStationPic::getModule, data.getModule());
		queryWrapper.eq(OwnerUnitStationPic::getRounds, data.getRounds());
		if (data.getPileId() != null) {
			queryWrapper.apply(" {0} member of(pile_ids) ", data.getPileId());
		}
		queryWrapper.last(" LIMIT 1 ");
		return this.baseMapper.selectOne(queryWrapper);
	}

	@Override
	public List<OwnerUnitStationPic> getStationPicsByUnitId(Long unitId, Integer rounds) {
		LambdaQueryWrapper<OwnerUnitStationPic> queryWrapper = new LambdaQueryWrapper<OwnerUnitStationPic>();
		queryWrapper.eq(OwnerUnitStationPic::getUnitId, unitId);
		queryWrapper.eq(OwnerUnitStationPic::getRounds, rounds);
		return this.baseMapper.selectList(queryWrapper);
	}

}
