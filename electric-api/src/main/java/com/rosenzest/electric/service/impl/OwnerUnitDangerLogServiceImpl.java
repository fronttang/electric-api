package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.OwnerUnitDangerLog;
import com.rosenzest.electric.mapper.OwnerUnitDangerLogMapper;
import com.rosenzest.electric.service.IOwnerUnitDangerLogService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-08
 */
@Service
public class OwnerUnitDangerLogServiceImpl extends ModelBaseServiceImpl<OwnerUnitDangerLogMapper, OwnerUnitDangerLog>
		implements IOwnerUnitDangerLogService {

	@Override
	public List<OwnerUnitDangerLog> listByDangerId(Long dangerId) {
		LambdaQueryWrapper<OwnerUnitDangerLog> queryWrapper = new LambdaQueryWrapper<OwnerUnitDangerLog>();
		queryWrapper.eq(OwnerUnitDangerLog::getDangerId, dangerId);

		return this.baseMapper.selectList(queryWrapper);
	}

}
