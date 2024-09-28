package com.rosenzest.electric.storage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.base.ListResult;
import com.rosenzest.base.Result;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.controller.ElectricBaseController;
import com.rosenzest.electric.storage.dto.PhotovoltaicDto;
import com.rosenzest.electric.storage.dto.PhotovoltaicQuery;
import com.rosenzest.electric.storage.entity.Photovoltaic;
import com.rosenzest.electric.storage.entity.PhotovoltaicConfig;
import com.rosenzest.electric.storage.service.IPhotovoltaicConfigService;
import com.rosenzest.electric.storage.service.IPhotovoltaicService;
import com.rosenzest.electric.storage.service.PhotovoltaicReportService;
import com.rosenzest.electric.storage.vo.PhotovoltaicConfigVo;
import com.rosenzest.electric.storage.vo.PhotovoltaicVo;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.enums.UserType;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 光伏 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
@TokenRule(project = false, userType = UserType.STORAGE)
@Api(tags = "光伏")
@RestController
@RequestMapping("/electric/photovoltaic")
public class PhotovoltaicController extends ElectricBaseController {

	@Autowired
	private IPhotovoltaicService photovoltaicService;

	@Autowired
	private IPhotovoltaicConfigService configService;

	@Autowired
	private PhotovoltaicReportService photovoltaicReportService;

	@ApiOperation(tags = "光伏", value = "项目列表")
	@PostMapping("/list")
	public ListResult<PhotovoltaicVo> list(@RequestBody @Valid PhotovoltaicQuery query) {

		Page<Photovoltaic> dangerPage = photovoltaicService.queryList(query);

		List<Photovoltaic> records = dangerPage.getRecords();
		if (CollUtil.isNotEmpty(records)) {

			List<PhotovoltaicVo> datas = BeanUtils.copyList(records, PhotovoltaicVo.class);

			return ListResult.SUCCESS(dangerPage.getTotal(), datas);
		}

		return ListResult.SUCCESS(0L, new ArrayList<PhotovoltaicVo>());
	}

	@ApiOperation(tags = "光伏", value = "添加/修改项目")
	@PostMapping("")
	public Result<?> save(@RequestBody @Valid PhotovoltaicDto data) {

		Photovoltaic entity = new Photovoltaic();
		BeanUtils.copyProperties(data, entity);

		entity.setCreateBy(String.valueOf(getUserId()));

		if (photovoltaicService.saveOrUpdate(entity)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "光伏", value = "删除项目")
	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable Long id) {

		Photovoltaic photovoltaic = photovoltaicService.getById(id);

		if (!photovoltaic.getCreateBy().equalsIgnoreCase(String.valueOf(getUserId()))) {
			throw new BusinessException(400, "没有权限");
		}

		if (photovoltaicService.removeById(id)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "光伏", value = "光伏参数")
	@GetMapping("/config")
	public Result<PhotovoltaicConfigVo> config() {

		PhotovoltaicConfig config = configService.getById(1L);
		if (config == null) {
			config = new PhotovoltaicConfig();
		}
		PhotovoltaicConfigVo result = new PhotovoltaicConfigVo();
		BeanUtils.copyProperties(config, result);

		return Result.SUCCESS(result);
	}

	@ApiOperation(tags = "光伏", value = "项目报告(word)")
	@GetMapping("/report/word/{id}")
	public Result<String> report(@PathVariable Long id) {
		String url = photovoltaicReportService.report(id);
		if (StrUtil.isNotBlank(url)) {
			return Result.SUCCESS(url);
		}
		return Result.ERROR();
	}

}
