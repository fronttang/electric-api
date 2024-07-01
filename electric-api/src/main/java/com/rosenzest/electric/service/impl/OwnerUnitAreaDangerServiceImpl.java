package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.OwnerUnitAreaDanger;
import com.rosenzest.electric.mapper.OwnerUnitAreaDangerMapper;
import com.rosenzest.electric.service.IOwnerUnitAreaDangerService;
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
public class OwnerUnitAreaDangerServiceImpl extends ModelBaseServiceImpl<OwnerUnitAreaDangerMapper, OwnerUnitAreaDanger> implements IOwnerUnitAreaDangerService {

	@Override
	public List<OwnerUnitAreaDanger> getByUnitAreaId(Long unitAreaId) {
		LambdaQueryWrapper<OwnerUnitAreaDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitAreaDanger>();
		queryWrapper.eq(OwnerUnitAreaDanger::getUnitAreaId, unitAreaId);
		return this.baseMapper.selectList(queryWrapper);
	}

}
