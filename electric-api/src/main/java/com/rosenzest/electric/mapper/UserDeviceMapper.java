package com.rosenzest.electric.mapper;

import java.util.List;

import com.rosenzest.electric.entity.DetectDevice;
import com.rosenzest.electric.entity.UserDevice;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * 用户设备 Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
public interface UserDeviceMapper extends ModelBaseMapper<UserDevice> {

	List<DetectDevice> getUserDeviceList(Long userId);
}
