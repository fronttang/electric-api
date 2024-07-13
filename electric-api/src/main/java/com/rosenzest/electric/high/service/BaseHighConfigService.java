package com.rosenzest.electric.high.service;

import com.rosenzest.electric.high.dto.BaseHighDto;
import com.rosenzest.model.base.service.IModelBaseService;

public interface BaseHighConfigService<T, DTO extends BaseHighDto, VO> extends IModelBaseService<T> {

	boolean saveOwnerUnit(DTO data) throws Exception;

	VO getOwnerUnitById(Long unitId) throws Exception;
}
