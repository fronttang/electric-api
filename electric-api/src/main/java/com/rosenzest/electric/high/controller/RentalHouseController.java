package com.rosenzest.electric.high.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.high.dto.UnitRentalHouseDto;
import com.rosenzest.electric.high.service.IRentalHouseConfigService;
import com.rosenzest.electric.high.vo.UnitRentalHouseVo;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "业主单元(高风险-出租屋)")
@RestController
@RequestMapping("/unit/high/rentalhouse")
public class RentalHouseController extends ServerBaseController {

	@Autowired
	private IRentalHouseConfigService rentalHouseConfigService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IProjectWorkerService projectWorkerService;

	@ApiOperation(tags = "业主单元(高风险-出租屋)", value = "添加/修改出租屋")
	@PostMapping("")
	public Result<?> saveRentalHouse(@RequestBody @Valid UnitRentalHouseDto data) {

		LoginUser loginUser = getLoginUser();
		OwnerUnit unit = new OwnerUnit();
		BeanUtils.copyProperties(data, unit);

		Project project = projectService.getById(data.getProjectId());
		if (project == null || !ProjectType.HIGH_RISK.code().equalsIgnoreCase(project.getType())) {
			return Result.ERROR(400, "无操作权限");
		}

		if (StrUtil.isBlank(unit.getDistrict())) {
			return Result.ERROR(400, "区ID不能为空");
		} else if (StrUtil.isBlank(unit.getStreet())) {
			return Result.ERROR(400, "街道ID不能为空");
		} else if (StrUtil.isBlank(unit.getCommunity())) {
			return Result.ERROR(400, "社区ID不能为空");
		}

		// 工作人员权限检查
		if (!projectWorkerService.checkWorkerAreaRole(unit, loginUser.getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
			return Result.ERROR(400, "无操作权限");
		}

		if (rentalHouseConfigService.saveOwnerUnit(data)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元(高风险-出租屋)", value = "查询出租屋信息")
	@GetMapping("/{unitId}")
	public Result<UnitRentalHouseVo> getRentalHouse(@PathVariable Long unitId) {

		UnitRentalHouseVo rentalHouse = rentalHouseConfigService.getRentalHouseById(unitId);
		return Result.SUCCESS(rentalHouse);
	}

}
