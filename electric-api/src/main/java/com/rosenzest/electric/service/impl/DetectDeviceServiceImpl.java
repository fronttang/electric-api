package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.DetectDevice;
import com.rosenzest.electric.mapper.DetectDeviceMapper;
import com.rosenzest.electric.service.IDetectDeviceService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 检测设备 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Service
public class DetectDeviceServiceImpl extends ModelBaseServiceImpl<DetectDeviceMapper, DetectDevice> implements IDetectDeviceService {

	@Override
	public List<DetectDevice> getByDetectId(Long detectId, String type) {
		
		LambdaQueryWrapper<DetectDevice> queryWrapper = new LambdaQueryWrapper<DetectDevice>();
		queryWrapper.eq(DetectDevice::getDetectId, detectId);
		if(StrUtil.isNotBlank(type)) {
			queryWrapper.eq(DetectDevice::getType, type);
		}
		return this.baseMapper.selectList(queryWrapper);
	}

}
