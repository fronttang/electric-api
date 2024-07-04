package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
public interface IOwnerUnitDangerService extends IModelBaseService<OwnerUnitDanger> {

	List<OwnerUnitDanger> getByUnitAreaId(Long unitAreaId);

}
