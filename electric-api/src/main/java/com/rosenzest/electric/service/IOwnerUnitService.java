package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.base.PageList;
import com.rosenzest.electric.dto.OwnerUnitAgainQuery;
import com.rosenzest.electric.dto.OwnerUnitDto;
import com.rosenzest.electric.dto.OwnerUnitQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.vo.AgainOwnerUnitVo;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 业主单元 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
public interface IOwnerUnitService extends IModelBaseService<OwnerUnit> {

	List<InitialOwnerUnitVo> queryInitialList(OwnerUnitQuery query, PageList pageList);

	OwnerUnitVo getOwnerUnitById(Long unitId);

	boolean saveOwnerUnit(OwnerUnitDto data);

	List<AgainOwnerUnitVo> queryAginList(OwnerUnitAgainQuery query, PageList pageList);

}
