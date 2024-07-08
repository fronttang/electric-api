package com.rosenzest.electric.service;

import java.util.List;

import javax.validation.Valid;

import com.rosenzest.electric.dto.DangerNotPassDto;
import com.rosenzest.electric.dto.DangerPassDto;
import com.rosenzest.electric.dto.OwnerUnitAgainDangerQuery;
import com.rosenzest.electric.dto.OwnerUnitDangerQuery;
import com.rosenzest.electric.dto.UnitAreaDangerQuery;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.vo.OwnerUnitAgainDangerVo;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
public interface IOwnerUnitDangerService extends IModelBaseService<OwnerUnitDanger> {

	List<OwnerUnitDanger> getByUnitAreaId(Long unitAreaId);

	List<OwnerUnitDangerVo> queryOwnerUnitDanger(OwnerUnitDangerQuery query);

	Integer countByUnitAreaId(Long unitAreaId);

	Integer countByUnitId(Long unitId);

	List<OwnerUnitDanger> selectByCondition(UnitAreaDangerQuery query);

	boolean saveOrUpdateDanger(OwnerUnitDanger danger);

	boolean removeDanger(OwnerUnitDanger danger);

	List<OwnerUnitAgainDangerVo> queryOwnerUnitAgainDanger(OwnerUnitAgainDangerQuery query);

	boolean pass(DangerPassDto data);

	boolean notPass(@Valid DangerNotPassDto data);

}
