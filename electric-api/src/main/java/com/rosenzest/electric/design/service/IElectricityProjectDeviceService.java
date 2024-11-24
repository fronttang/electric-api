package com.rosenzest.electric.design.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.electric.design.dto.ElectricityProjectDeviceQuery;
import com.rosenzest.electric.design.entity.ElectricityProjectDevice;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 电力设计项目设备 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
public interface IElectricityProjectDeviceService extends IModelBaseService<ElectricityProjectDevice> {

	Page<ElectricityProjectDevice> queryList(ElectricityProjectDeviceQuery query);

}
