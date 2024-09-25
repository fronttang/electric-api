package com.rosenzest.electric.storage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.rosenzest.electric.storage.dto.EnergyStorageDto;
import com.rosenzest.electric.storage.dto.EnergyStorageMonthDto;
import com.rosenzest.electric.storage.dto.EnergyStorageQuery;
import com.rosenzest.electric.storage.entity.EnergyStorage;
import com.rosenzest.electric.storage.entity.EnergyStorageMonth;
import com.rosenzest.electric.storage.service.IEnergyStorageMonthService;
import com.rosenzest.electric.storage.service.IEnergyStorageService;
import com.rosenzest.electric.storage.vo.EnergyStorageMonthVo;
import com.rosenzest.electric.storage.vo.EnergyStorageVo;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.enums.UserType;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 储能 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
@TokenRule(project = false, userType = UserType.STORAGE)
@Api(tags = "储能")
@RestController
@RequestMapping("/electric/storage")
public class EnergyStorageController extends ElectricBaseController {

	@Autowired
	private IEnergyStorageService energyStorageService;

	@Autowired
	private IEnergyStorageMonthService energyStorageMonthService;

	@ApiOperation(tags = "储能", value = "项目列表")
	@PostMapping("/list")
	public ListResult<EnergyStorageVo> list(@RequestBody @Valid EnergyStorageQuery query) {

		Page<EnergyStorage> queryList = energyStorageService.queryList(query);

		List<EnergyStorage> records = queryList.getRecords();
		if (CollUtil.isNotEmpty(records)) {
			List<EnergyStorageVo> datas = BeanUtils.copyList(records, EnergyStorageVo.class);
			return ListResult.SUCCESS(queryList.getTotal(), datas);
		}

		return ListResult.SUCCESS(0L, new ArrayList<EnergyStorageVo>());
	}

	@ApiOperation(tags = "储能", value = "添加/修改项目")
	@PostMapping("")
	public Result<?> save(@RequestBody @Valid EnergyStorageDto data) {

		EnergyStorage entity = new EnergyStorage();
		BeanUtils.copyProperties(data, entity);

		entity.setCreateBy(String.valueOf(getUserId()));

		if (energyStorageService.saveOrUpdate(entity)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "储能", value = "删除项目")
	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable Long id) {

		EnergyStorage energyStorage = energyStorageService.getById(id);

		if (!energyStorage.getCreateBy().equalsIgnoreCase(String.valueOf(getUserId()))) {
			throw new BusinessException(400, "没有权限");
		}

		if (energyStorageService.remove(id)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "储能", value = "月数据列表")
	@PostMapping("/month/list/{storageId}")
	public ListResult<EnergyStorageMonthVo> listMonth(@PathVariable Long storageId,
			@RequestBody @Valid EnergyStorageQuery query) {

		Page<EnergyStorageMonth> queryList = energyStorageService.queryMonthList(storageId, query);

		List<EnergyStorageMonth> records = queryList.getRecords();
		if (CollUtil.isNotEmpty(records)) {
			List<EnergyStorageMonthVo> datas = BeanUtils.copyList(records, EnergyStorageMonthVo.class);
			return ListResult.SUCCESS(queryList.getTotal(), datas);
		}

		return ListResult.SUCCESS(0L, new ArrayList<EnergyStorageMonthVo>());
	}

	@ApiOperation(tags = "储能", value = "添加/修改月数据")
	@PostMapping("/month/{storageId}")
	public Result<?> saveMonth(@PathVariable Long storageId, @RequestBody @Valid EnergyStorageMonthDto data) {

		EnergyStorageMonth entity = new EnergyStorageMonth();
		BeanUtils.copyProperties(data, entity);
		entity.setStorageId(storageId);

		if (energyStorageMonthService.saveOrUpdate(entity)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "储能", value = "删除月数据")
	@DeleteMapping("/month/{storageId}/{id}")
	public Result<?> deleteMonth(@PathVariable Long storageId, @PathVariable Long id) {

		EnergyStorageMonth month = energyStorageMonthService.getById(id);

		if (!month.getStorageId().equals(storageId)) {
			return Result.ERROR();
		}

		if (energyStorageMonthService.removeById(id)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}
}
