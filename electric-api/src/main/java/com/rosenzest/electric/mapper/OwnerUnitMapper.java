package com.rosenzest.electric.mapper;

import java.util.List;

import com.rosenzest.electric.dto.OwnerUnitAgainQuery;
import com.rosenzest.electric.dto.OwnerUnitQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.vo.AgainOwnerUnitVo;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitVo;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * 业主单元 Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
public interface OwnerUnitMapper extends ModelBaseMapper<OwnerUnit> {

	List<InitialOwnerUnitVo> queryInitialList(OwnerUnitQuery query);

	OwnerUnitVo getOwnerUnitById(Long unitId);

	List<AgainOwnerUnitVo> queryAginList(OwnerUnitAgainQuery query);

}
