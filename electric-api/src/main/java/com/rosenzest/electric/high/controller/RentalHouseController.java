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
import com.rosenzest.electric.high.config.RentalHouseConfig;
import com.rosenzest.electric.high.dto.UnitRentalHouseDto;
import com.rosenzest.electric.high.service.IRentalHouseConfigService;
import com.rosenzest.electric.high.vo.UnitRentalHouseVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "业主单元(高风险-出租屋)")
@RestController
@RequestMapping("/unit/high/rentalhouse")
public class RentalHouseController
		extends BaseHighController<RentalHouseConfig, UnitRentalHouseDto, UnitRentalHouseVo> {

	@Autowired
	private IRentalHouseConfigService rentalHouseConfigService;

	@ApiOperation(tags = "业主单元(高风险-出租屋)", value = "添加/修改出租屋")
	@PostMapping("")
	public Result<UnitRentalHouseVo> saveRentalHouse(@RequestBody @Valid UnitRentalHouseDto data) throws Exception {

		return this.save(data);
	}

	@ApiOperation(tags = "业主单元(高风险-出租屋)", value = "查询出租屋信息")
	@GetMapping("/{unitId}")
	public Result<UnitRentalHouseVo> getRentalHouse(@PathVariable Long unitId) throws Exception {
		return this.getById(unitId);
	}

	@Override
	public IRentalHouseConfigService getConfigService() {
		return this.rentalHouseConfigService;
	}

}
