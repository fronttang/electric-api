package com.rosenzest.electric.high.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.HighRiskType;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.high.dto.BaseHighDto;
import com.rosenzest.electric.high.entity.OwnerUnitConfig;
import com.rosenzest.electric.high.service.BaseHighConfigService;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

import cn.hutool.core.bean.BeanUtil;

@SuppressWarnings("unchecked")
public abstract class BaseHighConfigServiceImpl<C, DTO extends BaseHighDto, VO> extends OwnerUnitConfigServiceImpl
		implements BaseHighConfigService<C, DTO, VO> {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	protected Class<VO> voClass = currentVoClass();

	protected Class<C> configClass = currentConfigClass();

	protected Class<VO> currentVoClass() {
		// (Class<T>)ReflectionKit.getSuperClassGenericType(getClass(), 0);
		return (Class<VO>) this.getResolvableType().as(BaseHighConfigServiceImpl.class).getGeneric(2).getType();
	}

	protected Class<C> currentConfigClass() {
		// (Class<T>)ReflectionKit.getSuperClassGenericType(getClass(), 0);
		return (Class<C>) this.getResolvableType().as(BaseHighConfigServiceImpl.class).getGeneric(0).getType();
	}

	protected abstract HighRiskType getHighRiskType();

	@Override
	public boolean saveOwnerUnit(DTO data) throws Exception {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		OwnerUnit unit = new OwnerUnit();
		BeanUtils.copyProperties(data, unit);

		Project project = projectService.getById(unit.getProjectId());
		if (project != null) {
			unit.setProjectName(project.getName());
			unit.setDetectId(project.getDetectId());
			unit.setType(project.getType());
		}

		unit.setHighRiskType(getHighRiskType().code());

		if (unit.getId() == null) {
			unit.setCreateBy(String.valueOf(loginUser.getUserId()));
		}

		ownerUnitService.saveOrUpdate(unit);
		data.setId(unit.getId());

		OwnerUnitConfig unitConfig = super.getById(unit.getId());
		if (unitConfig == null) {
			unitConfig = new OwnerUnitConfig();
			unitConfig.setUnitId(unit.getId());
			unitConfig.setCreateBy(String.valueOf(loginUser.getUserId()));
		}

		C config = configClass.newInstance();
		BeanUtils.copyProperties(data, config);

		JSONObject bean = BeanUtil.toBean(config, JSONObject.class);
		unitConfig.setConfig(bean);
		this.saveOrUpdate(unitConfig);

		// 初检报告
		OwnerUnitReport report = ownerUnitReportService.getReportByUnitIdAndType(unit.getId(), UnitReportType.INITIAL);
		if (report == null) {
			report = new OwnerUnitReport();
			report.setUnitId(unit.getId());
			report.setType(UnitReportType.INITIAL.code());
			// report.setCode(data.getInitialTestNo());
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
			// againReport.setCode(data.getAgainTestNo());
			// againReport.setDetectData(data.getAgainTestData());
			againReport.setDetectStatus(ReviewStatus.RECTIFIED.code());
			againReport.setInspector(loginUser.getName());
			againReport.setInspectorId(loginUser.getUserId());

			ownerUnitReportService.saveOrUpdate(againReport);
		}
		return true;
	}

	@Override
	public VO getOwnerUnitById(Long unitId) throws Exception {

		VO vo = this.voClass.newInstance();

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);

		if (ownerUnit == null || !getHighRiskType().code().equalsIgnoreCase(ownerUnit.getHighRiskType())) {
			throw new BusinessException("非该类型业主单元");
		}

		if (ownerUnit != null) {
			BeanUtils.copyProperties(ownerUnit, vo);
		}

		OwnerUnitConfig config = super.getById(unitId);
		if (config != null) {
			JSONObject jsonConfig = config.getConfig();
			C bean = jsonConfig.toJavaObject(configClass);
			BeanUtils.copyProperties(bean, vo);
		}

		return vo;
	}

}
