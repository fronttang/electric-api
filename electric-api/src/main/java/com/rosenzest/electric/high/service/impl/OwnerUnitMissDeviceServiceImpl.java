package com.rosenzest.electric.high.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.high.entity.OwnerUnitMissDevice;
import com.rosenzest.electric.high.mapper.OwnerUnitMissDeviceMapper;
import com.rosenzest.electric.high.service.IOwnerUnitMissDeviceService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 缺失设备 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Service
public class OwnerUnitMissDeviceServiceImpl extends ModelBaseServiceImpl<OwnerUnitMissDeviceMapper, OwnerUnitMissDevice>
		implements IOwnerUnitMissDeviceService {

	@Override
	public List<OwnerUnitMissDevice> findByUnitId(Long unitId) {
		LambdaQueryWrapper<OwnerUnitMissDevice> queryWrapper = new LambdaQueryWrapper<OwnerUnitMissDevice>();
		queryWrapper.eq(OwnerUnitMissDevice::getUnitId, unitId);
		return this.baseMapper.selectList(queryWrapper);
	}

}
