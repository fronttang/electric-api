package com.rosenzest.electric.high.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.high.dto.PublicInspectionDto;
import com.rosenzest.electric.high.entity.PublicInspection;
import com.rosenzest.electric.high.service.IPublicInspectionService;
import com.rosenzest.electric.high.vo.PublicInspectionVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 公共查阅 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Api(tags = "高风险-公共检查")
@RestController
@RequestMapping("/high/public/inspection")
public class PublicInspectionController extends ServerBaseController {

	@Autowired
	private IPublicInspectionService publicInspectionService;

	@ApiOperation(tags = "高风险-公共检查", value = "查询信息")
	@PostMapping("/info")
	public Result<PublicInspectionVo> info(@RequestBody @Valid PublicInspectionDto data) {

		PublicInspection publicInspection = publicInspectionService.findPublicInspection(data);

		if (publicInspection != null) {

			PublicInspectionVo result = new PublicInspectionVo();
			BeanUtils.copyProperties(publicInspection, result);

			return Result.SUCCESS(result);
		}
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "高风险-公共检查", value = "保存信息")
	@PostMapping("")
	public Result<PublicInspectionVo> save(@RequestBody @Valid PublicInspectionDto data) {

		PublicInspection publicInspection = publicInspectionService.savePublicInspection(data);

		if (publicInspection != null) {

			PublicInspectionVo result = new PublicInspectionVo();
			BeanUtils.copyProperties(publicInspection, result);

			return Result.SUCCESS(result);
		}
		return Result.SUCCESS();
	}
}
