package com.rosenzest.electric.high.service.impl;

import org.springframework.stereotype.Service;

import com.rosenzest.electric.enums.HighRiskType;
import com.rosenzest.electric.high.dto.UnitRentalHouseDto;
import com.rosenzest.electric.high.entity.RentalHouseConfig;
import com.rosenzest.electric.high.mapper.RentalHouseConfigMapper;
import com.rosenzest.electric.high.service.IRentalHouseConfigService;
import com.rosenzest.electric.high.vo.UnitRentalHouseVo;

/**
 * <p>
 * 出租屋消防配置 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-11
 */
@Service
public class RentalHouseConfigServiceImpl extends
		BaseHighConfigServiceImpl<RentalHouseConfigMapper, RentalHouseConfig, UnitRentalHouseDto, UnitRentalHouseVo>
		implements IRentalHouseConfigService {

	@Override
	protected HighRiskType getHighRiskType() {
		return HighRiskType.RENTAL_HOUSE;
	}

}
