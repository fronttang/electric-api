package com.rosenzest.electric.miniapp.danger.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.ListResult;
import com.rosenzest.base.PageList;
import com.rosenzest.base.Result;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.enums.TerminalType;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.DangerPassDto;
import com.rosenzest.electric.dto.OwnerUnitDangerQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.entity.OwnerUnitDangerLog;
import com.rosenzest.electric.miniapp.dto.MiniAppOwnerUnitDangerQuery;
import com.rosenzest.electric.miniapp.owner.executor.OwnerUnitBelongPermissionExecutor;
import com.rosenzest.electric.service.IOwnerUnitDangerLogService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.vo.OwnerUnitDangerLogVo;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;
import com.rosenzest.server.base.annotation.Permission;
import com.rosenzest.server.base.annotation.PermissionParam;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.controller.ServerBaseController;
import com.rosenzest.server.base.enums.RequestParamType;
import com.rosenzest.server.base.enums.UserType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "小程序-隐患")
@TokenRule(terminal = TerminalType.MINIAPP, userType = { UserType.OWNER_UNIT, UserType.VISITOR, UserType.GRADMAN,
		UserType.AREA_USER })
@RestController
@RequestMapping("/miniapp/owner/unit/danger")
public class MiniAppOwnerUnitDangerController extends ServerBaseController {

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@Autowired
	private IOwnerUnitDangerLogService dangerLogService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Permission(type = OwnerUnitBelongPermissionExecutor.class, param = {
			@PermissionParam(name = "unitId", type = RequestParamType.PATH_VARIABLE) })
	@ApiOperation(tags = "小程序-隐患", value = "隐患列表/详情")
	@PostMapping("/list/{unitId}")
	public ListResult<OwnerUnitDangerVo> list(@PathVariable Long unitId,
			@RequestBody @Valid MiniAppOwnerUnitDangerQuery data) {

		OwnerUnitDangerQuery query = new OwnerUnitDangerQuery();
		BeanUtils.copyProperties(data, query);
		data.setUnitId(unitId);

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		List<OwnerUnitDangerVo> dangers = ownerUnitDangerService.queryOwnerUnitDanger(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), dangers);
	}

	@ApiOperation(tags = "小程序-隐患", value = "隐患检测记录")
	@GetMapping("/log/{dangerId}")
	public Result<List<OwnerUnitDangerLogVo>> dangerLogs(@PathVariable Long dangerId) {

		List<OwnerUnitDangerLog> dangerLogs = dangerLogService.listByDangerId(dangerId);

		List<OwnerUnitDangerLogVo> result = BeanUtils.copyList(dangerLogs, OwnerUnitDangerLogVo.class);
		return Result.SUCCESS(result);
	}

	@ApiOperation(tags = "小程序-隐患", value = "隐患整改")
	@PostMapping("/rectification/{dangerId}")
	public Result<?> rectification(@RequestBody @Valid DangerPassDto data) {

		OwnerUnitDanger danger = ownerUnitDangerService.getById(data.getDangerId());
		if (danger == null) {
			return Result.ERROR();
		}

		OwnerUnit ownerUnit = ownerUnitService.getById(danger.getUnitId());
		if (ownerUnit == null) {
			throw new BusinessException(ResultEnum.FORBIDDEN);
		}

		if (ownerUnitDangerService.rectification(data)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}
}
