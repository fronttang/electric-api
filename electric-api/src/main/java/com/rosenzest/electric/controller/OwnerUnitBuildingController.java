package com.rosenzest.electric.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.Result;
import com.rosenzest.server.base.controller.ServerBaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Api(tags = "业主单元(工业园)")
@RestController
@RequestMapping("/unit/building")
public class OwnerUnitBuildingController extends ServerBaseController {

	@ApiOperation(tags = "业主单元(工业园)", value = "添加/删除楼栋")
	@PostMapping("")
	public Result<?> saveBuilding() {

		return Result.SUCCESS();
	}
}
