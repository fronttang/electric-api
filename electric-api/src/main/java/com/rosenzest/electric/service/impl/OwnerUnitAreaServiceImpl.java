package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rosenzest.base.PageList;
import com.rosenzest.electric.dto.OwnerUnitAreaQuery;
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
	public List<OwnerUnitAreaVo> queryUnitAreaByType(OwnerUnitAreaQuery query, PageList pageList) {
		Page<OwnerUnitAreaVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
		startPage.setReasonable(false);
		List<OwnerUnitAreaVo> list = this.baseMapper.queryUnitAreaByType(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

	@Override
	public int removeByUnitId(Long unitId) {
		LambdaQueryWrapper<OwnerUnitArea> queryWrapper = new LambdaQueryWrapper<OwnerUnitArea>();
		queryWrapper.eq(OwnerUnitArea::getUnitId, unitId);
		return this.baseMapper.delete(queryWrapper);
	}

	@Override
	public OwnerUnitArea getByUnitId(Long unitId) {
		LambdaQueryWrapper<OwnerUnitArea> queryWrapper = new LambdaQueryWrapper<OwnerUnitArea>();
		queryWrapper.eq(OwnerUnitArea::getUnitId, unitId);
		queryWrapper.last(" limit 1 ");
		return this.baseMapper.selectOne(queryWrapper);
	}

	@Override
	public OwnerUnitArea getByUnitIdAndBuildingId(Long unitId, Long buildingId) {
		LambdaQueryWrapper<OwnerUnitArea> queryWrapper = new LambdaQueryWrapper<OwnerUnitArea>();
		queryWrapper.eq(OwnerUnitArea::getUnitId, unitId);
		queryWrapper.eq(OwnerUnitArea::getBuildingId, buildingId);
		queryWrapper.last(" limit 1 ");
		return this.baseMapper.selectOne(queryWrapper);
	}

}
