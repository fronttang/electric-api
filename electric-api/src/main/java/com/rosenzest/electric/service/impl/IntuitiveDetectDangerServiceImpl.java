package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.IntuitiveDetectDanger;
import com.rosenzest.electric.mapper.IntuitiveDetectDangerMapper;
import com.rosenzest.electric.service.IIntuitiveDetectDangerService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 检测表内容隐患 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
@Service
public class IntuitiveDetectDangerServiceImpl extends ModelBaseServiceImpl<IntuitiveDetectDangerMapper, IntuitiveDetectDanger> implements IIntuitiveDetectDangerService {

	@Override
	public List<IntuitiveDetectDanger> getByDataId(Long dataId) {
		LambdaQueryWrapper<IntuitiveDetectDanger> queryWrapper = new LambdaQueryWrapper<IntuitiveDetectDanger>();
		queryWrapper.eq(IntuitiveDetectDanger::getDataId, dataId);
		return this.baseMapper.selectList(queryWrapper);
	}

}
