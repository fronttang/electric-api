package com.rosenzest.electric.high.service;

import com.rosenzest.electric.high.dto.BaseHighDto;

public interface BaseHighConfigService<C, DTO extends BaseHighDto, VO> extends IOwnerUnitConfigService {

	boolean saveOwnerUnit(DTO data) throws Exception;

	VO getOwnerUnitById(Long unitId) throws Exception;
}
