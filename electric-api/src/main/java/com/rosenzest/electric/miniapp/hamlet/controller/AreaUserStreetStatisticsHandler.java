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
import cn.hutool.core.util.StrUtil;

/**
 * 街道账号
 */
@AreaUserStatisticsHandler(AreaUserType.STREET)
public class AreaUserStreetStatisticsHandler implements IAreaUserStatisticsHandler {

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Override
	public List<OwnerUnitDangerStatisticsVo> statistics(AreaUserInfoVo userInfo) {

		List<AreaUserDangerVo> userDangers = ownerUnitDangerService.getOwnerUnitDangerByAreaUser(userInfo);

		List<OwnerUnitDangerStatisticsVo> result = new ArrayList<OwnerUnitDangerStatisticsVo>();

		if (CollUtil.isNotEmpty(userDangers)) {

			Map<String, List<AreaUserDangerVo>> groupMap = userDangers.stream()
					.collect(Collectors.groupingBy(
							(d) -> StrUtil.format("{}/{}/{}", d.getDistrict(), d.getStreet(), d.getCommunity()),
							Collectors.toList()));

			List<SysDictData> hazardLevel = ownerUnitService.getHazardLevel(userInfo.getProjectType());

			groupMap.forEach((key, dangers) -> {

				AreaUserDangerVo danger = dangers.get(0);

				OwnerUnitOverviewVo ownerUnitOverviewVo = new OwnerUnitOverviewVo();
				ownerUnitOverviewVo.setDistrict(danger.getDistrict());
				ownerUnitOverviewVo.setDistrictName(danger.getDistrictName());
				ownerUnitOverviewVo.setStreet(danger.getStreet());
				ownerUnitOverviewVo.setStreetName(danger.getStreetName());
				ownerUnitOverviewVo.setCommunity(danger.getCommunity());
				ownerUnitOverviewVo.setCommunityName(danger.getCommunityName());

				OwnerUnitDangerStatisticsVo vo = ownerUnitService.buildOwnerUnitDangerStatisticsVo(ownerUnitOverviewVo,
						null, dangers, hazardLevel);

				vo.setName(StrUtil.format("{} {} {}", danger.getDistrictName(), danger.getStreetName(),
						danger.getCommunityName()));

				result.add(vo);

			});
		}

		return result;
	}

}
