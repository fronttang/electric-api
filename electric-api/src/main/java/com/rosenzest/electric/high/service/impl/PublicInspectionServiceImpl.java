package com.rosenzest.electric.high.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.high.dto.PublicInspectionDto;
import com.rosenzest.electric.high.entity.PublicInspection;
import com.rosenzest.electric.high.mapper.PublicInspectionMapper;
import com.rosenzest.electric.high.service.IPublicInspectionService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 公共查阅 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Service
public class PublicInspectionServiceImpl extends ModelBaseServiceImpl<PublicInspectionMapper, PublicInspection>
		implements IPublicInspectionService {

	@Override
	public PublicInspection findPublicInspection(PublicInspectionDto data) {
		LambdaQueryWrapper<PublicInspection> queryWrapper = new LambdaQueryWrapper<PublicInspection>();
		queryWrapper.eq(PublicInspection::getProjectId, data.getProjectId());
		queryWrapper.eq(PublicInspection::getDistrict, data.getDistrict());
		queryWrapper.eq(PublicInspection::getStreet, data.getStreet());
		queryWrapper.eq(PublicInspection::getCommunity, data.getCommunity());
		queryWrapper.eq(PublicInspection::getType, data.getType());
		queryWrapper.last(" LIMIT 1 ");

		return this.baseMapper.selectOne(queryWrapper);
	}

	@Override
	public PublicInspection savePublicInspection(PublicInspectionDto data) {

		PublicInspection publicInspection = findPublicInspection(data);

		if (publicInspection == null) {
			publicInspection = new PublicInspection();
		}

		BeanUtils.copyProperties(data, publicInspection);

		this.saveOrUpdate(publicInspection);
		return publicInspection;
	}

}
