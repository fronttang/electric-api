package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rosenzest.electric.entity.OwnerUnitArea;
import com.rosenzest.electric.mapper.OwnerUnitAreaMapper;
import com.rosenzest.electric.service.IOwnerUnitAreaService;
import com.rosenzest.electric.vo.OwnerUnitAreaVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
@Service
public class OwnerUnitAreaServiceImpl extends ModelBaseServiceImpl<OwnerUnitAreaMapper, OwnerUnitArea>
		implements IOwnerUnitAreaService {

	@Override
	public List<OwnerUnitAreaVo> queryUnitAreaByType(Long unitId, Long buildingId, String type, String keyword) {

		return this.baseMapper.queryUnitAreaByType(unitId, buildingId, type, keyword);
	}

}
