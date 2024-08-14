package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.base.PageList;
import com.rosenzest.electric.dto.OwnerUnitDto;
import com.rosenzest.electric.dto.OwnerUnitQuery;
import com.rosenzest.electric.dto.OwnerUnitReviewQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.entity.SysDictData;
import com.rosenzest.electric.miniapp.dto.MiniAppAreaUserOwnerUnitQuery;
import com.rosenzest.electric.miniapp.dto.MiniAppOwnerUnitQuery;
import com.rosenzest.electric.miniapp.vo.AreaUserIndexVo;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.electric.miniapp.vo.IDangerStatisticsVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitDangerStatisticsVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitOverviewVo;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitReviewVo;
import com.rosenzest.electric.vo.OwnerUnitVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 业主单元 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
public interface IOwnerUnitService extends IModelBaseService<OwnerUnit> {

	List<InitialOwnerUnitVo> queryInitialList(OwnerUnitQuery query, PageList pageList);

	OwnerUnitVo getOwnerUnitById(Long unitId);

	boolean saveOwnerUnit(OwnerUnitDto data);

	List<OwnerUnitReviewVo> queryReviewList(OwnerUnitReviewQuery query, PageList pageList);

	boolean removeOwnerUnitById(Long unitId);

	boolean checkOwnerUnitName(OwnerUnit unit);

	List<OwnerUnitOverviewVo> getOwnerUnitListByOwner(Long userId, Long projectId);

	OwnerUnitOverviewVo getOwnerUnitInfoById(Long unitId);

	boolean checkOwnerUnitManager(Long unitId, Long userId);

	boolean checkOwnerUnitGridman(Long unitId, Long userId);

	OwnerUnitDangerStatisticsVo getOwnerUnitDangerStatistics(Long unitId, Long buildingId);

	OwnerUnitDangerStatisticsVo buildOwnerUnitDangerStatisticsVo(OwnerUnitOverviewVo ownerUnit,
			OwnerUnitBuilding building, List<OwnerUnitDanger> dangerLists, List<SysDictData> hazardLevel);

	void buildDangerStatisticsVo(List<OwnerUnitDanger> dangerLists, List<SysDictData> hazardLevel,
			IDangerStatisticsVo vo);

	List<SysDictData> getHazardLevel(String type);

	List<OwnerUnitOverviewVo> getOwnerUnitListByGridman(Long userId, Long projectId);

	List<OwnerUnitDangerStatisticsVo> getOwnerUnitDangerStatisticsByGridman(MiniAppOwnerUnitQuery query,
			PageList pageList);

	// UnitStatistics getUnitStatistics(AreaUserInfoVo userInfo);

	AreaUserIndexVo getAreaUserIndex(AreaUserInfoVo userInfo);

	List<OwnerUnitDangerStatisticsVo> getOwnerUnitDangerStatisticsByAreaUser(MiniAppAreaUserOwnerUnitQuery query,
			PageList pageList);

}
