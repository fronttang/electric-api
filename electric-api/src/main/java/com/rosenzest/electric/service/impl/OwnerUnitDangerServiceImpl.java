package com.rosenzest.electric.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.util.SnowFlakeUtil;
import com.rosenzest.electric.dto.DangerNotPassDto;
import com.rosenzest.electric.dto.DangerPassDto;
import com.rosenzest.electric.dto.OwnerUnitAgainDangerQuery;
import com.rosenzest.electric.dto.OwnerUnitDangerQuery;
import com.rosenzest.electric.dto.UnitAreaDangerQuery;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.entity.OwnerUnitDangerLog;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.enums.DangerOperationType;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ProjectWorkerType;
import com.rosenzest.electric.enums.ReExaminationStatus;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.mapper.OwnerUnitDangerMapper;
import com.rosenzest.electric.service.IOwnerUnitDangerLogService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.vo.OwnerUnitAgainDangerVo;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

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

	@Override
	public List<OwnerUnitDanger> getByUnitAreaId(Long unitAreaId) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		queryWrapper.eq(OwnerUnitDanger::getUnitAreaId, unitAreaId);
		return this.baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<OwnerUnitDangerVo> queryOwnerUnitDanger(OwnerUnitDangerQuery query) {
		return this.baseMapper.queryOwnerUnitDanger(query);
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

	@Override
	public List<OwnerUnitDanger> selectByCondition(UnitAreaDangerQuery query) {
		LambdaQueryWrapper<OwnerUnitDanger> queryWrapper = new LambdaQueryWrapper<OwnerUnitDanger>();
		if (query.getUnitAreaId() != null) {
			queryWrapper.eq(OwnerUnitDanger::getUnitAreaId, query.getUnitAreaId());
		}
		if (query.getUnitId() != null) {
			queryWrapper.eq(OwnerUnitDanger::getUnitId, query.getUnitId());
		}
		if (query.getBuildingId() != null) {
			queryWrapper.eq(OwnerUnitDanger::getBuildingId, query.getBuildingId());
		}

		if ("B".equalsIgnoreCase(query.getType())) {
			if (StrUtil.isNotBlank(query.getFormCode())) {
				queryWrapper.eq(OwnerUnitDanger::getFormCode, query.getFormCode());
			}
		} else {
			if (query.getFormId() != null) {
				queryWrapper.eq(OwnerUnitDanger::getFormId, query.getFormId());
			}
		}

		return this.baseMapper.selectList(queryWrapper);
	}

	@Override
	@Transactional
	public boolean saveOrUpdateDanger(OwnerUnitDanger danger) {

		OwnerUnitDangerLog log = null;
		// 增加隐患日志
		if (danger.getId() == null) {
			log = new OwnerUnitDangerLog();
			log.setOperator(danger.getInspector());
			log.setOperatorId(danger.getInspectorId());
			log.setOperationType(DangerOperationType.INITIAL.code());
			log.setOperationPic(danger.getDangerPic());

			log.setOperatorRole(ProjectWorkerType.INSPECTOR.code());
			log.setContent("初检");
		}
		danger.setStatus(ReExaminationStatus.RECTIFIED.code());
		this.saveOrUpdate(danger);
		if (log != null) {
			log.setDangerId(danger.getId());
			dangerLogService.save(log);
		}

		// 修改报告状态
		OwnerUnitReport initialReport = unitReportService.getReportByUnitIdAndBuildingIdAndType(danger.getUnitId(),
				null, UnitReportType.INITIAL);
		if (initialReport == null) {
			initialReport = new OwnerUnitReport();
			initialReport.setType(UnitReportType.INITIAL.code());
			initialReport.setInspector(danger.getInspector());
			initialReport.setInspectorId(danger.getInspectorId());
			initialReport.setUnitId(danger.getId());
			initialReport.setBuildingId(danger.getBuildingId());
			initialReport.setIsDangerNotice("0");
			initialReport.setIsHouseholdRate("0");
			initialReport.setIsTest("0");
			initialReport.setIsTestReason(null);

			// 没有初检编号的随机生成一个 TODO
			if (StrUtil.isBlank(initialReport.getCode())) {
				initialReport.setCode(SnowFlakeUtil.uniqueString());
			}
		}

		initialReport.setDetectStatus(InitialInspectionStatus.CHECKING.code());
		unitReportService.saveOrUpdate(initialReport);

		OwnerUnitReport againReport = unitReportService.getReportByUnitIdAndBuildingIdAndType(danger.getUnitId(), null,
				UnitReportType.AGAIN);
		if (againReport == null) {
			againReport = new OwnerUnitReport();
			againReport.setType(UnitReportType.AGAIN.code());
			againReport.setInspector(danger.getInspector());
			againReport.setInspectorId(danger.getInspectorId());
			againReport.setUnitId(danger.getId());
			againReport.setBuildingId(danger.getBuildingId());
			againReport.setIsDangerNotice("0");
			againReport.setIsHouseholdRate("0");
			againReport.setIsTest("0");
			againReport.setIsTestReason(null);

			// 没有初检编号的随机生成一个 TODO
			if (StrUtil.isBlank(againReport.getCode())) {
				againReport.setCode(SnowFlakeUtil.uniqueString());
			}
		}

		againReport.setDetectStatus(ReExaminationStatus.RECTIFIED.code());
		unitReportService.saveOrUpdate(againReport);

		return true;
	}

	@Override
	@Transactional
	public boolean removeDanger(OwnerUnitDanger danger) {

		this.baseMapper.deleteById(danger.getId());

		OwnerUnitReport againReport = unitReportService.getReportByUnitIdAndBuildingIdAndType(danger.getUnitId(), null,
				UnitReportType.AGAIN);
		if (againReport == null) {
			againReport = new OwnerUnitReport();
			againReport.setType(UnitReportType.AGAIN.code());
			againReport.setInspector(danger.getInspector());
			againReport.setInspectorId(danger.getInspectorId());
			againReport.setUnitId(danger.getId());
			againReport.setBuildingId(danger.getBuildingId());
			againReport.setIsDangerNotice("0");
			againReport.setIsHouseholdRate("0");
			againReport.setIsTest("0");
			againReport.setIsTestReason(null);

			// 没有初检编号的随机生成一个 TODO
			if (StrUtil.isBlank(againReport.getCode())) {
				againReport.setCode(SnowFlakeUtil.uniqueString());
			}
		}

		againReport.setDetectStatus(unitReportService.getReportDetectStatus(danger.getUnitId()).code());
		unitReportService.saveOrUpdate(againReport);

		return true;
	}

	@Override
	public List<OwnerUnitAgainDangerVo> queryOwnerUnitAgainDanger(OwnerUnitAgainDangerQuery query) {

		return this.baseMapper.queryOwnerUnitAgainDanger(query);
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

		if (StrUtil.isNotBlank(data.getPic())) {
			danger.setRectification(loginUser.getName());
			danger.setRectificationDate(new Date());
			danger.setStatus(ReExaminationStatus.FINISH.code());
		} else {
			danger.setStatus(ReExaminationStatus.RE_EXAMINATION.code());
		}

		this.saveOrUpdate(danger);

		OwnerUnitReport againReport = unitReportService.getReportByUnitIdAndBuildingIdAndType(danger.getUnitId(), null,
				UnitReportType.AGAIN);
		if (againReport == null) {
			againReport = new OwnerUnitReport();
			againReport.setType(UnitReportType.AGAIN.code());
			againReport.setInspector(danger.getInspector());
			againReport.setInspectorId(danger.getInspectorId());
			againReport.setUnitId(danger.getId());
			againReport.setBuildingId(danger.getBuildingId());
			againReport.setIsDangerNotice("0");
			againReport.setIsHouseholdRate("0");
			againReport.setIsTest("0");
			againReport.setIsTestReason(null);

			// 没有初检编号的随机生成一个 TODO
			if (StrUtil.isBlank(againReport.getCode())) {
				againReport.setCode(SnowFlakeUtil.uniqueString());
			}
		}

		againReport.setDetectStatus(unitReportService.getReportDetectStatus(danger.getUnitId()).code());
		unitReportService.saveOrUpdate(againReport);

		// 检测员整改添加整改日志
		if (StrUtil.isNotBlank(data.getPic())) {
			// 添加隐患日志
			OwnerUnitDangerLog log = new OwnerUnitDangerLog();
			log.setDangerId(danger.getId());
			log.setOperator(loginUser.getName());
			log.setOperatorId(loginUser.getUserId());
			log.setOperationType(DangerOperationType.RECTIFICATION.code());
			log.setOperationPic(data.getPic());
			log.setOperatorRole(ProjectWorkerType.INSPECTOR.code());
			log.setContent("整改");
			dangerLogService.save(log);
		}

		OwnerUnitDangerLog log = new OwnerUnitDangerLog();
		log.setDangerId(danger.getId());
		log.setOperator(loginUser.getName());
		log.setOperatorId(loginUser.getUserId());
		log.setOperationType(DangerOperationType.REVIEW_PASS.code());
		log.setOperatorRole(ProjectWorkerType.INSPECTOR.code());
		log.setContent("复检通过");
		dangerLogService.save(log);

		return true;
	}

	@Override
	public boolean notPass(@Valid DangerNotPassDto data) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		OwnerUnitDanger danger = this.baseMapper.selectById(data.getDangerId());
		if (danger == null) {
			return false;
		}

		danger.setDetectPic(data.getPic());
		danger.setReviewer(loginUser.getName());
		danger.setReviewerDate(new Date());
		danger.setReason(data.getReason());

		if ("0".equalsIgnoreCase(data.getUnableToDetect())) {
			// 状态变更待整改
			danger.setStatus(ReExaminationStatus.RECTIFIED.code());
		}

		this.saveOrUpdate(danger);

		OwnerUnitReport againReport = unitReportService.getReportByUnitIdAndBuildingIdAndType(danger.getUnitId(), null,
				UnitReportType.AGAIN);
		if (againReport == null) {
			againReport = new OwnerUnitReport();
			againReport.setType(UnitReportType.AGAIN.code());
			againReport.setInspector(danger.getInspector());
			againReport.setInspectorId(danger.getInspectorId());
			againReport.setUnitId(danger.getId());
			againReport.setBuildingId(danger.getBuildingId());
			againReport.setIsDangerNotice("0");
			againReport.setIsHouseholdRate("0");
			againReport.setIsTest("0");
			againReport.setIsTestReason(null);

			// 没有初检编号的随机生成一个 TODO
			if (StrUtil.isBlank(againReport.getCode())) {
				againReport.setCode(SnowFlakeUtil.uniqueString());
			}
		}

		againReport.setDetectStatus(unitReportService.getReportDetectStatus(danger.getUnitId()).code());
		unitReportService.saveOrUpdate(againReport);

		OwnerUnitDangerLog log = new OwnerUnitDangerLog();
		log.setDangerId(danger.getId());
		log.setOperator(loginUser.getName());
		log.setOperatorId(loginUser.getUserId());
		log.setOperationPic(data.getPic());
		if ("0".equalsIgnoreCase(data.getUnableToDetect())) {
			log.setOperationType(DangerOperationType.REVIEW_NOTPASS.code());
			log.setOperatorRole(ProjectWorkerType.INSPECTOR.code());
			log.setContent("复检不通过");
		} else {
			log.setOperationType(DangerOperationType.UNABLE_TO_DETECT.code());
			log.setOperatorRole(ProjectWorkerType.INSPECTOR.code());
			log.setContent("无法检测");
		}
		log.setRemark(data.getReason());
		dangerLogService.save(log);

		return true;
	}

}
