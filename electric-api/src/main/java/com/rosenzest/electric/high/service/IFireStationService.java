package com.rosenzest.electric.high.service;

import java.util.List;

import javax.validation.Valid;

import com.rosenzest.electric.high.dto.FireStationQuery;
import com.rosenzest.electric.high.entity.FireStation;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 消防站 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
public interface IFireStationService extends IModelBaseService<FireStation> {

	List<FireStation> fireStationList(@Valid FireStationQuery query);

}
