package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.dto.OwnerUnitDangerQuery;
import com.rosenzest.electric.dto.UnitAreaDangerQuery;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.mapper.OwnerUnitDangerMapper;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
@Service
public class OwnerUnitDangerServiceImpl extends ModelBaseServiceImpl<OwnerUnitDangerMapper, OwnerUnitDanger>
		implements IOwnerUnitDangerService {

	@Override
	public List<OwnerUnitDanger> getByUnitAreaId(Long unitAreaId) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.eq(OwnerUnitDanger::getUnitAreaId, unitAreaId);
		return this.baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<OwnerUnitDangerVo> queryOwnerUnitDanger(OwnerUnitDangerQuery query) {
		return this.baseMapper.queryOwnerUnitDanger(query);
	}

	@Override
	public Integer countByUnitAreaId(Long unitAreaId) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.eq(OwnerUnitDanger::getUnitAreaId, unitAreaId);
		return this.baseMapper.selectCount(queryWrapper);
	}

	@Override
	public Integer countByUnitId(Long unitId) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.eq(OwnerUnitDanger::getUnitId, unitId);
		return this.baseMapper.selectCount(queryWrapper);
	}

	@Override
	public List<OwnerUnitDanger> selectByCondition(UnitAreaDangerQuery query) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		if (query.getUnitAreaId() != null) {
			queryWrapper.eq(OwnerUnitDanger::getUnitAreaId, query.getUnitAreaId());
		}
		if (query.getUnitId() != null) {
			queryWrapper.eq(OwnerUnitDanger::getUnitId, query.getUnitId());
		}
		if (query.getBuildingId() != null) {
			queryWrapper.eq(OwnerUnitDanger::getBuildingId, query.getBuildingId());
		}

		if ("B".equalsIgnoreCase(query.getType())) {
			if (StrUtil.isNotBlank(query.getFormCode())) {
				queryWrapper.eq(OwnerUnitDanger::getFormCode, query.getFormCode());
			}
		} else {
			if (query.getFormId() != null) {
				queryWrapper.eq(OwnerUnitDanger::getFormId, query.getFormId());
			}
		}

		return this.baseMapper.selectList(queryWrapper);
	}

}
