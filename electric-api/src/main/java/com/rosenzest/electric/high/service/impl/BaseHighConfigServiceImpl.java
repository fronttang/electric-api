package com.rosenzest.electric.high.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.rosenzest.electric.high.entity.BaseHighConfigEntity;
import com.rosenzest.electric.high.service.BaseHighConfigService;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.model.base.mapper.ModelBaseMapper;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

@SuppressWarnings("unchecked")
public abstract class BaseHighConfigServiceImpl<M extends ModelBaseMapper<T>, T extends BaseHighConfigEntity<T>, DTO extends BaseHighDto, VO>
		extends ModelBaseServiceImpl<M, T> implements BaseHighConfigService<T, DTO, VO> {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	protected Class<VO> voClass = currentVoClass();

	protected Class<VO> currentVoClass() {
		// (Class<T>)ReflectionKit.getSuperClassGenericType(getClass(), 0);
		return (Class<VO>) this.getResolvableType().as(BaseHighConfigServiceImpl.class).getGeneric(3).getType();
	}

	@Override
	protected Class<T> currentMapperClass() {
		// (Class<T>)ReflectionKit.getSuperClassGenericType(getClass(), 0);
		return (Class<T>) this.getResolvableType().as(BaseHighConfigServiceImpl.class).getGeneric(0).getType();
	}

	@Override
	protected Class<T> currentModelClass() {
		// (Class<T>)ReflectionKit.getSuperClassGenericType(getClass(), 1);
		return (Class<T>) this.getResolvableType().as(BaseHighConfigServiceImpl.class).getGeneric(1).getType();
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

		T config = this.getById(unit.getId());
		if (config == null) {
			config = this.entityClass.newInstance();
		}

		BeanUtils.copyProperties(data, config);
		config.setUnitId(unit.getId());
		this.saveOrUpdate(config);

		// 初检报告
		OwnerUnitReport report = ownerUnitReportService.getReportByUnitIdAndType(unit.getId(), UnitReportType.INITIAL);
		if (report == null) {
			report = new OwnerUnitReport();
		}
		report.setUnitId(unit.getId());
		report.setType(UnitReportType.INITIAL.code());
		// report.setCode(data.getInitialTestNo());
		report.setDetectData(new Date());
		report.setDetectStatus(InitialInspectionStatus.CHECKING.code());
		report.setInspector(loginUser.getName());
		report.setInspectorId(loginUser.getUserId());

		// 复检报告
		OwnerUnitReport againReport = ownerUnitReportService.getReportByUnitIdAndType(unit.getId(),
				UnitReportType.REVIEW);
		if (againReport == null) {
			againReport = new OwnerUnitReport();
		}
		againReport.setUnitId(unit.getId());
		againReport.setType(UnitReportType.REVIEW.code());
		// againReport.setCode(data.getAgainTestNo());
		// againReport.setDetectData(data.getAgainTestData());
		againReport.setDetectStatus(ReviewStatus.RECTIFIED.code());
		againReport.setInspector(loginUser.getName());
		againReport.setInspectorId(loginUser.getUserId());

		ownerUnitReportService.saveOrUpdate(againReport);
		ownerUnitReportService.saveOrUpdate(report);

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

		T config = this.getById(unitId);
		if (config != null) {
			BeanUtils.copyProperties(config, vo);
		}

		return vo;
	}

}
