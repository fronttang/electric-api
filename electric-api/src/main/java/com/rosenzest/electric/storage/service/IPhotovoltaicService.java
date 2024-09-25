package com.rosenzest.electric.storage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.electric.storage.dto.PhotovoltaicQuery;
import com.rosenzest.electric.storage.entity.Photovoltaic;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 光伏 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
public interface IPhotovoltaicService extends IModelBaseService<Photovoltaic> {

	Page<Photovoltaic> queryList(PhotovoltaicQuery query);

}
