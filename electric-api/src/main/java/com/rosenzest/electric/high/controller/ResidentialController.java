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
import com.rosenzest.electric.high.dto.UnitResidentialDto;
import com.rosenzest.electric.high.entity.ResidentialConfig;
import com.rosenzest.electric.high.service.IResidentialConfigService;
import com.rosenzest.electric.high.vo.UnitResidentialVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 住宅小区 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-11
 */
@Api(tags = "业主单元(高风险-住宅小区)")
@RestController
@RequestMapping("/unit/high/residential")
public class ResidentialController
		extends BaseHighController<ResidentialConfig, UnitResidentialDto, UnitResidentialVo> {

	@Autowired
	private IResidentialConfigService residentialConfigService;

	@ApiOperation(tags = "业主单元(高风险-住宅小区)", value = "添加/修改住宅小区")
	@PostMapping("")
	public Result<UnitResidentialVo> saveResidential(@RequestBody @Valid UnitResidentialDto data) throws Exception {
		return this.save(data);
	}

	@ApiOperation(tags = "业主单元(高风险-住宅小区)", value = "查询住宅小区信息")
	@GetMapping("/{unitId}")
	public Result<UnitResidentialVo> getResidential(@PathVariable Long unitId) throws Exception {
		return this.getById(unitId);
	}

	@Override
	public IResidentialConfigService getConfigService() {
		return this.residentialConfigService;
	}

}
