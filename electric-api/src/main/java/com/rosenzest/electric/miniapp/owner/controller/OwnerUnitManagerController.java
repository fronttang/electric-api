package com.rosenzest.electric.miniapp.owner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.enums.TerminalType;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.miniapp.owner.executor.OwnerUnitBelongPermissionExecutor;
import com.rosenzest.electric.miniapp.owner.executor.OwnerUnitPermissionExecutor;
import com.rosenzest.electric.miniapp.vo.OwnerUnitDangerStatisticsVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitOverviewVo;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.server.base.annotation.Permission;
import com.rosenzest.server.base.annotation.PermissionParam;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.controller.ServerBaseController;
import com.rosenzest.server.base.enums.RequestParamType;
import com.rosenzest.server.base.enums.UserType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 业主单元端
 */

@Api(tags = "业主单元端")
@RestController
@RequestMapping("/miniapp/manager/owner/unit")
@TokenRule(terminal = TerminalType.MINIAPP, userType = { UserType.OWNER_UNIT, UserType.VISITOR })
@Permission(type = OwnerUnitBelongPermissionExecutor.class, param = {
		@PermissionParam(name = "unitId", type = RequestParamType.PATH_VARIABLE) })
public class OwnerUnitManagerController extends ServerBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitBuildingService ownerUnitBuildingService;

	@Permission(OwnerUnitPermissionExecutor.class)
	@ApiOperation(tags = "业主单元端", value = "业主单元列表")
	@GetMapping("/list")
	public Result<List<OwnerUnitOverviewVo>> ownerUnitList() {

		LoginUser loginUser = getLoginUser();
		Long projectId = loginUser.getProjectId();

		List<OwnerUnitOverviewVo> list = ownerUnitService.getOwnerUnitListByOwner(loginUser.getUserId(), projectId);

		return Result.SUCCESS(list);
	}

	@ApiOperation(tags = "业主单元端", value = "业主单元楼栋列表")
	@GetMapping("/building/list/{unitId}")
	public Result<List<OwnerUnitDangerStatisticsVo>> ownerUnitBuildingList(@PathVariable Long unitId,
			@RequestParam(required = false) String keyword) {

		// LoginUser loginUser = getLoginUser();

		// if (!ownerUnitService.checkOwnerUnitManager(unitId, loginUser.getUserId())) {
		// throw new BusinessException(ResultEnum.FORBIDDEN);
		// }

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit == null) {
			throw new BusinessException(ResultEnum.FORBIDDEN);
		}

		List<OwnerUnitDangerStatisticsVo> result = ownerUnitBuildingService.getOwnerUnitBuildingDangerStatistics(unitId,
				keyword);

		return Result.SUCCESS(result);
	}

	@ApiOperation(tags = "业主单元端", value = "业主单元隐患汇总")
	@GetMapping("/danger/{unitId}")
	public Result<OwnerUnitDangerStatisticsVo> ownerUnitDangerStatistics(@PathVariable Long unitId) {

		return ownerUnitBuildingDangerStatistics(unitId, null);
	}

	@ApiOperation(tags = "业主单元端", value = "业主单元楼栋隐患汇总")
	@GetMapping("/danger/{unitId}/{buildingId}")
	public Result<OwnerUnitDangerStatisticsVo> ownerUnitBuildingDangerStatistics(@PathVariable Long unitId,
			@PathVariable Long buildingId) {

		LoginUser loginUser = getLoginUser();

//		if (!ownerUnitService.checkOwnerUnitManager(unitId, loginUser.getUserId())) {
//			throw new BusinessException(ResultEnum.FORBIDDEN);
//		}

		OwnerUnitDangerStatisticsVo result = ownerUnitService.getOwnerUnitDangerStatistics(unitId, buildingId);
		result.setUserName(loginUser.getName());
		return Result.SUCCESS(result);
	}
}
