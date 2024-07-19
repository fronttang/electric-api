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
import com.rosenzest.electric.high.config.PublicPlacesConfig;
import com.rosenzest.electric.high.dto.UnitPublicPlacesDto;
import com.rosenzest.electric.high.service.IPublicPlacesConfigService;
import com.rosenzest.electric.high.vo.UnitPublicPlacesVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 公共场所 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Api(tags = "业主单元(高风险-公共场所)")
@RestController
@RequestMapping("/unit/high/public")
public class PublicPlacesController
		extends BaseHighController<PublicPlacesConfig, UnitPublicPlacesDto, UnitPublicPlacesVo> {

	@Autowired
	private IPublicPlacesConfigService publicPlaceConfigService;

	@ApiOperation(tags = "业主单元(高风险-公共场所)", value = "添加/修改公共场所")
	@PostMapping("")
	public Result<UnitPublicPlacesVo> savePublicPlaces(@RequestBody @Valid UnitPublicPlacesDto data) throws Exception {
		return super.save(data);
	}

	@ApiOperation(tags = "业主单元(高风险-公共场所)", value = "查询公共场所信息")
	@GetMapping("/{unitId}")
	public Result<UnitPublicPlacesVo> getPublicPlaces(@PathVariable Long unitId) throws Exception {
		return super.getById(unitId);
	}

	@Override
	public IPublicPlacesConfigService getConfigService() {
		return this.publicPlaceConfigService;
	}

}
