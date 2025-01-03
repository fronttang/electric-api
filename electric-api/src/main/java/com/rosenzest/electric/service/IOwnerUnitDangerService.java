package com.rosenzest.electric.service;

import java.util.List;

import javax.validation.Valid;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.electric.dto.DangerNotPassDto;
import com.rosenzest.electric.dto.DangerPassDto;
import com.rosenzest.electric.dto.OwnerUnitDangerQuery;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.miniapp.dto.MiniAppAreaQuery;
import com.rosenzest.electric.miniapp.vo.AreaUserDangerVo;
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

	Integer countByUnitIdAndDataIdAndPileId(Long unitId, Long dataId, Long pileId);

	List<OwnerUnitDanger> getDangersByUnitIdAndBuildingIds(Long unitId, List<Long> buildingIds);

	boolean rectification(DangerPassDto data);

	GridmanDangerStatisticsVo statisticsByGridman(LoginUser user);

	List<OwnerUnitDanger> getOwnerUnitDangerByGridman(Long userId, String type);

	List<AreaUserDangerVo> getOwnerUnitDangerByAreaUser(AreaUserInfoVo userInfo);

	List<OwnerUnitDanger> getTodayDangersByAreaUser(AreaUserInfoVo userInfo);

	OwnerUnitDangerVo getOwnerUnitDangerById(Long dangerId);

	List<OwnerUnitDangerVo> queryReviewOwnerUnitDanger(@Valid OwnerUnitDangerQuery query, PageList pageList);

	List<AreaUserDangerVo> getOwnerUnitDangerByArea(MiniAppAreaQuery query);
}
