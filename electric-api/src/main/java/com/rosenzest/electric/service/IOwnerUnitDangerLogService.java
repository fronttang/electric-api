package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.OwnerUnitDangerLog;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-08
 */
public interface IOwnerUnitDangerLogService extends IModelBaseService<OwnerUnitDangerLog> {

	List<OwnerUnitDangerLog> listByDangerId(Long dangerId);

}
