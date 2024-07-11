package com.rosenzest.electric.high.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.HighRiskType;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.high.dto.UnitResidentialDto;
import com.rosenzest.electric.high.entity.ResidentialConfig;
import com.rosenzest.electric.high.mapper.ResidentialConfigMapper;
import com.rosenzest.electric.high.service.IResidentialConfigService;
import com.rosenzest.electric.high.vo.UnitResidentialVo;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

/**
 * <p>
 * 住宅小区 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-11
 */
@Service
public class ResidentialConfigServiceImpl extends ModelBaseServiceImpl<ResidentialConfigMapper, ResidentialConfig>
		implements IResidentialConfigService {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	@Override
	public ResidentialConfig getByUnitId(Long unitId) {
		LambdaQueryWrapper<ResidentialConfig> queryWrapper = new LambdaQueryWrapper<ResidentialConfig>();
		queryWrapper.eq(ResidentialConfig::getUnitId, unitId);
		queryWrapper.last(" LIMIT 1 ");
		return this.baseMapper.selectOne(queryWrapper);
	}

	@Override
	public boolean saveOwnerUnit(UnitResidentialDto data) {

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

		unit.setHighRiskType(HighRiskType.RENTAL_HOUSE.code());

		ownerUnitService.saveOrUpdate(unit);

		ResidentialConfig config = getByUnitId(unit.getId());
		if (config == null) {
			config = new ResidentialConfig();
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
	public UnitResidentialVo getResidentialById(Long unitId) {

		UnitResidentialVo residential = new UnitResidentialVo();

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit != null) {
			BeanUtils.copyProperties(ownerUnit, residential);
		}

		ResidentialConfig config = this.getById(unitId);
		if (config != null) {
			BeanUtils.copyProperties(config, residential);
		}

		return residential;
	}

}
