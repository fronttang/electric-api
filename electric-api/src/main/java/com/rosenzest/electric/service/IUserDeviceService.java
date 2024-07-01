package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.dto.UserDeviceDto;
import com.rosenzest.electric.entity.DetectDevice;
import com.rosenzest.electric.entity.UserDevice;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 用户设备 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
public interface IUserDeviceService extends IModelBaseService<UserDevice> {

	List<DetectDevice> getUserDeviceList(Long userId);

	boolean saveUserDevice(Long userId, UserDeviceDto data);

}
