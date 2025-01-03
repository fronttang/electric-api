package com.rosenzest.electric.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.base.util.IdUtils;
import com.rosenzest.electric.dto.DangerNotPassDto;
import com.rosenzest.electric.dto.DangerPassDto;
import com.rosenzest.electric.dto.OwnerUnitDangerQuery;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.entity.OwnerUnitDangerLog;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.entity.SysDictData;
import com.rosenzest.electric.enums.DangerOperationType;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ProjectWorkerType;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.mapper.OwnerUnitDangerMapper;
import com.rosenzest.electric.miniapp.dto.MiniAppAreaQuery;
import com.rosenzest.electric.miniapp.vo.AreaUserDangerVo;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.electric.miniapp.vo.GridmanDangerStatisticsVo;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitDangerLogService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;
import com.rosenzest.server.base.enums.UserType;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
@Service
public class OwnerUnitDangerServiceImpl extends ModelBaseServiceImpl<OwnerUnitDangerMapper, OwnerUnitDanger>
		implements IOwnerUnitDangerService {

	@Autowired
	private IOwnerUnitDangerLogService dangerLogService;

	@Autowired
	private IOwnerUnitReportService unitReportService;

	@Autowired
	private IOwnerUnitBuildingService unitBuildingService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Override
	public List<OwnerUnitDanger> getByUnitAreaId(Long unitAreaId) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.eq(OwnerUnitDanger::getUnitAreaId, unitAreaId);
		return this.baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<OwnerUnitDangerVo> queryOwnerUnitDanger(OwnerUnitDangerQuery query, PageList pageList) {
		Page<OwnerUnitDangerVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
		startPage.setReasonable(false);
		List<OwnerUnitDangerVo> list = this.baseMapper.queryOwnerUnitDanger(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

	@Override
	public OwnerUnitDangerVo getOwnerUnitDangerById(Long dangerId) {
		return this.baseMapper.getOwnerUnitDangerById(dangerId);
	}

	@Override
	public Integer countByUnitAreaId(Long unitAreaId) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.eq(OwnerUnitDanger::getUnitAreaId, unitAreaId);
		return this.baseMapper.selectCount(queryWrapper);
	}

	@Override
	public Integer countByUnitId(Long unitId) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.eq(OwnerUnitDanger::getUnitId, unitId);
		return this.baseMapper.selectCount(queryWrapper);
	}

	private OwnerUnitDanger getB14ByFormIdAndType(Long formId, String type) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.eq(OwnerUnitDanger::getFormId, formId);
		queryWrapper.eq(OwnerUnitDanger::getFormCode, "B14");
		queryWrapper.apply(" formb ->'$.data.type' = {0} ", type);
		queryWrapper.last(" LIMIT 1 ");
		return this.baseMapper.selectOne(queryWrapper);
	}

	private OwnerUnitDanger getB14Target(Long id) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.eq(OwnerUnitDanger::getId, id);
		queryWrapper.eq(OwnerUnitDanger::getFormCode, "B14");
		queryWrapper.last(" LIMIT 1 ");
		return this.baseMapper.selectOne(queryWrapper);

	}

	@Override
	@Transactional
	public boolean saveOrUpdateDanger(OwnerUnitDanger danger) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		boolean addLog = danger.getId() == null;

		OwnerUnitDanger b14 = null;

		if (danger.getId() != null) {
			// 当前 B14 数据
			OwnerUnitDanger target = getB14Target(danger.getId());
			if (target != null) {
				String targetType = target.getB14Type();
				if (StrUtil.isBlank(targetType)) {
					targetType = "residualCurrent";
				}
				danger.setB14Type(targetType);

				String type = "residualCurrent".equalsIgnoreCase(targetType) ? "alarmTime" : "residualCurrent";
				if (target.getFormId() != null) {
					b14 = getB14ByFormIdAndType(target.getFormId(), type);
				}
				if (b14 == null) {
					b14 = new OwnerUnitDanger();

					BeanUtils.copyProperties(danger, b14);
					b14.setId(null);
					Long formId = IdUtils.getSnowflakeNextId();
					b14.setFormId(formId);
					b14.setCreateBy(String.valueOf(loginUser.getUserId()));
					danger.setFormId(formId);
				}

				this.saveOrUpdate(danger);

				b14.setFormb(danger.getFormb());
				b14.setB14Type(type);
				b14.setStatus(ReviewStatus.RECTIFIED.code());

				this.saveOrUpdate(b14);
			} else {
				this.saveOrUpdate(danger);
			}
		} else {
			if ("B14".equalsIgnoreCase(danger.getFormCode())) {
				Long formId = IdUtils.getSnowflakeNextId();

				danger.setB14Type("residualCurrent");
				danger.setFormId(formId);

				this.saveOrUpdate(danger);

				b14 = new OwnerUnitDanger();
				BeanUtils.copyProperties(danger, b14);

				b14.setB14Type("alarmTime");
				b14.setFormId(formId);
				b14.setId(null);
				b14.setCreateBy(String.valueOf(loginUser.getUserId()));

				this.saveOrUpdate(b14);
			} else {
				this.saveOrUpdate(danger);
			}
		}
		//

		// boolean saveDangerFlag = this.saveOrUpdate(danger);
		// if (b14 != null) {
		// this.saveOrUpdate(b14);
		// }

		boolean saveDangerLogFlag = true;
		// 增加隐患日志
		if (addLog) {
			OwnerUnitDangerLog log = new OwnerUnitDangerLog();
			log.setDangerId(danger.getId());
			log.setOperator(danger.getInspector());
			log.setOperatorId(danger.getInspectorId());
			log.setOperationType(DangerOperationType.INITIAL.code());
			log.setOperationPic(danger.getDangerPic());

			log.setOperatorRole(ProjectWorkerType.INSPECTOR.code());
			log.setContent("");
			saveDangerLogFlag = dangerLogService.save(log);
		}

		// 初检报告状态
		boolean initalReportFlag = unitReportService.updateUnitReportStatus(danger.getUnitId(), UnitReportType.INITIAL,
				InitialInspectionStatus.CHECKING.code());

		// 复检报告状态
		boolean reviewReportFlag = unitReportService.updateUnitReportStatus(danger.getUnitId(), UnitReportType.REVIEW,
				ReviewStatus.RECTIFIED.code());

		// 楼栋复检状态
		unitBuildingService.updateBuildingReviewStatus(danger.getBuildingId(), ReviewStatus.RECTIFIED);

		return saveDangerLogFlag && initalReportFlag && reviewReportFlag;
	}

	@Override
	@Transactional
	public boolean removeDanger(OwnerUnitDanger danger) {

		boolean deleteFlag = this.baseMapper.deleteById(danger.getId()) > 0;

		boolean reviewReportFlag = false;
		if (deleteFlag) {
			// 复检报告状态
			reviewReportFlag = unitReportService.updateUnitReportStatus(danger.getUnitId(), UnitReportType.REVIEW);
			// 楼栋复检状态
			unitBuildingService.updateBuildingReviewStatus(danger.getBuildingId(), null);
		}

		return deleteFlag && reviewReportFlag;
	}

	@Override
	@Transactional
	public boolean pass(DangerPassDto data) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		OwnerUnitDanger danger = this.baseMapper.selectById(data.getDangerId());
		if (danger == null) {
			return false;
		}

		danger.setRectificationPic(data.getPic());
		danger.setReviewer(loginUser.getName());
		danger.setReviewerDate(new Date());

		// 检测员整改
		boolean workerRectified = UserType.WORKER.code().equalsIgnoreCase(loginUser.getType())
				&& ReviewStatus.RECTIFIED.code().equalsIgnoreCase(danger.getStatus());

		if (workerRectified) {
			// 检测员通过待整改的隐患直接完成
			danger.setRectification(loginUser.getName());
			danger.setRectificationDate(new Date());
			danger.setStatus(ReviewStatus.FINISH.code());
		} else {
			danger.setStatus(ReviewStatus.FINISH.code());
		}

		this.saveOrUpdate(danger);

		// 复检报告状态
		unitReportService.updateUnitReportStatus(danger.getUnitId(), UnitReportType.REVIEW);

		// 楼栋复检状态
		unitBuildingService.updateBuildingReviewStatus(danger.getBuildingId(), null);

		// 检测员整改添加整改日志
		if (workerRectified) {
			// 添加整改隐患日志
			OwnerUnitDangerLog log = new OwnerUnitDangerLog();
			log.setDangerId(danger.getId());
			log.setOperator(loginUser.getName());
			log.setOperatorId(loginUser.getUserId());
			log.setOperationType(DangerOperationType.RECTIFICATION.code());
			log.setOperationPic(data.getPic());
			log.setOperatorRole(ProjectWorkerType.INSPECTOR.code());
			dangerLogService.save(log);
		}

		// 添加复检通过隐患日志
		OwnerUnitDangerLog log = new OwnerUnitDangerLog();
		log.setDangerId(danger.getId());
		log.setOperator(loginUser.getName());
		log.setOperatorId(loginUser.getUserId());
		log.setOperationType(DangerOperationType.REVIEW.code());
		log.setOperatorRole(ProjectWorkerType.INSPECTOR.code());
		log.setContent("通过");
		dangerLogService.save(log);

		return true;
	}

	@Override
	@Transactional
	public boolean notPass(@Valid DangerNotPassDto data) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		OwnerUnitDanger danger = this.baseMapper.selectById(data.getDangerId());
		if (danger == null) {
			return false;
		}

		danger.setRectificationPic(data.getRectificationPic());
		danger.setDetectPic(data.getPic());
		danger.setReviewer(loginUser.getName());
		danger.setReviewerDate(new Date());
		danger.setReason(data.getReason());

		if (!"1".equalsIgnoreCase(data.getUnableToDetect())) {
			// 状态变更待整改
			danger.setStatus(ReviewStatus.RECTIFIED.code());
		}

		this.saveOrUpdate(danger);

		// 复检报告状态
		unitReportService.updateUnitReportStatus(danger.getUnitId(), UnitReportType.REVIEW);

		// 隐患日志
		OwnerUnitDangerLog log = new OwnerUnitDangerLog();
		log.setDangerId(danger.getId());
		log.setOperator(loginUser.getName());
		log.setOperatorId(loginUser.getUserId());
		log.setOperationPic(data.getPic());
		if (!"1".equalsIgnoreCase(data.getUnableToDetect())) {
			log.setOperationType(DangerOperationType.REVIEW.code());
			log.setOperatorRole(ProjectWorkerType.INSPECTOR.code());
			log.setContent("不通过");
		} else {
			log.setOperationType(DangerOperationType.UNABLE_TO_DETECT.code());
			log.setOperatorRole(ProjectWorkerType.INSPECTOR.code());
			log.setContent("");
		}
		log.setRemark(data.getReason());
		dangerLogService.save(log);

		return true;
	}

	@Override
	public Integer countByBuildingId(Long buildingId) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.eq(OwnerUnitDanger::getBuildingId, buildingId);
		return this.baseMapper.selectCount(queryWrapper);
	}

	@Override
	public Integer countByChargingPileId(Long pileId) {
		return this.baseMapper.countByChargingPileId(pileId);
	}

	@Override
	public Integer countFormDangers(Long formId, Long unitId, Long unitAreaId, Long buildingId) {
		return this.baseMapper.countFormDangers(formId, unitId, unitAreaId, buildingId);
	}

	@Override
	public Integer countFormbDangers(String code, Long unitId, Long unitAreaId, Long buildingId) {
		return this.baseMapper.countFormbDangers(code, unitId, unitAreaId, buildingId);
	}

	@Override
	public Integer countByDataIdAndPileId(Long dataId, Long pileId) {
		return this.baseMapper.countByDataIdAndPileId(dataId, pileId);
	}

	@Override
	public Integer countByUnitIdAndDataIdAndPileId(Long unitId, Long dataId, Long pileId) {
		return this.baseMapper.countByUnitIdAndDataIdAndPileId(unitId, dataId, pileId);
	}

	@Override
	public List<OwnerUnitDanger> getDangersByUnitIdAndBuildingIds(Long unitId, List<Long> buildingIds) {

		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.select(OwnerUnitDanger::getStatus, OwnerUnitDanger::getUnitId, OwnerUnitDanger::getBuildingId,
				OwnerUnitDanger::getId, OwnerUnitDanger::getLevel);
		queryWrapper.eq(OwnerUnitDanger::getUnitId, unitId);
		queryWrapper.ne(OwnerUnitDanger::getStatus, "9");
		queryWrapper.ne(OwnerUnitDanger::getStatus, "");
		if (CollUtil.isNotEmpty(buildingIds)) {
			queryWrapper.in(OwnerUnitDanger::getBuildingId, buildingIds);
		}
		return this.baseMapper.selectList(queryWrapper);
	}

	@Transactional
	@Override
	public boolean rectification(DangerPassDto data) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		OwnerUnitDanger danger = this.baseMapper.selectById(data.getDangerId());
		if (danger == null) {
			return false;
		}

		if (!ReviewStatus.RECTIFIED.code().equalsIgnoreCase(danger.getStatus())) {
			return false;
		}

		danger.setRectificationPic(data.getPic());
		danger.setRectification(loginUser.getName());
		danger.setRectificationDate(new Date());
		// 其他情况隐患状态为待复检
		danger.setStatus(ReviewStatus.RE_EXAMINATION.code());

		this.saveOrUpdate(danger);

		// 复检报告状态
		unitReportService.updateUnitReportStatus(danger.getUnitId(), UnitReportType.REVIEW);

		// 楼栋复检状态
		unitBuildingService.updateBuildingReviewStatus(danger.getBuildingId(), null);

		// 添加整改隐患日志
		OwnerUnitDangerLog log = new OwnerUnitDangerLog();
		log.setDangerId(danger.getId());
		log.setOperator(loginUser.getName());
		log.setOperatorId(loginUser.getUserId());
		log.setOperationType(DangerOperationType.RECTIFICATION.code());
		log.setOperationPic(data.getPic());
		// log.setOperatorRole();
		dangerLogService.save(log);

		return true;
	}

	@Override
	public GridmanDangerStatisticsVo statisticsByGridman(LoginUser user) {

		GridmanDangerStatisticsVo result = new GridmanDangerStatisticsVo();
		result.setDanger(0L);
		result.setFinish(0L);
		result.setRectification(0L);
		result.setReview(0L);

		Project project = projectService.getById(user.getProjectId());
		if (project != null) {

			List<OwnerUnitDanger> dangers = this.baseMapper.getOwnerUnitDangerByGridman(user.getUserId(), null);

			final List<SysDictData> hazardLevel = ownerUnitService.getHazardLevel(project.getType());

			ownerUnitService.buildDangerStatisticsVo(dangers, hazardLevel, result);

		}

		return result;
	}

	@Override
	public List<AreaUserDangerVo> getOwnerUnitDangerByAreaUser(AreaUserInfoVo userInfo) {
		return this.baseMapper.getOwnerUnitDangerByAreaUser(userInfo);
	}

	@Override
	public List<OwnerUnitDanger> getTodayDangersByAreaUser(AreaUserInfoVo userInfo) {
		return this.baseMapper.getTodayDangersByAreaUser(userInfo);
	}

	@Override
	public List<OwnerUnitDangerVo> queryReviewOwnerUnitDanger(OwnerUnitDangerQuery query, PageList pageList) {
		Page<OwnerUnitDangerVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
		startPage.setReasonable(false);
		List<OwnerUnitDangerVo> list = this.baseMapper.queryReviewOwnerUnitDanger(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

	@Override
	public List<AreaUserDangerVo> getOwnerUnitDangerByArea(MiniAppAreaQuery query) {
		return this.baseMapper.getOwnerUnitDangerByArea(query);
	}

	@Override
	public List<OwnerUnitDanger> getOwnerUnitDangerByGridman(Long userId, String type) {
		return this.baseMapper.getOwnerUnitDangerByGridman(userId, type);
	}
}
