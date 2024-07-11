package com.rosenzest.electric.high.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.rosenzest.electric.high.dto.UnitRentalHouseDto;
import com.rosenzest.electric.high.entity.RentalHouseConfig;
import com.rosenzest.electric.high.mapper.RentalHouseConfigMapper;
import com.rosenzest.electric.high.service.IRentalHouseConfigService;
import com.rosenzest.electric.high.vo.UnitRentalHouseVo;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

/**
 * <p>
 * 出租屋消防配置 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-11
 */
@Service
public class RentalHouseConfigServiceImpl extends ModelBaseServiceImpl<RentalHouseConfigMapper, RentalHouseConfig>
		implements IRentalHouseConfigService {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	@Override
	@Transactional
	public boolean saveOwnerUnit(UnitRentalHouseDto data) {

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

		RentalHouseConfig config = getByUnitId(unit.getId());
		if (config == null) {
			config = new RentalHouseConfig();
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
	public RentalHouseConfig getByUnitId(Long unitId) {
		LambdaQueryWrapper<RentalHouseConfig> queryWrapper = new LambdaQueryWrapper<RentalHouseConfig>();
		queryWrapper.eq(RentalHouseConfig::getUnitId, unitId);
		queryWrapper.last(" LIMIT 1 ");
		return this.baseMapper.selectOne(queryWrapper);
	}

	@Override
	public UnitRentalHouseVo getRentalHouseById(Long unitId) {

		UnitRentalHouseVo rentalHouse = new UnitRentalHouseVo();

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit != null) {
			BeanUtils.copyProperties(ownerUnit, rentalHouse);
		}

		RentalHouseConfig config = this.getById(unitId);
		if (config != null) {
			BeanUtils.copyProperties(config, rentalHouse);
		}

		return rentalHouse;
	}

}
