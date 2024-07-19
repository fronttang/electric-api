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
import com.rosenzest.electric.high.config.ComplexConfig;
import com.rosenzest.electric.high.dto.UnitComplexDto;
import com.rosenzest.electric.high.service.IComplexConfigService;
import com.rosenzest.electric.high.vo.UnitComplexVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 大型综合体 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Api(tags = "业主单元(高风险-大型综合体)")
@RestController
@RequestMapping("/unit/high/complex")
public class ComplexController extends BaseHighController<ComplexConfig, UnitComplexDto, UnitComplexVo> {

	@Autowired
	private IComplexConfigService complexConfigService;

	@ApiOperation(tags = "业主单元(高风险-大型综合体)", value = "添加/修改大型综合体")
	@PostMapping("")
	public Result<UnitComplexVo> saveComplex(@RequestBody @Valid UnitComplexDto data) throws Exception {

		return super.save(data);
	}

	@ApiOperation(tags = "业主单元(高风险-大型综合体)", value = "查询大型综合体信息")
	@GetMapping("/{unitId}")
	public Result<UnitComplexVo> getComplex(@PathVariable Long unitId) throws Exception {

		return super.getById(unitId);
	}

	@Override
	public IComplexConfigService getConfigService() {
		return complexConfigService;
	}

}
