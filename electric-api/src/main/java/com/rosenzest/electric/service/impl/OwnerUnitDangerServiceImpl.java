package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.mapper.OwnerUnitDangerMapper;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
@Service
public class OwnerUnitDangerServiceImpl extends ModelBaseServiceImpl<OwnerUnitDangerMapper, OwnerUnitDanger> implements IOwnerUnitDangerService {

	@Override
	public List<OwnerUnitDanger> getByUnitAreaId(Long unitAreaId) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.eq(OwnerUnitDanger::getUnitAreaId, unitAreaId);
		return this.baseMapper.selectList(queryWrapper);
	}

}
