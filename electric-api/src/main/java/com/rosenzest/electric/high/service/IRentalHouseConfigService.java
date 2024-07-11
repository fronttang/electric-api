package com.rosenzest.electric.high.service;

import com.rosenzest.electric.high.dto.UnitRentalHouseDto;
import com.rosenzest.electric.high.entity.RentalHouseConfig;
import com.rosenzest.electric.high.vo.UnitRentalHouseVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 出租屋消防配置 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-11
 */
public interface IRentalHouseConfigService extends IModelBaseService<RentalHouseConfig> {

	boolean saveOwnerUnit(UnitRentalHouseDto data);

	RentalHouseConfig getByUnitId(Long unitId);

	UnitRentalHouseVo getRentalHouseById(Long unitId);

}
