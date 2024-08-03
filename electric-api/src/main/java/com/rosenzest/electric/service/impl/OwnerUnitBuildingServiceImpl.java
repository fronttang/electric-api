package com.rosenzest.electric.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rosenzest.base.PageList;
import com.rosenzest.electric.constant.ElectricConstant;
import com.rosenzest.electric.dto.OwnerUnitBuildingQuery;
import com.rosenzest.electric.dto.OwnerUnitBuildingReivewQuery;
import com.rosenzest.electric.dto.ReportDetectStatusDto;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.entity.SysDictData;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.mapper.OwnerUnitBuildingMapper;
import com.rosenzest.electric.owner.vo.OwnerUnitBuildingDangerStatisticsVo;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.ISysDictDataService;
import com.rosenzest.electric.vo.InitialOwnerUnitBuildingVo;
import com.rosenzest.electric.vo.OwnerUnitBuildingReviewVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Service
public class OwnerUnitBuildingServiceImpl extends ModelBaseServiceImpl<OwnerUnitBuildingMapper, OwnerUnitBuilding>
		implements IOwnerUnitBuildingService {

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@Autowired
	private ISysDictDataService dictDataService;

	@Override
	public List<InitialOwnerUnitBuildingVo> queryInitialList(OwnerUnitBuildingQuery query, PageList pageList) {

		Page<InitialOwnerUnitBuildingVo> startPage = PageHelper.startPage(pageList.getPageNum(),
				pageList.getPageSize());
		startPage.setReasonable(false);
		List<InitialOwnerUnitBuildingVo> list = this.baseMapper.queryInitialList(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

	@Override
	public List<OwnerUnitBuildingReviewVo> queryReviewList(OwnerUnitBuildingReivewQuery query, PageList pageList) {
		Page<OwnerUnitBuildingReviewVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
		startPage.setReasonable(false);
		List<OwnerUnitBuildingReviewVo> list = this.baseMapper.queryReviewList(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

	@Override
	public boolean updateBuildingReviewStatus(Long buildingId) {
		return this.updateBuildingReviewStatus(buildingId, null);
	}

	@Override
	public boolean updateBuildingReviewStatus(Long buildingId, ReviewStatus status) {
		if (buildingId == null) {
			return false;
		}
		OwnerUnitBuilding building = new OwnerUnitBuilding();
		building.setId(buildingId);
		building.setReviewStatus(getBuildingReviewStatus(buildingId).code());
		if (status != null) {
			building.setReviewStatus(status.code());
		}
		return this.updateById(building);
	}

	@Override
	public ReviewStatus getBuildingReviewStatus(Long buildingId) {

		ReportDetectStatusDto reviewStatus = this.baseMapper.getBuildingReviewStatus(buildingId);
		if (reviewStatus.getDangers() > 0 && reviewStatus.getDangers() == reviewStatus.getFinishs()) {
			return ReviewStatus.FINISH;
		} else if (reviewStatus.getRectifications() > 0) {
			return ReviewStatus.RECTIFIED;
		} else if (reviewStatus.getReexaminations() > 0) {
			return ReviewStatus.RE_EXAMINATION;
		} else {
			return ReviewStatus.RECTIFIED;
		}
	}

	@Override
	public List<OwnerUnitBuildingDangerStatisticsVo> getOwnerUnitBuildingDangerStatistics(Long unitId, String keyword) {

		//
		final List<SysDictData> hazardLevel = dictDataService
				.getDictDataByType(ElectricConstant.DICT_TYPE_HAZARD_LEVEL);

		LambdaQueryWrapper<OwnerUnitBuilding> queryWrapper = new LambdaQueryWrapper<OwnerUnitBuilding>();
		queryWrapper.eq(OwnerUnitBuilding::getUnitId, unitId);
		if (StrUtil.isNotBlank(keyword)) {
			queryWrapper.like(OwnerUnitBuilding::getName, keyword);
		}
		List<OwnerUnitBuilding> buildingLists = this.baseMapper.selectList(queryWrapper);

		if (CollUtil.isNotEmpty(buildingLists)) {

			List<Long> buildingIds = buildingLists.stream().map(OwnerUnitBuilding::getId).collect(Collectors.toList());

			final Map<Long, List<OwnerUnitDanger>> buildingDangerMap = new HashMap<Long, List<OwnerUnitDanger>>();
			List<OwnerUnitDanger> dangerLists = ownerUnitDangerService.getDangersByUnitIdAndBuildingIds(unitId,
					buildingIds);
			if (CollUtil.isNotEmpty(dangerLists)) {
				buildingDangerMap.putAll(dangerLists.stream()
						.collect(Collectors.groupingBy(OwnerUnitDanger::getBuildingId, Collectors.toList())));
			}

			List<OwnerUnitBuildingDangerStatisticsVo> result = buildingLists.stream().map((building) -> {

				OwnerUnitBuildingDangerStatisticsVo vo = new OwnerUnitBuildingDangerStatisticsVo();
				vo.setId(building.getId());
				vo.setName(building.getName());

				List<OwnerUnitDanger> buidingDangers = buildingDangerMap.get(building.getId());
				vo.setDanger(0L);
				vo.setFinish(0L);

				final Map<String, Long> dangerLevelMap = new HashMap<String, Long>();
				final Map<String, Long> finishLevelMap = new HashMap<String, Long>();
				if (CollUtil.isNotEmpty(buidingDangers)) {
					vo.setDanger(buidingDangers.stream().count());
					Long finish = buidingDangers.stream()
							.filter((d) -> ReviewStatus.FINISH.code().equalsIgnoreCase(d.getStatus()))
							.collect(Collectors.counting());
					vo.setFinish(finish);

					dangerLevelMap.putAll(dangerLists.stream()
							.collect(Collectors.groupingBy(OwnerUnitDanger::getLevel, Collectors.counting())));

					finishLevelMap.putAll(dangerLists.stream()
							.filter((d) -> ReviewStatus.FINISH.code().equalsIgnoreCase(d.getStatus()))
							.collect(Collectors.groupingBy(OwnerUnitDanger::getLevel, Collectors.counting())));
				}

				hazardLevel.forEach((level) -> {
					if (dangerLevelMap.get(level.getDictValue()) == null) {
						dangerLevelMap.put(StrUtil.format("count{}", level.getDictValue()), 0L);
					}
				});
				vo.setDangers(dangerLevelMap);

				hazardLevel.forEach((level) -> {
					if (finishLevelMap.get(level.getDictValue()) == null) {
						finishLevelMap.put(StrUtil.format("count{}", level.getDictValue()), 0L);
					}
				});
				vo.setFinishs(finishLevelMap);

				return vo;
			}).collect(Collectors.toList());
			return result;
		}
		return null;
	}

}
