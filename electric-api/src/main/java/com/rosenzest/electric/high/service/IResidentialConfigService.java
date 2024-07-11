package com.rosenzest.electric.high.service;

import com.rosenzest.electric.high.dto.UnitResidentialDto;
import com.rosenzest.electric.high.entity.ResidentialConfig;
import com.rosenzest.electric.high.vo.UnitResidentialVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 住宅小区 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-11
 */
public interface IResidentialConfigService extends IModelBaseService<ResidentialConfig> {

	ResidentialConfig getByUnitId(Long unitId);

	boolean saveOwnerUnit(UnitResidentialDto data);

	UnitResidentialVo getResidentialById(Long unitId);

}
