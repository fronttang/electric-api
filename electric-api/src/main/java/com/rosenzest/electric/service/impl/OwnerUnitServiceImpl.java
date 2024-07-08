package com.rosenzest.electric.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.OwnerUnitAgainQueryDto;
import com.rosenzest.electric.dto.OwnerUnitDto;
import com.rosenzest.electric.dto.OwnerUnitQueryDto;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ReExaminationStatus;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.mapper.OwnerUnitMapper;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.vo.AgainOwnerUnitVo;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

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
	private IProjectService projectService;

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	@Override
	public List<InitialOwnerUnitVo> queryInitialList(OwnerUnitQueryDto query, PageList pageList) {

		Page<InitialOwnerUnitVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
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

		Project project = projectService.getById(data.getProjectId());
		if (project != null) {
			data.setProjectName(project.getName());
			data.setDetectId(project.getDetectId());
			data.setType(project.getType());
		}

		OwnerUnit unit = new OwnerUnit();
		BeanUtils.copyProperties(data, unit);

		this.saveOrUpdate(unit);

		// 初检报告
		OwnerUnitReport report = ownerUnitReportService.getReportByUnitIdAndBuildingIdAndType(unit.getId(), null,
				UnitReportType.INITIAL);
		if (report == null) {
			report = new OwnerUnitReport();
		}
		report.setUnitId(unit.getId());
		report.setType(UnitReportType.INITIAL.code());
		report.setCode(data.getInitialTestNo());
		report.setDetectData(new Date());
		report.setDetectStatus(InitialInspectionStatus.CHECKING.code());
		report.setInspector(loginUser.getName());
		report.setInspectorId(loginUser.getUserId());

		// 复检报告
		OwnerUnitReport againReport = ownerUnitReportService.getReportByUnitIdAndBuildingIdAndType(unit.getId(), null,
				UnitReportType.AGAIN);
		if (againReport == null) {
			againReport = new OwnerUnitReport();
		}
		againReport.setUnitId(unit.getId());
		againReport.setType(UnitReportType.AGAIN.code());
		againReport.setCode(data.getAgainTestNo());
		againReport.setDetectData(data.getAgainTestData());
		againReport.setDetectStatus(ReExaminationStatus.RECTIFIED.code());
		againReport.setInspector(loginUser.getName());
		againReport.setInspectorId(loginUser.getUserId());

		ownerUnitReportService.saveOrUpdate(againReport);
		ownerUnitReportService.saveOrUpdate(report);

		return true;
	}

	@Override
	public List<AgainOwnerUnitVo> queryAginList(OwnerUnitAgainQueryDto query, PageList pageList) {
		Page<AgainOwnerUnitVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
		List<AgainOwnerUnitVo> list = this.baseMapper.queryAginList(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

}
