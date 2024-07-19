package com.rosenzest.electric.high.service.impl;

import org.springframework.stereotype.Service;

import com.rosenzest.electric.enums.HighRiskType;
import com.rosenzest.electric.high.config.PublicPlacesConfig;
import com.rosenzest.electric.high.dto.UnitPublicPlacesDto;
import com.rosenzest.electric.high.service.IPublicPlacesConfigService;
import com.rosenzest.electric.high.vo.UnitPublicPlacesVo;

/**
 * <p>
 * 公共场所 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Service
public class PublicPlacesConfigServiceImpl
		extends BaseHighConfigServiceImpl<PublicPlacesConfig, UnitPublicPlacesDto, UnitPublicPlacesVo>
		implements IPublicPlacesConfigService {

	@Override
	protected HighRiskType getHighRiskType() {
		return HighRiskType.PUBLIC_PLACES;
	}

}
