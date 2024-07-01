package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.OwnerUnitArea;
import com.rosenzest.electric.vo.OwnerUnitAreaVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
public interface IOwnerUnitAreaService extends IModelBaseService<OwnerUnitArea> {

	List<OwnerUnitAreaVo> queryUnitAreaByType(Long unitId, String type);

}
