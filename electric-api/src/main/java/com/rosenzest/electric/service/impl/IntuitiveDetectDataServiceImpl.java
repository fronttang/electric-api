package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.IntuitiveDetectData;
import com.rosenzest.electric.enums.HighRiskType;
import com.rosenzest.electric.mapper.IntuitiveDetectDataMapper;
import com.rosenzest.electric.service.IIntuitiveDetectDataService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 检测表内容 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
@Service
public class IntuitiveDetectDataServiceImpl extends ModelBaseServiceImpl<IntuitiveDetectDataMapper, IntuitiveDetectData>
		implements IIntuitiveDetectDataService {

	@SuppressWarnings("unchecked")
	@Override
	public List<IntuitiveDetectData> getByDetectId(Long formId, HighRiskType type) {
		LambdaQueryWrapper<IntuitiveDetectData> queryWrapper = new LambdaQueryWrapper<IntuitiveDetectData>();
		queryWrapper.eq(IntuitiveDetectData::getDetectTitle, formId);
		if (type != null) {
			queryWrapper.eq(IntuitiveDetectData::getUnitType, type.code());
		}
		queryWrapper.eq(IntuitiveDetectData::getView, "1");
		queryWrapper.orderByAsc(IntuitiveDetectData::getWeights);
		return this.baseMapper.selectList(queryWrapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntuitiveDetectData> getByViewParent(Long viewParentId, HighRiskType type) {
		LambdaQueryWrapper<IntuitiveDetectData> queryWrapper = new LambdaQueryWrapper<IntuitiveDetectData>();
		queryWrapper.eq(IntuitiveDetectData::getViewParentId, viewParentId);
		queryWrapper.eq(IntuitiveDetectData::getUnitType, type.code());
		queryWrapper.eq(IntuitiveDetectData::getView, "2");
		queryWrapper.orderByAsc(IntuitiveDetectData::getWeights);
		return this.baseMapper.selectList(queryWrapper);
	}

}
