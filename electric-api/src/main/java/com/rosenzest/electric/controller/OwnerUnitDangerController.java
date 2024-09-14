package com.rosenzest.electric.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.ListResult;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.base.Result;
import com.rosenzest.base.enums.EnumUtils;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.OwnerUnitDangerDto;
import com.rosenzest.electric.dto.OwnerUnitDangerQuery;
import com.rosenzest.electric.entity.IntuitiveDetectDanger;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitArea;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.enums.IndustrialAreaBuildingType;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.formb.FormbDangerHandlerFactory;
import com.rosenzest.electric.formb.handler.IFormbDangerHandler;
import com.rosenzest.electric.service.IIntuitiveDetectDangerService;
import com.rosenzest.electric.service.IOwnerUnitAreaService;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "隐患")
@RestController
@RequestMapping("/unit/danger")
public class OwnerUnitDangerController extends ElectricBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitAreaService unitAreaService;

	// @Autowired
	// private IProjectWorkerService projectWorkerService;

	// @Autowired
	// private IOwnerUnitReportService unitReportService;

	@Autowired
	private IOwnerUnitDangerService unitDangerService;

	@Autowired
	private IIntuitiveDetectDangerService detectDangerService;

	@Autowired
	private IOwnerUnitBuildingService unitBuildingService;

	@ApiOperation(tags = "隐患", value = "添加/修改隐患")
	@PostMapping("")
	public Result<?> saveAreaDanger(@RequestBody @Valid OwnerUnitDangerDto danger) {

		LoginUser loginUser = getLoginUser();

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(danger.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		OwnerUnitDangerVo vo = new OwnerUnitDangerVo();
		BeanUtils.copyProperties(danger, vo);
		vo.setStatus(ReviewStatus.RECTIFIED.code());

		if (danger.getId() != null) {
			// id不为空是修改数据
			OwnerUnitDanger dbDanger = unitDangerService.getById(danger.getId());

			if (dbDanger == null) {
				return Result.ERROR(400, "无操作权限");
			}

			// 充电场项目检查轮次
			if (ProjectType.CHARGING_STATION.code().equalsIgnoreCase(ownerUnit.getType())) {

				if (!ownerUnit.getRounds().equals(dbDanger.getRounds())) {
					return Result.ERROR(400, "非当前轮次数据不能修改");
				}
			}

//			if (ReviewStatus.FINISH.code().equalsIgnoreCase(dbDanger.getStatus())) {
//				return Result.ERROR(400, "隐患已整改完成");
//			}

			checkPermission(dbDanger, ownerUnit);

			// 非自己数据才需要判断编辑权限
//			if (!String.valueOf(loginUser.getUserId()).equalsIgnoreCase(dbDanger.getCreateBy())) {
//
//				if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
//					return Result.ERROR(400, "无操作权限");
//				}
//			}

			if ("B14".equalsIgnoreCase(danger.getFormCode())) {
				danger.setFormId(dbDanger.getFormId());
			}

		}

		if (ProjectType.URBAN_VILLAGE.code().equalsIgnoreCase(ownerUnit.getType())) {
			if (danger.getUnitAreaId() == null) {
				return Result.ERROR(400, "公共区域/户ID不能为空");
			}

			OwnerUnitArea unitArea = unitAreaService.getByUnitId(ownerUnit.getId());
			if (unitArea == null) {
				return Result.ERROR(400, "公共区域/户ID错误");
			}

			vo.setUnitAreaId(unitArea.getId());
			vo.setUnitAreaName(unitArea.getName());
			vo.setUnitAreatype(unitArea.getType());

		} else if (ProjectType.INDUSTRIAL_AREA.code().equalsIgnoreCase(ownerUnit.getType())) {

			if ((danger.getBuildingId() == null || danger.getBuildingId() == 0)) {
				return Result.ERROR(400, "楼栋ID不能为空");
			}

			OwnerUnitBuilding building = unitBuildingService.getById(danger.getBuildingId());

			if (building == null) {
				return Result.ERROR(400, "楼栋不存在");
			}
			vo.setBuildingId(building.getId());
			vo.setBuildingName(building.getName());

			// 非配电房需要公共区域/户ID
			if (!IndustrialAreaBuildingType.POWER_ROOM.code().equalsIgnoreCase(building.getType())) {
				if (danger.getUnitAreaId() == null) {
					return Result.ERROR(400, "公共区域/户ID不能为空");
				}

				OwnerUnitArea unitArea = unitAreaService.getByUnitIdAndBuildingId(ownerUnit.getId(), building.getId());
				if (unitArea == null) {
					return Result.ERROR(400, "公共区域/户ID错误");
				}
			}

//			if (building != null && InitialInspectionStatus.FINISH.code().equalsIgnoreCase(building.getStatus())) {
//				return Result.ERROR(400, "楼栋已完成初检");
//			}
		}

		if ("B".equalsIgnoreCase(danger.getFormType())) {
			if (StrUtil.isBlank(danger.getFormCode())) {
				return Result.ERROR(400, "B类表CODE不能为空");
			}
			// 查询B类表
			if (StrUtil.isNotBlank(danger.getFormCode())) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory
						.getFormbDangerHander(danger.getFormCode());
				if (formbDangerHander != null) {
					danger.setDescription(formbDangerHander.getDescription(vo));
					danger.setSuggestions(formbDangerHander.getSuggestions(vo));
					danger.setLevel(formbDangerHander.getLevel(vo));
					// danger.setLocation(formbDangerHander.getInfoLocation(vo));
				}
			}
		} else {
//			if (StrUtil.isBlank(danger.getLocation())) {
//				return Result.ERROR(400, "隐患位置不能为空");
//			}
			if (danger.getFormId() == null) {
				return Result.ERROR(400, "A/C类表ID不能为空");
			}
		}
		// 检测业主单元报告状态
//		OwnerUnitReport report = unitReportService.getReportByUnitIdAndType(danger.getUnitId(), UnitReportType.INITIAL);
//		if (report != null && InitialInspectionStatus.FINISH.code().equalsIgnoreCase(report.getDetectStatus())) {
//			return Result.ERROR(400, "已完成初检");
//		}

		if (danger.getDangerId() != null) {
			IntuitiveDetectDanger detectDanger = detectDangerService.getById(danger.getDangerId());
			if (detectDanger != null) {
				danger.setLevel(detectDanger.getLevel());
				danger.setDescription(detectDanger.getDescription());
				danger.setSuggestions(detectDanger.getSuggestions());
				danger.setFormDataId(detectDanger.getDataId());
			}
		}

		OwnerUnitDanger areaDanger = new OwnerUnitDanger();
		BeanUtils.copyProperties(danger, areaDanger);

		areaDanger.setStatus(vo.getStatus());
		areaDanger.setInspector(loginUser.getName());
		areaDanger.setInspectorId(loginUser.getUserId());
		areaDanger.setInitialTime(new Date());
		areaDanger.setRounds(ownerUnit.getRounds());

		if (areaDanger.getId() == null) {
			areaDanger.setCreateBy(String.valueOf(loginUser.getUserId()));
		}

		if (unitDangerService.saveOrUpdateDanger(areaDanger)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "隐患", value = "删除隐患")
	@DeleteMapping("/{dangerId}")
	public Result<?> deleteAreaDanger(@PathVariable Long dangerId) {

		// LoginUser loginUser = getLoginUser();

		OwnerUnitDanger danger = unitDangerService.getById(dangerId);
		if (danger == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (ReviewStatus.FINISH.code().equalsIgnoreCase(danger.getStatus())) {
			return Result.ERROR(400, "隐患已整改完成");
		}

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(danger.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		// 充电场项目检查轮次
		if (ProjectType.CHARGING_STATION.code().equalsIgnoreCase(ownerUnit.getType())) {

			if (!ownerUnit.getRounds().equals(danger.getRounds())) {
				return Result.ERROR(400, "非当前轮次数据不能删除");
			}
		}

		checkPermission(danger, ownerUnit);

		// 不是自己的数据才判断编辑权限
//		if (!String.valueOf(loginUser.getUserId()).equalsIgnoreCase(danger.getCreateBy())) {
//
//			if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
//				return Result.ERROR(400, "无操作权限");
//			}
//		}

		// 检测业主单元报告状态
//		OwnerUnitReport report = unitReportService.getReportByUnitIdAndType(danger.getUnitId(), UnitReportType.INITIAL);
//		if (report != null && InitialInspectionStatus.FINISH.code().equalsIgnoreCase(report.getDetectStatus())) {
//			return Result.ERROR(400, "业主单元已完成初检");
//		}

//		if (danger.getBuildingId() != null) {
//			OwnerUnitBuilding building = unitBuildingService.getById(danger.getBuildingId());
//
//			if (building != null && InitialInspectionStatus.FINISH.code().equalsIgnoreCase(building.getStatus())) {
//				return Result.ERROR(400, "楼栋已完成初检");
//			}
//		}

		if (unitDangerService.removeDanger(danger)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "隐患", value = "隐患列表(初检)")
	@PostMapping("/initial/list")
	public ListResult<OwnerUnitDangerVo> dangerList(@RequestBody @Valid OwnerUnitDangerQuery query) {

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		List<OwnerUnitDangerVo> dangers = unitDangerService.queryOwnerUnitDanger(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), dangers);
	}

	@ApiOperation(tags = "隐患", value = "隐患列表(复检)")
	@PostMapping("/list")
	public ListResult<OwnerUnitDangerVo> reviewDangerList(@RequestBody @Valid OwnerUnitDangerQuery query) {

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		List<OwnerUnitDangerVo> dangers = unitDangerService.queryReviewOwnerUnitDanger(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), dangers);
	}

	@ApiOperation(tags = "隐患", value = "隐患详情")
	@PostMapping("/info/{dangerId}")
	public Result<OwnerUnitDangerVo> dangerInfo(@Autowired Long dangerId) {

		OwnerUnitDangerVo danger = unitDangerService.getOwnerUnitDangerById(dangerId);
		return Result.SUCCESS(danger);
	}

	@ApiOperation(tags = "隐患", value = "重置隐患状态")
	@GetMapping("/reset/status/{dangerId}/{status}")
	public Result<?> resetDangerStatus(Long dangerId, String status) {

		OwnerUnitDanger danger = unitDangerService.getById(dangerId);
		if (danger == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!EnumUtils.init(ReviewStatus.class).isInEnum(status)) {
			return Result.ERROR(400, "状态参数不对");
		}

		if (StrUtil.isBlank(danger.getStatus()) || ReviewStatus.NOTDANGER.code().equalsIgnoreCase(danger.getStatus())) {
			return Result.ERROR(400, "非隐患不能修改状态");
		}

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(danger.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		// 充电场项目检查轮次
		if (ProjectType.CHARGING_STATION.code().equalsIgnoreCase(ownerUnit.getType())) {

			if (!ownerUnit.getRounds().equals(danger.getRounds())) {
				return Result.ERROR(400, "非当前轮次数据不能修改");
			}
		}

		checkPermission(danger, ownerUnit);

		OwnerUnitDanger update = new OwnerUnitDanger();
		update.setId(dangerId);
		update.setStatus(status);

		if (unitDangerService.updateById(update)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

}
