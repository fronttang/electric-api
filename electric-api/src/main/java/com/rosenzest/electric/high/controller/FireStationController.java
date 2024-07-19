package com.rosenzest.electric.high.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.high.dto.FireStationDto;
import com.rosenzest.electric.high.dto.FireStationQuery;
import com.rosenzest.electric.high.entity.FireStation;
import com.rosenzest.electric.high.service.IFireStationService;
import com.rosenzest.electric.high.vo.FireStationVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 消防站 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Api(tags = "高风险-公共检查-消防队伍建设")
@RestController
@RequestMapping("/high/fire/station")
public class FireStationController extends ServerBaseController {

	@Autowired
	private IFireStationService fireStationService;

	@ApiOperation(tags = "高风险-公共检查-消防队伍建设", value = "消防站列表")
	@PostMapping("/list")
	public Result<List<FireStationVo>> list(@RequestBody @Valid FireStationQuery query) {

		List<FireStation> fireStations = fireStationService.fireStationList(query);
		List<FireStationVo> results = BeanUtils.copyList(fireStations, FireStationVo.class);
		return Result.SUCCESS(results);

	}

	@ApiOperation(tags = "高风险-公共检查-消防队伍建设", value = "新增/修改消防站")
	@PostMapping("/")
	public Result<FireStationVo> save(@RequestBody @Valid FireStationDto data) {

		FireStation fireStation = new FireStation();
		BeanUtils.copyProperties(data, fireStation);

		fireStationService.saveOrUpdate(fireStation);

		FireStationVo result = new FireStationVo();
		BeanUtils.copyProperties(fireStation, result);

		return Result.SUCCESS(result);
	}

	@ApiOperation(tags = "高风险-公共检查-消防队伍建设", value = "删除消防站")
	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable Long id) {

		if (fireStationService.removeById(id)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}
}
