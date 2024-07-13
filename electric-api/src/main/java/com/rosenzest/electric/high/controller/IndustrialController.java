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
import com.rosenzest.electric.high.dto.UnitIndustrialDto;
import com.rosenzest.electric.high.entity.IndustrialConfig;
import com.rosenzest.electric.high.service.IIndustrialConfigService;
import com.rosenzest.electric.high.vo.UnitIndustrialVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 工业企业 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Api(tags = "业主单元(高风险-工业企业)")
@RestController
@RequestMapping("/unit/high/industrial")
public class IndustrialController extends BaseHighController<IndustrialConfig, UnitIndustrialDto, UnitIndustrialVo> {

	@Autowired
	private IIndustrialConfigService industrialConfigService;

	@ApiOperation(tags = "业主单元(高风险-工业企业)", value = "添加/修改工业企业")
	@PostMapping("")
	public Result<UnitIndustrialVo> saveIndustrial(@RequestBody @Valid UnitIndustrialDto data) throws Exception {
		return super.save(data);
	}

	@ApiOperation(tags = "业主单元(高风险-工业企业)", value = "查询工业企业信息")
	@GetMapping("/{unitId}")
	public Result<UnitIndustrialVo> getIndustrial(@PathVariable Long unitId) throws Exception {
		return super.getById(unitId);
	}

	@Override
	public IIndustrialConfigService getConfigService() {
		return industrialConfigService;
	}

}
