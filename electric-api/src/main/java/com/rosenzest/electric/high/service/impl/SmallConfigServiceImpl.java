package com.rosenzest.electric.high.service.impl;

import org.springframework.stereotype.Service;

import com.rosenzest.electric.enums.HighRiskType;
import com.rosenzest.electric.high.dto.UnitSmallDto;
import com.rosenzest.electric.high.entity.SmallConfig;
import com.rosenzest.electric.high.mapper.SmallConfigMapper;
import com.rosenzest.electric.high.service.ISmallConfigService;
import com.rosenzest.electric.high.vo.UnitSmallVo;

/**
 * <p>
 * 三小场所 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Service
public class SmallConfigServiceImpl
		extends BaseHighConfigServiceImpl<SmallConfigMapper, SmallConfig, UnitSmallDto, UnitSmallVo>
		implements ISmallConfigService {

	@Override
	protected HighRiskType getHighRiskType() {
		return HighRiskType.SMALL;
	}

}
