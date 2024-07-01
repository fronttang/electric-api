package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.dto.UserDeviceDto;
import com.rosenzest.electric.entity.DetectDevice;
import com.rosenzest.electric.entity.UserDevice;
import com.rosenzest.electric.mapper.UserDeviceMapper;
import com.rosenzest.electric.service.IUserDeviceService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 用户设备 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Service
public class UserDeviceServiceImpl extends ModelBaseServiceImpl<UserDeviceMapper, UserDevice> implements IUserDeviceService {

	@Override
	public List<DetectDevice> getUserDeviceList(Long userId) {
		return this.baseMapper.getUserDeviceList(userId);
	}

	@Override
	public boolean saveUserDevice(Long userId, UserDeviceDto data) {
		
		LambdaQueryWrapper<UserDevice> queryWrapper = new LambdaQueryWrapper<UserDevice>();
		queryWrapper.in(UserDevice::getDeviceType, data.getDeviceType());
		queryWrapper.in(UserDevice::getUserId, userId);
		queryWrapper.last("LIMIT 1");
		UserDevice userDevice = this.getOne(queryWrapper);
		if(userDevice == null) {
			userDevice = new UserDevice();
			userDevice.setUserId(userId);
		}
		userDevice.setDeviceType(data.getDeviceType());
		userDevice.setDeviceId(data.getDeviceId());
		
		return this.saveOrUpdate(userDevice);
	}

}
