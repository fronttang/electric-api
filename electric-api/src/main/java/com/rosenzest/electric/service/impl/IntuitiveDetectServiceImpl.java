package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.IntuitiveDetect;
import com.rosenzest.electric.mapper.IntuitiveDetectMapper;
import com.rosenzest.electric.service.IIntuitiveDetectService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 直观检测表标题 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
@Service
public class IntuitiveDetectServiceImpl extends ModelBaseServiceImpl<IntuitiveDetectMapper, IntuitiveDetect>
		implements IIntuitiveDetectService {

	@Override
	public List<IntuitiveDetect> getIntuitiveDetectByTemplateId(Long templateId) {
		LambdaQueryWrapper<IntuitiveDetect> queryWrapper = new LambdaQueryWrapper<IntuitiveDetect>();
		queryWrapper.eq(IntuitiveDetect::getTemplateId, templateId);
		return this.baseMapper.selectList(queryWrapper);
	}

	@Override
	public Integer getFormDangers(Long formId, Long unitId, Long unitAreaId, Long buildingId) {
		return this.baseMapper.getFormDangers(formId, unitId, unitAreaId, buildingId);
	}

	@Override
	public Integer getFormbDangers(String code, Long unitId, Long unitAreaId, Long buildingId) {
		return this.baseMapper.getFormbDangers(code, unitId, unitAreaId, buildingId);
	}

}
