package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.base.PageList;
import com.rosenzest.electric.dto.OwnerUnitAreaQuery;
import com.rosenzest.electric.entity.OwnerUnitArea;
import com.rosenzest.electric.vo.OwnerUnitAreaVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
public interface IOwnerUnitAreaService extends IModelBaseService<OwnerUnitArea> {

	List<OwnerUnitAreaVo> queryUnitAreaByType(OwnerUnitAreaQuery query, PageList pageList);

	int removeByUnitId(Long unitId);

	OwnerUnitArea getByUnitId(Long unitId);

	OwnerUnitArea getByUnitIdAndBuildingId(Long unitId, Long buildingId);

}
