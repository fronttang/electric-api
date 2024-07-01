package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.constant.ElectricConstant;
import com.rosenzest.electric.entity.SysDictType;
import com.rosenzest.electric.mapper.SysDictTypeMapper;
import com.rosenzest.electric.service.ISysDictTypeService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Service
public class SysDictTypeServiceImpl extends ModelBaseServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

	@Override
	public List<SysDictType> getBusinessDictType() {
		LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<SysDictType>();
		queryWrapper.in(SysDictType::getDictType, ElectricConstant.BUSINESS_DICT_TYPES);
		return this.baseMapper.selectList(queryWrapper);
	}

}
