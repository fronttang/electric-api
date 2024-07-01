package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.OwnerUnitAreaDanger;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
public interface IOwnerUnitAreaDangerService extends IModelBaseService<OwnerUnitAreaDanger> {

	List<OwnerUnitAreaDanger> getByUnitAreaId(Long unitAreaId);

}
