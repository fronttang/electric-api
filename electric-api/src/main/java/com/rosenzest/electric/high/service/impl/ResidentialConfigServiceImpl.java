package com.rosenzest.electric.high.service.impl;

import org.springframework.stereotype.Service;

import com.rosenzest.electric.enums.HighRiskType;
import com.rosenzest.electric.high.config.ResidentialConfig;
import com.rosenzest.electric.high.dto.UnitResidentialDto;
import com.rosenzest.electric.high.service.IResidentialConfigService;
import com.rosenzest.electric.high.vo.UnitResidentialVo;

/**
 * <p>
 * 住宅小区 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-11
 */
@Service
public class ResidentialConfigServiceImpl
		extends BaseHighConfigServiceImpl<ResidentialConfig, UnitResidentialDto, UnitResidentialVo>
		implements IResidentialConfigService {

	@Override
	protected HighRiskType getHighRiskType() {
		return HighRiskType.RESIDENTIAL;
	}

}
