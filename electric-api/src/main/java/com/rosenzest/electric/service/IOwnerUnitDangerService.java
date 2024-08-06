package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.electric.dto.DangerNotPassDto;
import com.rosenzest.electric.dto.DangerPassDto;
import com.rosenzest.electric.dto.OwnerUnitDangerQuery;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.electric.miniapp.vo.GridmanDangerStatisticsVo;
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

	List<OwnerUnitDangerVo> queryOwnerUnitDanger(OwnerUnitDangerQuery query, PageList pageList);

	Integer countByUnitAreaId(Long unitAreaId);

	Integer countByUnitId(Long unitId);

	boolean saveOrUpdateDanger(OwnerUnitDanger danger);

	boolean removeDanger(OwnerUnitDanger danger);

	boolean pass(DangerPassDto data);

	boolean notPass(DangerNotPassDto data);

	Integer countByBuildingId(Long buildingId);

	Integer countByChargingPileId(Long pileId);

	Integer countFormDangers(Long formId, Long unitId, Long unitAreaId, Long buildingId);

	Integer countFormbDangers(String code, Long unitId, Long unitAreaId, Long buildingId);

	Integer countByDataIdAndPileId(Long id, Long pileId);

	List<OwnerUnitDanger> getDangersByUnitIdAndBuildingIds(Long unitId, List<Long> buildingIds);

	boolean rectification(DangerPassDto data);

	GridmanDangerStatisticsVo statisticsByGridman(LoginUser user);

	List<OwnerUnitDanger> getOwnerUnitDangerByAreaUser(AreaUserInfoVo userInfo);

	List<OwnerUnitDanger> getTodayDangersByAreaUser(AreaUserInfoVo userInfo);
}
