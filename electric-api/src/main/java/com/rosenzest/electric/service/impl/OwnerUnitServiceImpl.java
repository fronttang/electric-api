package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.OwnerUnitDto;
import com.rosenzest.electric.dto.OwnerUnitQueryDto;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.mapper.OwnerUnitMapper;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
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

		OwnerUnitReport report = ownerUnitReportService.getReportByUnitIdAndBuildingId(unit.getId(), null);
		if (report == null) {
			report = new OwnerUnitReport();
		}
		report.setUnitId(unit.getId());
		report.setAgainTestData(data.getAgainTestData());
		report.setAgainTestNo(data.getAgainTestNo());
		report.setInitialTestNo(data.getInitialTestNo());
		report.setInitialTestStatus(InitialInspectionStatus.CHECKING.code());
		report.setInspector(loginUser.getName());
		report.setInspectorId(loginUser.getUserId());

		ownerUnitReportService.saveOrUpdate(report);

		return true;
	}

}
