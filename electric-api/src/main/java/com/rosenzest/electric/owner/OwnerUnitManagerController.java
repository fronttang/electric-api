package com.rosenzest.electric.owner;

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
import com.rosenzest.electric.owner.executor.OwnerUnitPermissionExecutor;
import com.rosenzest.electric.owner.vo.OwnerUnitBuildingDangerStatisticsVo;
import com.rosenzest.electric.owner.vo.OwnerUnitDangerStatisticsVo;
import com.rosenzest.electric.owner.vo.OwnerUnitListVo;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.server.base.annotation.Permission;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.controller.ServerBaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 业主单元端
 */

@Api(tags = "业主单元端")
@RestController
@RequestMapping("/manager/owner/unit")
@TokenRule(terminal = TerminalType.MINIAPP)
@Permission(OwnerUnitPermissionExecutor.class)
public class OwnerUnitManagerController extends ServerBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitBuildingService ownerUnitBuildingService;

	@ApiOperation(tags = "业主单元端", value = "业主单元列表")
	@GetMapping("/list")
	public Result<List<OwnerUnitListVo>> ownerUnitList() {

		LoginUser loginUser = getLoginUser();
		Long projectId = loginUser.getProjectId();

		List<OwnerUnitListVo> list = ownerUnitService.getOwnerUnitListByOwner(loginUser.getUserId(), projectId);

		return Result.SUCCESS(list);
	}

	@ApiOperation(tags = "业主单元端", value = "业主单元楼栋列表")
	@GetMapping("/building/list/{unitId}")
	public Result<List<OwnerUnitBuildingDangerStatisticsVo>> ownerUnitBuildingList(@PathVariable Long unitId,
			@RequestParam(required = false) String keyword) {

		LoginUser loginUser = getLoginUser();

		if (!ownerUnitService.checkOwnerUnitManager(unitId, loginUser.getUserId())) {
			throw new BusinessException(ResultEnum.FORBIDDEN);
		}

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit == null) {
			throw new BusinessException(ResultEnum.FORBIDDEN);
		}

		List<OwnerUnitBuildingDangerStatisticsVo> result = ownerUnitBuildingService
				.getOwnerUnitBuildingDangerStatistics(unitId, keyword);

		return Result.SUCCESS(result);
	}

	@ApiOperation(tags = "业主单元端", value = "业主单元隐患详情")
	@GetMapping("/danger/{unitId}")
	public Result<OwnerUnitDangerStatisticsVo> ownerUnitDangerStatistics(@PathVariable Long unitId,
			@RequestParam(required = false) Long buildingId) {

		return ownerUnitBuildingDangerStatistics(unitId, null);
	}

	@ApiOperation(tags = "业主单元端", value = "业主单元楼栋隐患详情")
	@GetMapping("/danger/{unitId}/{buildingId}")
	public Result<OwnerUnitDangerStatisticsVo> ownerUnitBuildingDangerStatistics(@PathVariable Long unitId,
			@PathVariable Long buildingId) {

		LoginUser loginUser = getLoginUser();

		if (!ownerUnitService.checkOwnerUnitManager(unitId, loginUser.getUserId())) {
			throw new BusinessException(ResultEnum.FORBIDDEN);
		}

		OwnerUnitDangerStatisticsVo result = ownerUnitService.getOwnerUnitDangerStatistics(unitId, buildingId);

		return Result.SUCCESS(result);
	}
}
