package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.constant.ElectricConstant;
import com.rosenzest.electric.entity.SysDictData;
import com.rosenzest.electric.mapper.SysDictDataMapper;
import com.rosenzest.electric.service.ISysDictDataService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Service
public class SysDictDataServiceImpl extends ModelBaseServiceImpl<SysDictDataMapper, SysDictData>
		implements ISysDictDataService {

	@Override
	public List<SysDictData> getBusinessDictData() {
		LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<SysDictData>();
		queryWrapper.in(SysDictData::getDictType, ElectricConstant.BUSINESS_DICT_TYPES);
		return this.baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<SysDictData> getBrandDict(Long detectId, String keyword) {
		LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<SysDictData>();
		queryWrapper.eq(SysDictData::getDictType, ElectricConstant.BRAND_DICT_TYPE);
		queryWrapper.eq(SysDictData::getDetectId, detectId);
		if (StrUtil.isNotBlank(keyword)) {
			queryWrapper.like(SysDictData::getDictLabel, keyword);
		}
		queryWrapper.last(" LIMIT 100 ");
		return this.baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<SysDictData> getDictDataByType(String dictType) {
		LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<SysDictData>();
		queryWrapper.in(SysDictData::getDictType, dictType);
		return this.baseMapper.selectList(queryWrapper);
	}

}
