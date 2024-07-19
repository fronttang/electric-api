package com.rosenzest.electric.high.service.impl;

import org.springframework.stereotype.Service;

import com.rosenzest.electric.enums.HighRiskType;
import com.rosenzest.electric.high.config.IndustrialConfig;
import com.rosenzest.electric.high.dto.UnitIndustrialDto;
import com.rosenzest.electric.high.service.IIndustrialConfigService;
import com.rosenzest.electric.high.vo.UnitIndustrialVo;

/**
 * <p>
 * 工业企业 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Service
public class IndustrialConfigServiceImpl
		extends BaseHighConfigServiceImpl<IndustrialConfig, UnitIndustrialDto, UnitIndustrialVo>
		implements IIndustrialConfigService {

	@Override
	protected HighRiskType getHighRiskType() {
		return HighRiskType.INDUSTRIAL;
	}

}
