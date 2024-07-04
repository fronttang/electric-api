package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.DetectDevice;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 检测设备 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
public interface IDetectDeviceService extends IModelBaseService<DetectDevice> {

	List<DetectDevice> getByDetectId(Long detectId, String type);

}
