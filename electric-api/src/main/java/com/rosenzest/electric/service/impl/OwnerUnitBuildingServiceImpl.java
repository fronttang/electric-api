package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rosenzest.base.PageList;
import com.rosenzest.electric.dto.OwnerUnitBuildingQuery;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.mapper.OwnerUnitBuildingMapper;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.vo.InitialOwnerUnitBuildingVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Service
public class OwnerUnitBuildingServiceImpl extends ModelBaseServiceImpl<OwnerUnitBuildingMapper, OwnerUnitBuilding>
		implements IOwnerUnitBuildingService {

	@Override
	public List<InitialOwnerUnitBuildingVo> queryInitialList(OwnerUnitBuildingQuery query, PageList pageList) {

		Page<InitialOwnerUnitBuildingVo> startPage = PageHelper.startPage(pageList.getPageNum(),
				pageList.getPageSize());
		List<InitialOwnerUnitBuildingVo> list = this.baseMapper.queryInitialList(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

}
