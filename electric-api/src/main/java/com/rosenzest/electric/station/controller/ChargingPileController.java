package com.rosenzest.electric.station.controller;

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

import com.rosenzest.base.ListResult;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.base.util.IdUtils;
import com.rosenzest.electric.controller.ElectricBaseController;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.station.dto.ChargingPileDto;
import com.rosenzest.electric.station.dto.ChargingPileQuery;
import com.rosenzest.electric.station.entity.ChargingPile;
import com.rosenzest.electric.station.service.IChargingPileService;
import com.rosenzest.electric.station.vo.ChargingPileVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-07-18
 */
@Api(tags = "充电桩")
@RestController
@RequestMapping("/unit/charging/pile")
public class ChargingPileController extends ElectricBaseController {

	@Autowired
	private IChargingPileService chargingPileService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	// @Autowired
	// private IProjectWorkerService projectWorkerService;

	@Autowired
	private IOwnerUnitDangerService unitDangerService;

	@ApiOperation(tags = "充电桩", value = "添加/修改充电桩")
	@PostMapping("")
	public Result<?> saveChangePile(@RequestBody @Valid ChargingPileDto data) {

		LoginUser loginUser = getLoginUser();

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(data.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!ProjectType.CHARGING_STATION.code().equalsIgnoreCase(ownerUnit.getType())) {
			return Result.ERROR(400, "非充电站类型项目不能添加充电桩");
		}

		if (data.getId() != null) {

			ChargingPile chargingPile = chargingPileService.getById(data.getId());

			if (chargingPile == null) {
				return Result.ERROR(400, "无操作权限");
			}

			if (!chargingPile.getRounds().equals(ownerUnit.getRounds())) {
				// 轮次不一样不能修改
				return Result.ERROR(400, "非当前轮次数据不能修改");
			}

			checkPermission(chargingPile, ownerUnit);

//			if (!String.valueOf(loginUser.getUserId()).equalsIgnoreCase(chargingPile.getCreateBy())) {
//
//				if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
//					return Result.ERROR(400, "无操作权限");
//				}
//			}
		}

		ChargingPile chargingPile = new ChargingPile();

		BeanUtils.copyProperties(data, chargingPile);

		chargingPile.setRounds(ownerUnit.getRounds());

		if (chargingPile.getId() == null) {
			chargingPile.setCreateBy(String.valueOf(loginUser.getUserId()));
		}

		if (data.getId() == null) {
			Long id = IdUtils.getSnowflakeNextId();
			chargingPile.setId(id);
			chargingPile.setOriginalId(id);
		}

		if (chargingPileService.saveOrUpdate(chargingPile)) {
			ChargingPileVo vo = new ChargingPileVo();
			BeanUtils.copyProperties(chargingPile, vo);
			return Result.SUCCESS(vo);
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "充电桩", value = "删除充电桩")
	@DeleteMapping("/{pileId}")
	public Result<?> deleteChargingPile(@PathVariable Long pileId) {

		// LoginUser loginUser = getLoginUser();

		ChargingPile chargingPile = chargingPileService.getById(pileId);

		if (chargingPile == null) {
			return Result.ERROR(400, "无操作权限");
		}

		// 检查工作人员权限
		OwnerUnit ownerUnit = ownerUnitService.getById(chargingPile.getUnitId());
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!chargingPile.getRounds().equals(ownerUnit.getRounds())) {
			// 轮次不一样不能删除
			return Result.ERROR(400, "非当前轮次数据不能删除");
		}

		checkPermission(chargingPile, ownerUnit);

//		if (!String.valueOf(loginUser.getUserId()).equalsIgnoreCase(chargingPile.getCreateBy())) {
//
//			if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
//				return Result.ERROR(400, "无操作权限");
//			}
//		}

		// 检查是否有隐患数据
		Integer dangers = unitDangerService.countByChargingPileId(pileId);
		if (dangers > 0) {
			return Result.ERROR(400, "请先删除隐患数据！");
		}

		if (chargingPileService.removeById(pileId)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "充电桩", value = "充电桩列表")
	@GetMapping("/list")
	public ListResult<ChargingPileVo> changePileList(@Valid ChargingPileQuery query) {

		List<ChargingPileVo> pileList = new ArrayList<ChargingPileVo>();

		OwnerUnit ownerUnit = ownerUnitService.getById(query.getUnitId());
		if (ownerUnit == null) {
			return ListResult.SUCCESS(0L, pileList);
		}

		if (query.getRounds() == null) {
			query.setRounds(ownerUnit.getRounds());
		}

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		pileList = chargingPileService.queryList(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), pileList);

	}
}
