package com.rosenzest.electric.station.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.station.dto.ChargingStationDto;
import com.rosenzest.electric.station.service.IChargingStationService;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

@Service
public class ChargingStationServiceImpl implements IChargingStationService {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	@Override
	public boolean saveChargingStation(ChargingStationDto data) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		OwnerUnit unit = new OwnerUnit();
		BeanUtils.copyProperties(data, unit);

		Project project = projectService.getById(data.getProjectId());
		if (project != null) {
			unit.setProjectName(project.getName());
			unit.setDetectId(project.getDetectId());
			unit.setType(project.getType());
		}
		if (data.getId() == null) {
			// 默认第一轮
			unit.setRounds(1);
			unit.setCreateBy(String.valueOf(loginUser.getUserId()));
		} else {
			if ("admin".equalsIgnoreCase(data.getCreateBy())) {
				unit.setCreateBy(String.valueOf(loginUser.getUserId()));
			}
		}

		ownerUnitService.saveOrUpdate(unit);

		data.setId(unit.getId());
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

}
