package com.rosenzest.electric.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rosenzest.electric.dto.OwnerUnitQuery;
import com.rosenzest.electric.dto.OwnerUnitReviewQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.miniapp.dto.MiniAppAreaQuery;
import com.rosenzest.electric.miniapp.dto.MiniAppAreaUserOwnerUnitQuery;
import com.rosenzest.electric.miniapp.dto.MiniAppOwnerUnitQuery;
import com.rosenzest.electric.miniapp.dto.StatisticsHighQuery;
import com.rosenzest.electric.miniapp.dto.UnitStatisticsDto;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitOverviewVo;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitReviewVo;
import com.rosenzest.electric.vo.OwnerUnitVo;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * 业主单元 Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
public interface OwnerUnitMapper extends ModelBaseMapper<OwnerUnit> {

	List<InitialOwnerUnitVo> queryInitialList(OwnerUnitQuery query);

	OwnerUnitVo getOwnerUnitById(Long unitId);

	List<OwnerUnitReviewVo> queryReviewList(OwnerUnitReviewQuery query);

	List<OwnerUnitOverviewVo> getOwnerUnitListByOwner(@Param("userId") Long userId, @Param("projectId") Long projectId);

	OwnerUnitOverviewVo getOwnerUnitInfoById(Long unitId);

	List<OwnerUnitOverviewVo> getOwnerUnitListByGridman(@Param("userId") Long userId,
			@Param("projectId") Long projectId);

	List<OwnerUnitOverviewVo> queryOwnerUnitListByGridman(MiniAppOwnerUnitQuery query);

	List<UnitStatisticsDto> unitStatistics(AreaUserInfoVo userInfo);

	List<UnitStatisticsDto> unitStatisticsByGridman(@Param("userId") Long userId, @Param("type") String type);

	List<UnitStatisticsDto> unitStatisticsHigh(StatisticsHighQuery query);

	List<UnitStatisticsDto> unitStatisticsByArea(MiniAppAreaQuery data);

	Long countTodayDetectUnit(AreaUserInfoVo userInfo);

	List<OwnerUnitOverviewVo> queryOwnerUnitListByAreaUser(MiniAppAreaUserOwnerUnitQuery query);
}
