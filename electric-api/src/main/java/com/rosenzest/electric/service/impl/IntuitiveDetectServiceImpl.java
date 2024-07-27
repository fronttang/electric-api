package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.IntuitiveDetect;
import com.rosenzest.electric.enums.HighRiskType;
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
	public List<IntuitiveDetect> getIntuitiveDetectByTemplateId(Long templateId, String attribution,
			HighRiskType type) {
		LambdaQueryWrapper<IntuitiveDetect> queryWrapper = new LambdaQueryWrapper<IntuitiveDetect>();
		queryWrapper.eq(IntuitiveDetect::getTemplateId, templateId);
		if (type != null) {
			queryWrapper.eq(IntuitiveDetect::getUnitType, type.code());
		}
		if (attribution != null) {
			queryWrapper.apply(" {0} member of(attribution) ", attribution);
		}
		return this.baseMapper.selectList(queryWrapper);
	}

}
