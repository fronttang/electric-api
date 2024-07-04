package com.rosenzest.electric.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.ListResult;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.base.Result;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.base.util.SnowFlakeUtil;
import com.rosenzest.electric.dto.InitialOwnerUnitSettingDto;
import com.rosenzest.electric.dto.OwnerUnitAreaDto;
import com.rosenzest.electric.dto.OwnerUnitDto;
import com.rosenzest.electric.dto.OwnerUnitQueryDto;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitArea;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.service.IOwnerUnitAreaService;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitAreaVo;
import com.rosenzest.electric.vo.OwnerUnitVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "业主单元(城中村/工业园)")
@RestController
@RequestMapping("/unit")
public class OwnerUnitController extends ServerBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitAreaService ownerUnitAreaService;

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	@Autowired
	private IOwnerUnitBuildingService ownerUnitBuildingService;

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "公共区域/户列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", required = false, value = "类型,见字典:owner_unit_area_type"),
			@ApiImplicitParam(name = "unitId", required = true, value = "业主单元ID"),
			@ApiImplicitParam(name = "buildingId", required = false, value = "楼栋ID") })
	@GetMapping("/area/list")
	public Result<List<OwnerUnitAreaVo>> listArea(@RequestParam(name = "unitId") Long unitId,
			@RequestParam(name = "buildingId", required = false) Long buildingId,
			@RequestParam(name = "type", required = false) String type) {

		List<OwnerUnitAreaVo> unitAreas = ownerUnitAreaService.queryUnitAreaByType(unitId, buildingId, type);

		return Result.SUCCESS(unitAreas);
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "新增/修改公共区域/户")
	@PostMapping("/area")
	public Result<?> saveArea(@RequestBody OwnerUnitAreaDto data) {

		OwnerUnitArea entity = new OwnerUnitArea();
		BeanUtils.copyProperties(data, entity);

		ownerUnitAreaService.saveOrUpdate(entity);

		return Result.SUCCESS();
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "删除公共区域/户")
	@DeleteMapping("/area/{id}")
	public Result<?> deleteArea(Long id) {

		ownerUnitAreaService.removeById(id);

		return Result.SUCCESS();
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "初检列表")
	@PostMapping("/initial/list")
	public ListResult<InitialOwnerUnitVo> list(@RequestBody @Valid OwnerUnitQueryDto query) {

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		List<InitialOwnerUnitVo> unitList = ownerUnitService.queryInitialList(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), unitList);
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "设置初检状态")
	@PostMapping("/setting")
	public Result<?> setting(@RequestBody @Valid InitialOwnerUnitSettingDto data) {

		LoginUser loginUser = getLoginUser();

		OwnerUnit ownerUnit = ownerUnitService.getById(data.getId());
		if (ownerUnit == null) {
			throw new BusinessException(400, "业主单元不存在");
		}

		if (data.getBuildingId() != null) {
			OwnerUnitBuilding building = ownerUnitBuildingService.getById(data.getBuildingId());
			if (building == null) {
				throw new BusinessException(400, "楼栋不存在");
			}
		}

		OwnerUnitReport report = ownerUnitReportService.getReportByUnitIdAndBuildingId(data.getId(),
				data.getBuildingId());

		if (report == null) {
			report = new OwnerUnitReport();
		}
		report.setInspector(loginUser.getName());
		report.setInspectorId(loginUser.getUserId());
		report.setUnitId(data.getId());
		report.setBuildingId(data.getBuildingId());
		report.setIsDangerNotice(data.getIsDangerNotice());
		report.setIsHouseholdRate(data.getIsHouseholdRate());
		report.setIsTest(data.getIsTest());
		report.setIsTestReason(data.getIsTestReason());

		if ("1".equalsIgnoreCase(data.getIsHouseholdRate())) {
			report.setInitialTestStatus(InitialInspectionStatus.FINISH.code());
		} else if ("1".equalsIgnoreCase(data.getIsTest())) {
			report.setInitialTestStatus(InitialInspectionStatus.UNABLE_TO_DETECT.code());
		} else {
			report.setInitialTestStatus(InitialInspectionStatus.CHECKING.code());
		}

		// 没有初检编号的随机生成一个 TODO
		if (StrUtil.isBlank(report.getInitialTestNo())) {
			report.setInitialTestNo(SnowFlakeUtil.uniqueString());
		}

		boolean save = ownerUnitReportService.saveOrUpdate(report);
		if (save) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "查询业主单元信息")
	@GetMapping("/{unitId}")
	public Result<OwnerUnitVo> unitInfo(@PathVariable Long unitId) {

		OwnerUnitVo ownerUnit = ownerUnitService.getOwnerUnitById(unitId);

		return Result.SUCCESS(ownerUnit);
	}

	@ApiOperation(tags = "业主单元(城中村/工业园)", value = "添加/修改业主单元信息")
	@PostMapping("/")
	public Result<?> saveUnit(@RequestBody @Valid OwnerUnitDto data) {

		// 查询用户权限 TODO

		//
		ownerUnitService.saveOwnerUnit(data);

		return Result.SUCCESS();
	}
}
