package com.rosenzest.electric.high.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.Result;
import com.rosenzest.electric.high.config.SmallConfig;
import com.rosenzest.electric.high.dto.UnitSmallDto;
import com.rosenzest.electric.high.service.ISmallConfigService;
import com.rosenzest.electric.high.vo.UnitSmallVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 三小场所 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Api(tags = "业主单元(高风险-三小场所)")
@RestController
@RequestMapping("/unit/high/small")
public class SmallConfigController extends BaseHighController<SmallConfig, UnitSmallDto, UnitSmallVo> {

	@Autowired
	private ISmallConfigService smallConfigService;

	@Override
	public ISmallConfigService getConfigService() {
		return this.smallConfigService;
	}

	@ApiOperation(tags = "业主单元(高风险-三小场所)", value = "添加/修改三小场所")
	@PostMapping("")
	public Result<UnitSmallVo> saveSmall(@RequestBody @Valid UnitSmallDto data) throws Exception {
		return this.save(data);
	}

	@ApiOperation(tags = "业主单元(高风险-三小场所)", value = "查询三小场所信息")
	@GetMapping("/{unitId}")
	public Result<UnitSmallVo> getSmall(@PathVariable Long unitId) throws Exception {
		return this.getById(unitId);
	}

}
