package com.rosenzest.electric.high.service.impl;

import org.springframework.stereotype.Service;

import com.rosenzest.electric.enums.HighRiskType;
import com.rosenzest.electric.high.dto.UnitComplexDto;
import com.rosenzest.electric.high.entity.ComplexConfig;
import com.rosenzest.electric.high.mapper.ComplexConfigMapper;
import com.rosenzest.electric.high.service.IComplexConfigService;
import com.rosenzest.electric.high.vo.UnitComplexVo;

/**
 * <p>
 * 工业企业 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Service
public class ComplexConfigServiceImpl
		extends BaseHighConfigServiceImpl<ComplexConfigMapper, ComplexConfig, UnitComplexDto, UnitComplexVo>
		implements IComplexConfigService {

	@Override
	protected HighRiskType getHighRiskType() {
		return HighRiskType.COMPLEX;
	}

}
