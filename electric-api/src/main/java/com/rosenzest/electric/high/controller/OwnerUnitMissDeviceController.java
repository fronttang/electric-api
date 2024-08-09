package com.rosenzest.electric.high.controller;

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

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.high.dto.MissDeviceDataDto.RentalHouseType1;
import com.rosenzest.electric.high.dto.MissDeviceDataDto.RentalHouseType2;
import com.rosenzest.electric.high.dto.MissDeviceDataDto.Small;
import com.rosenzest.electric.high.dto.OwnerUnitMissDeviceDto;
import com.rosenzest.electric.high.entity.OwnerUnitMissDevice;
import com.rosenzest.electric.high.service.IOwnerUnitMissDeviceService;
import com.rosenzest.electric.high.vo.OwnerUnitMissDeviceVo;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.server.base.controller.ServerBaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 缺失设备 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Api(tags = "高风险-缺失设备")
@RestController
@RequestMapping("/unit/high/miss/device")
public class OwnerUnitMissDeviceController extends ServerBaseController {

	@Autowired
	private IOwnerUnitMissDeviceService missDeviceService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IProjectWorkerService projectWorkerService;

	@Autowired
	private IOwnerUnitReportService unitReportService;

	@ApiOperation(tags = "高风险-缺失设备", value = "查询缺失设备列表")
	@GetMapping("/list/{unitId}")
	public Result<List<OwnerUnitMissDeviceVo>> list(@PathVariable Long unitId) {

		List<OwnerUnitMissDevice> missDevices = missDeviceService.findByUnitId(unitId);
		List<OwnerUnitMissDeviceVo> missDeviceVos = BeanUtils.copyList(missDevices, OwnerUnitMissDeviceVo.class);

		return Result.SUCCESS(missDeviceVos);
	}

	@ApiOperation(tags = "高风险-缺失设备", value = "添加/修改缺失设备")
	@PostMapping("")
	public Result<?> saveMissDevice(@RequestBody @Valid OwnerUnitMissDeviceDto data) {

		LoginUser loginUser = getLoginUser();
		OwnerUnit unit = ownerUnitService.getById(data.getUnitId());
		if (unit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		Project project = projectService.getById(unit.getProjectId());
		if (project == null || !ProjectType.HIGH_RISK.code().equalsIgnoreCase(project.getType())) {
			return Result.ERROR(400, "无操作权限");
		}

		if (data.getId() != null) {
			OwnerUnitMissDevice missDevice = missDeviceService.getById(data.getId());
			if (missDevice == null) {
				return Result.ERROR(400, "无操作权限");
			}

			if (!String.valueOf(loginUser.getUserId()).equalsIgnoreCase(missDevice.getCreateBy())) {

				// 工作人员权限检查
				if (!projectWorkerService.checkWorkerAreaRole(unit, loginUser.getUserId(),
						ProjectWorkerAreaRoleType.EDIT)) {
					return Result.ERROR(400, "无操作权限");
				}
			}
		}

		OwnerUnitMissDevice missDevice = new OwnerUnitMissDevice();
		BeanUtils.copyProperties(data, missDevice);

		if (data.getId() == null) {
			missDevice.setCreateBy(String.valueOf(loginUser.getUserId()));
		}
		if (missDeviceService.saveOrUpdate(missDevice)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "高风险-缺失设备", value = "删除缺失设表")
	@DeleteMapping("/{id}")
	public Result<List<OwnerUnitMissDeviceVo>> delete(@PathVariable Long id) {

		LoginUser loginUser = getLoginUser();

		OwnerUnitMissDevice missDevice = missDeviceService.getById(id);
		if (missDevice == null) {
			return Result.ERROR(400, "无操作权限");
		}

		OwnerUnit ownerUnit = ownerUnitService.getById(missDevice.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		// 检测业主单元报告状态
		OwnerUnitReport report = unitReportService.getReportByUnitIdAndType(ownerUnit.getId(), UnitReportType.INITIAL);
		if (report != null && InitialInspectionStatus.FINISH.code().equalsIgnoreCase(report.getDetectStatus())) {
			return Result.ERROR(400, "已完成初检");
		}

		if (!String.valueOf(loginUser.getUserId()).equalsIgnoreCase(missDevice.getCreateBy())) {

			if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
				return Result.ERROR(400, "无操作权限");
			}
		}

		if (missDeviceService.removeById(id)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "高风险-缺失设备", value = "出租屋楼层设备数据结构")
	@PostMapping("/rentalHouseType1")
	public Result<RentalHouseType1> rentalHouseType1(@RequestBody RentalHouseType1 data) {

		return Result.SUCCESS();
	}

	@ApiOperation(tags = "高风险-缺失设备", value = "出租屋楼栋设备数据结构")
	@PostMapping("/rentalHouseType2")
	public Result<RentalHouseType2> rentalHouseType2(@RequestBody RentalHouseType2 data) {

		return Result.SUCCESS();
	}

	@ApiOperation(tags = "高风险-缺失设备", value = "三小场所缺失设备数据结构")
	@PostMapping("/small")
	public Result<Small> small(@RequestBody Small data) {

		return Result.SUCCESS();
	}

}
