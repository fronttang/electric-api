package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.IntuitiveDetectData;
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
public class IntuitiveDetectDataServiceImpl extends ModelBaseServiceImpl<IntuitiveDetectDataMapper, IntuitiveDetectData> implements IIntuitiveDetectDataService {

	@Override
	public List<IntuitiveDetectData> getByDetectId(Long titleId) {
		LambdaQueryWrapper<IntuitiveDetectData> queryWrapper = new LambdaQueryWrapper<IntuitiveDetectData>();
		queryWrapper.eq(IntuitiveDetectData::getDetectTitle, titleId);
		queryWrapper.eq(IntuitiveDetectData::getView, "1");
		return this.baseMapper.selectList(queryWrapper);
	}

}
