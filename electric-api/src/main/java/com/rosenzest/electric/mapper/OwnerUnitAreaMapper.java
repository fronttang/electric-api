package com.rosenzest.electric.mapper;

import java.util.List;

import com.rosenzest.electric.dto.OwnerUnitAreaQuery;
import com.rosenzest.electric.entity.OwnerUnitArea;
import com.rosenzest.electric.vo.OwnerUnitAreaVo;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
public interface OwnerUnitAreaMapper extends ModelBaseMapper<OwnerUnitArea> {

	List<OwnerUnitAreaVo> queryUnitAreaByType(OwnerUnitAreaQuery query);

}
