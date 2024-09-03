package com.rosenzest.electric.miniapp.hamlet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.rosenzest.electric.entity.SysDictData;
import com.rosenzest.electric.enums.AreaUserType;
import com.rosenzest.electric.miniapp.vo.AreaUserDangerVo;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitDangerStatisticsVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitOverviewVo;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;

import cn.hutool.core.collection.CollUtil;

/**
 * 村账号
 */
@AreaUserStatisticsHandler(AreaUserType.HAMLET)
public class AreaUserHamletStatisticsHandler implements IAreaUserStatisticsHandler {

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Override
	public List<OwnerUnitDangerStatisticsVo> statistics(AreaUserInfoVo userInfo) {

		List<AreaUserDangerVo> userDangers = ownerUnitDangerService.getOwnerUnitDangerByAreaUser(userInfo);

		List<OwnerUnitDangerStatisticsVo> result = new ArrayList<OwnerUnitDangerStatisticsVo>();

		if (CollUtil.isNotEmpty(userDangers)) {

			Map<Long, List<AreaUserDangerVo>> groupMap = userDangers.stream()
					.collect(Collectors.groupingBy((d) -> d.getUnitId(), Collectors.toList()));

			List<SysDictData> hazardLevel = ownerUnitService.getHazardLevel(userInfo.getProjectType());

			groupMap.forEach((unitId, dangers) -> {

				OwnerUnitOverviewVo ownerUnitOverviewVo = ownerUnitService.getOwnerUnitInfoById(unitId);

				if (ownerUnitOverviewVo != null) {
					OwnerUnitDangerStatisticsVo vo = ownerUnitService
							.buildOwnerUnitDangerStatisticsVo(ownerUnitOverviewVo, null, dangers, hazardLevel);

					vo.setName(ownerUnitOverviewVo.getName());

					result.add(vo);

				}
			});
		}

		return result;
	}

}