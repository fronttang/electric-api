package com.rosenzest.electric.design.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.electric.design.dto.ElectricityProjectDeviceImageQuery;
import com.rosenzest.electric.design.entity.ElectricityProjectDeviceImage;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 设备照片集 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
public interface IElectricityProjectDeviceImageService extends IModelBaseService<ElectricityProjectDeviceImage> {

	Page<ElectricityProjectDeviceImage> queryList(ElectricityProjectDeviceImageQuery query);

}
