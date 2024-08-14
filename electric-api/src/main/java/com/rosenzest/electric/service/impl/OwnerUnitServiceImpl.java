package com.rosenzest.electric.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.constant.ElectricConstant;
import com.rosenzest.electric.dto.OwnerUnitDto;
import com.rosenzest.electric.dto.OwnerUnitQuery;
import com.rosenzest.electric.dto.OwnerUnitReviewQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.entity.SysDictData;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.mapper.OwnerUnitMapper;
import com.rosenzest.electric.miniapp.dto.MiniAppAreaUserOwnerUnitQuery;
import com.rosenzest.electric.miniapp.dto.MiniAppOwnerUnitQuery;
import com.rosenzest.electric.miniapp.dto.UnitStatisticsDto;
import com.rosenzest.electric.miniapp.vo.AreaUserIndexVo;
import com.rosenzest.electric.miniapp.vo.AreaUserIndexVo.DangerStatistics;
import com.rosenzest.electric.miniapp.vo.AreaUserIndexVo.TodayStatistics;
import com.rosenzest.electric.miniapp.vo.AreaUserIndexVo.UnitStatistics;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.electric.miniapp.vo.IDangerStatisticsVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitDangerStatisticsVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitOverviewVo;
import com.rosenzest.electric.service.IOwnerUnitAreaService;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.ISysDictDataService;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitReviewVo;
import com.rosenzest.electric.vo.OwnerUnitVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 业主单元 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
@Service
public class OwnerUnitServiceImpl extends ModelBaseServiceImpl<OwnerUnitMapper, OwnerUnit>
		implements IOwnerUnitService {

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	@Autowired
	private IOwnerUnitAreaService ownerUnitAreaService;

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@Autowired
	private ISysDictDataService dictDataService;

	@Autowired
	private IOwnerUnitBuildingService ownerUnitBuildingService;

	@Autowired
	private IProjectService projectService;

	@Override
	public List<InitialOwnerUnitVo> queryInitialList(OwnerUnitQuery query, PageList pageList) {

		Page<InitialOwnerUnitVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
		startPage.setReasonable(false);
		List<InitialOwnerUnitVo> list = this.baseMapper.queryInitialList(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

	@Override
	public OwnerUnitVo getOwnerUnitById(Long unitId) {
		return this.baseMapper.getOwnerUnitById(unitId);
	}

	@Override
	@Transactional
	public boolean saveOwnerUnit(OwnerUnitDto data) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		OwnerUnit unit = new OwnerUnit();
		BeanUtils.copyProperties(data, unit);

		if (data.getId() == null) {
			unit.setCreateBy(String.valueOf(loginUser.getUserId()));
		}
		this.saveOrUpdate(unit);

		data.setId(unit.getId());
		// 初检报告
		OwnerUnitReport report = ownerUnitReportService.getReportByUnitIdAndType(unit.getId(), UnitReportType.INITIAL);
		if (report == null) {
			report = new OwnerUnitReport();
			report.setUnitId(unit.getId());
			report.setType(UnitReportType.INITIAL.code());
			report.setCode(data.getInitialTestNo());
			report.setDetectData(new Date());
			report.setDetectStatus(InitialInspectionStatus.CHECKING.code());
			report.setInspector(loginUser.getName());
			report.setInspectorId(loginUser.getUserId());
			ownerUnitReportService.saveOrUpdate(report);
		}

		// 复检报告
		OwnerUnitReport againReport = ownerUnitReportService.getReportByUnitIdAndType(unit.getId(),
				UnitReportType.REVIEW);
		if (againReport == null) {
			againReport = new OwnerUnitReport();
			againReport.setUnitId(unit.getId());
			againReport.setType(UnitReportType.REVIEW.code());
			againReport.setCode(data.getAgainTestNo());
			againReport.setDetectData(data.getAgainTestData());
			againReport.setDetectStatus(ReviewStatus.RECTIFIED.code());
			againReport.setInspector(loginUser.getName());
			againReport.setInspectorId(loginUser.getUserId());
			ownerUnitReportService.saveOrUpdate(againReport);
		}

		return true;
	}

	@Override
	public List<OwnerUnitReviewVo> queryReviewList(OwnerUnitReviewQuery query, PageList pageList) {
		Page<OwnerUnitReviewVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
		startPage.setReasonable(false);
		List<OwnerUnitReviewVo> list = this.baseMapper.queryReviewList(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

	@Override
	@Transactional
	public boolean removeOwnerUnitById(Long unitId) {
		// 删除公共区域/户
		ownerUnitAreaService.removeByUnitId(unitId);

		// 删除报告

		// 删除building

		// 删除config

		return this.removeById(unitId);
	}

	@Override
	public boolean checkOwnerUnitName(OwnerUnit unit) {
		LambdaQueryWrapper<OwnerUnit> queryWrapper = new LambdaQueryWrapper<OwnerUnit>();
		queryWrapper.eq(OwnerUnit::getProjectId, unit.getProjectId());
		queryWrapper.eq(OwnerUnit::getName, unit.getName());
		queryWrapper.eq(OwnerUnit::getDistrict, unit.getDistrict());
		queryWrapper.eq(OwnerUnit::getStreet, unit.getStreet());
		if (StrUtil.isNotBlank(unit.getUnitType())) {
			queryWrapper.eq(OwnerUnit::getUnitType, unit.getUnitType());
		}
		if (StrUtil.isNotBlank(unit.getCommunity())) {
			queryWrapper.eq(OwnerUnit::getCommunity, unit.getCommunity());
		}
		if (StrUtil.isNotBlank(unit.getHamlet())) {
			queryWrapper.eq(OwnerUnit::getHamlet, unit.getHamlet());
		}
		if (unit.getId() != null) {
			queryWrapper.ne(OwnerUnit::getId, unit.getId());
		}
		return this.count(queryWrapper) > 0;
	}

	@Override
	public List<OwnerUnitOverviewVo> getOwnerUnitListByOwner(Long userId, Long projectId) {
		return this.baseMapper.getOwnerUnitListByOwner(userId, projectId);
	}

	@Override
	public List<OwnerUnitOverviewVo> getOwnerUnitListByGridman(Long userId, Long projectId) {
		return this.baseMapper.getOwnerUnitListByGridman(userId, projectId);
	}

	@Override
	public boolean checkOwnerUnitManager(Long unitId, Long userId) {
		LambdaQueryWrapper<OwnerUnit> queryWrapper = new LambdaQueryWrapper<OwnerUnit>();
		queryWrapper.eq(OwnerUnit::getId, unitId);
		queryWrapper.eq(OwnerUnit::getManager, userId);
		return this.baseMapper.selectCount(queryWrapper) > 0;
	}

	@Override
	public boolean checkOwnerUnitGridman(Long unitId, Long userId) {
		LambdaQueryWrapper<OwnerUnit> queryWrapper = new LambdaQueryWrapper<OwnerUnit>();
		queryWrapper.eq(OwnerUnit::getId, unitId);
		queryWrapper.eq(OwnerUnit::getGridman, userId);
		return this.baseMapper.selectCount(queryWrapper) > 0;
	}

	@Override
	public OwnerUnitDangerStatisticsVo getOwnerUnitDangerStatistics(Long unitId, Long buildingId) {

		OwnerUnitOverviewVo ownerUnit = this.baseMapper.getOwnerUnitInfoById(unitId);
		if (ownerUnit == null) {
			throw new BusinessException(ResultEnum.FORBIDDEN);
		}

		OwnerUnitBuilding building = null;
		if (buildingId != null) {
			building = ownerUnitBuildingService.getById(buildingId);
			if (building == null) {
				throw new BusinessException(ResultEnum.FORBIDDEN);
			}
		}

		final List<SysDictData> hazardLevel = this.getHazardLevel(ownerUnit.getType());

		List<Long> buildingIds = null;

		if (buildingId != null) {
			buildingIds = Arrays.asList(buildingId);
		}
		List<OwnerUnitDanger> dangerLists = ownerUnitDangerService.getDangersByUnitIdAndBuildingIds(unitId,
				buildingIds);

		return buildOwnerUnitDangerStatisticsVo(ownerUnit, building, dangerLists, hazardLevel);

	}

	@Override
	public OwnerUnitOverviewVo getOwnerUnitInfoById(Long unitId) {
		return this.baseMapper.getOwnerUnitInfoById(unitId);
	}

	@Override
	public OwnerUnitDangerStatisticsVo buildOwnerUnitDangerStatisticsVo(OwnerUnitOverviewVo ownerUnit,
			OwnerUnitBuilding building, List<OwnerUnitDanger> dangerLists, List<SysDictData> hazardLevel) {
		OwnerUnitDangerStatisticsVo vo = new OwnerUnitDangerStatisticsVo();
		BeanUtils.copyProperties(ownerUnit, vo);

		if (building != null) {
			vo.setBuildingId(building.getId());
			vo.setBuildingName(building.getName());
			vo.setBuildingType(building.getType());
		}
		buildDangerStatisticsVo(dangerLists, hazardLevel, vo);
		return vo;
	}

	@Override
	public void buildDangerStatisticsVo(List<OwnerUnitDanger> dangerLists, List<SysDictData> hazardLevel,
			IDangerStatisticsVo vo) {
		vo.setDanger(0L);
		vo.setFinish(0L);
		vo.setRectification(0L);
		vo.setReview(0L);

		final Map<String, Long> dangerLevelMap = new HashMap<String, Long>();
		final Map<String, Long> finishLevelMap = new HashMap<String, Long>();
		final Map<String, Long> rectificationLevelMap = new HashMap<String, Long>();
		final Map<String, Long> reviewLevelMap = new HashMap<String, Long>();
		if (CollUtil.isNotEmpty(dangerLists)) {
			vo.setDanger(dangerLists.stream().count());

			// 完成数
			Long finish = dangerLists.stream().filter((d) -> ReviewStatus.FINISH.code().equalsIgnoreCase(d.getStatus()))
					.collect(Collectors.counting());
			vo.setFinish(finish);

			// 待整改数
			Long rectification = dangerLists.stream()
					.filter((d) -> ReviewStatus.RECTIFIED.code().equalsIgnoreCase(d.getStatus()))
					.collect(Collectors.counting());
			vo.setRectification(rectification);

			// 待复检数
			Long review = dangerLists.stream()
					.filter((d) -> ReviewStatus.RE_EXAMINATION.code().equalsIgnoreCase(d.getStatus()))
					.collect(Collectors.counting());
			vo.setReview(review);

			dangerLevelMap.putAll(dangerLists.stream().filter((d) -> Objects.nonNull(d.getLevel()))
					.collect(Collectors.groupingBy(OwnerUnitDanger::getLevel, Collectors.counting())).entrySet()
					.stream()
					.collect(Collectors.toMap((d) -> StrUtil.format("count{}", d.getKey()), (d) -> d.getValue())));

			finishLevelMap.putAll(dangerLists.stream().filter((d) -> Objects.nonNull(d.getLevel()))
					.filter((d) -> ReviewStatus.FINISH.code().equalsIgnoreCase(d.getStatus()))
					.collect(Collectors.groupingBy(OwnerUnitDanger::getLevel, Collectors.counting())).entrySet()
					.stream()
					.collect(Collectors.toMap((d) -> StrUtil.format("count{}", d.getKey()), (d) -> d.getValue())));

			rectificationLevelMap.putAll(dangerLists.stream().filter((d) -> Objects.nonNull(d.getLevel()))
					.filter((d) -> ReviewStatus.RECTIFIED.code().equalsIgnoreCase(d.getStatus()))
					.collect(Collectors.groupingBy(OwnerUnitDanger::getLevel, Collectors.counting())).entrySet()
					.stream()
					.collect(Collectors.toMap((d) -> StrUtil.format("count{}", d.getKey()), (d) -> d.getValue())));

			reviewLevelMap.putAll(dangerLists.stream().filter((d) -> Objects.nonNull(d.getLevel()))
					.filter((d) -> ReviewStatus.RE_EXAMINATION.code().equalsIgnoreCase(d.getStatus()))
					.collect(Collectors.groupingBy(OwnerUnitDanger::getLevel, Collectors.counting())).entrySet()
					.stream()
					.collect(Collectors.toMap((d) -> StrUtil.format("count{}", d.getKey()), (d) -> d.getValue())));
		}

		hazardLevel.forEach((level) -> {

			String key = StrUtil.format("count{}", level.getDictValue());

			dangerLevelMap.put(key, dangerLevelMap.get(key) == null ? 0L : dangerLevelMap.get(key));

			finishLevelMap.put(key, finishLevelMap.get(key) == null ? 0L : finishLevelMap.get(key));

			rectificationLevelMap.put(key,
					rectificationLevelMap.get(key) == null ? 0L : rectificationLevelMap.get(key));

			reviewLevelMap.put(key, reviewLevelMap.get(key) == null ? 0L : reviewLevelMap.get(key));

		});
		vo.setDangers(dangerLevelMap);
		vo.setFinishs(finishLevelMap);
		vo.setRectifications(rectificationLevelMap);
		vo.setReviews(reviewLevelMap);
	}

	@Override
	public List<SysDictData> getHazardLevel(String type) {
		List<SysDictData> hazardLevel = new ArrayList<SysDictData>();
		if ("1".equalsIgnoreCase(type) || "2".equalsIgnoreCase(type)) {
			// 城中村/工业园
			hazardLevel.addAll(dictDataService.getDictDataByType(ElectricConstant.DICT_TYPE_HAZARD_LEVEL));
		} else if ("3".equalsIgnoreCase(type)) {
			// 高风险
			hazardLevel.addAll(dictDataService.getDictDataByType(ElectricConstant.DICT_TYPE_HAZARD_LEVEL_HIGH));
		} else if ("4".equalsIgnoreCase(type)) {
			// 高风险
			hazardLevel.addAll(
					dictDataService.getDictDataByType(ElectricConstant.DICT_TYPE_HAZARD_LEVEL_CHARGING_STATION));
		}
		return hazardLevel;
	}

	private List<OwnerUnitOverviewVo> getOwnerUnitListByGridman(MiniAppOwnerUnitQuery query) {
		return this.baseMapper.queryOwnerUnitListByGridman(query);
	}

	private List<OwnerUnitOverviewVo> getOwnerUnitListByAreaUser(MiniAppAreaUserOwnerUnitQuery query) {
		return this.baseMapper.queryOwnerUnitListByAreaUser(query);
	}

	@Override
	public List<OwnerUnitDangerStatisticsVo> getOwnerUnitDangerStatisticsByGridman(MiniAppOwnerUnitQuery query,
			PageList pageList) {

		List<OwnerUnitDangerStatisticsVo> result = new ArrayList<OwnerUnitDangerStatisticsVo>();
		Project project = projectService.getById(query.getProjectId());
		if (project != null) {

			Page<OwnerUnitOverviewVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
			startPage.setReasonable(false);
			List<OwnerUnitOverviewVo> ownerUnitList = getOwnerUnitListByGridman(query);

			if (CollUtil.isNotEmpty(ownerUnitList)) {

				final List<SysDictData> hazardLevel = this.getHazardLevel(project.getType());
				result = ownerUnitList.stream().map((unit) -> {
					List<OwnerUnitDanger> dangers = ownerUnitDangerService
							.getDangersByUnitIdAndBuildingIds(unit.getId(), null);
					return this.buildOwnerUnitDangerStatisticsVo(unit, null, dangers, hazardLevel);

				}).collect(Collectors.toList());
			}
			pageList.setTotalNum(startPage.getTotal());
		}
		return result;
	}

	@Override
	public List<OwnerUnitDangerStatisticsVo> getOwnerUnitDangerStatisticsByAreaUser(MiniAppAreaUserOwnerUnitQuery query,
			PageList pageList) {

		List<OwnerUnitDangerStatisticsVo> result = new ArrayList<OwnerUnitDangerStatisticsVo>();
		Project project = projectService.getById(query.getProjectId());
		if (project != null) {

			Page<OwnerUnitOverviewVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
			startPage.setReasonable(false);
			List<OwnerUnitOverviewVo> ownerUnitList = getOwnerUnitListByAreaUser(query);

			if (CollUtil.isNotEmpty(ownerUnitList)) {

				final List<SysDictData> hazardLevel = this.getHazardLevel(project.getType());
				result = ownerUnitList.stream().map((unit) -> {
					List<OwnerUnitDanger> dangers = ownerUnitDangerService
							.getDangersByUnitIdAndBuildingIds(unit.getId(), null);
					return this.buildOwnerUnitDangerStatisticsVo(unit, null, dangers, hazardLevel);

				}).collect(Collectors.toList());
			}
			pageList.setTotalNum(startPage.getTotal());
		}
		return result;
	}

	private UnitStatistics getUnitStatistics(AreaUserInfoVo userInfo) {

		List<UnitStatisticsDto> unitStatisticsResult = this.baseMapper.unitStatistics(userInfo);
		UnitStatistics result = new UnitStatistics();

		if (CollUtil.isNotEmpty(unitStatisticsResult)) {

			result.setTotal(unitStatisticsResult.stream().count());

			long detected = unitStatisticsResult.stream().filter((e) -> Objects.nonNull(e.getReportId())).count();
			result.setDetected(detected);

			long detecting = unitStatisticsResult.stream().filter((e) -> Objects.isNull(e.getReportId())).count();
			result.setDetecting(detecting);
		}

		return result;
	}

	private TodayStatistics getTodayStatistics(AreaUserInfoVo userInfo) {

		TodayStatistics today = new TodayStatistics();

		Long unit = this.baseMapper.countTodayDetectUnit(userInfo);
		today.setUnit(unit);

		List<OwnerUnitDanger> todayDangers = ownerUnitDangerService.getTodayDangersByAreaUser(userInfo);
		if (CollUtil.isNotEmpty(todayDangers)) {
			today.setDanger(todayDangers.stream().count());
			today.setFinish(todayDangers.stream()
					.filter((d) -> ReviewStatus.FINISH.code().equalsIgnoreCase(d.getStatus())).count());
		}

		return today;
	}

	@Override
	public AreaUserIndexVo getAreaUserIndex(AreaUserInfoVo userInfo) {

		AreaUserIndexVo result = new AreaUserIndexVo();
		result.setUserName(userInfo.getNickName());
		result.setAreaName(userInfo.getAreaName());
		result.setUnit(getUnitStatistics(userInfo));

		Project project = projectService.getById(userInfo.getProjectId());
		if (project != null) {

			List<OwnerUnitDanger> dangerLists = ownerUnitDangerService.getOwnerUnitDangerByAreaUser(userInfo);
			DangerStatistics danger = new DangerStatistics();
			final List<SysDictData> hazardLevel = this.getHazardLevel(project.getType());

			buildDangerStatisticsVo(dangerLists, hazardLevel, danger);

			result.setDanger(danger);
		}

		TodayStatistics today = this.getTodayStatistics(userInfo);
		result.setToday(today);
		return result;
	}

}
