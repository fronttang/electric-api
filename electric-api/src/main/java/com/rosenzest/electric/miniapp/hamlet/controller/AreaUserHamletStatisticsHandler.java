package com.rosenzest.electric.miniapp.hamlet.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rosenzest.electric.enums.AreaUserType;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.miniapp.dto.MiniAppAreaQuery;
import com.rosenzest.electric.miniapp.dto.UnitStatisticsDto;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitDangerStatisticsVo;
import com.rosenzest.electric.service.IOwnerUnitService;

import cn.hutool.core.collection.CollUtil;

/**
 * 村账号
 */
@AreaUserStatisticsHandler(AreaUserType.HAMLET)
public class AreaUserHamletStatisticsHandler implements IAreaUserStatisticsHandler {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Override
	public List<OwnerUnitDangerStatisticsVo> statistics(AreaUserInfoVo userInfo, MiniAppAreaQuery areaQuery) {

		List<OwnerUnitDangerStatisticsVo> result = new ArrayList<OwnerUnitDangerStatisticsVo>();

		if (ProjectType.URBAN_VILLAGE.code().equalsIgnoreCase(userInfo.getProjectType())) {
			List<UnitStatisticsDto> unitStatisticsResult = ownerUnitService.unitStatisticsByArea(areaQuery);

			if (CollUtil.isNotEmpty(unitStatisticsResult)) {

				unitStatisticsResult.forEach((unit) -> {

					OwnerUnitDangerStatisticsVo vo = ownerUnitService.getOwnerUnitDangerStatistics(unit.getId(), null);

					vo.setName(unit.getName());
					result.add(vo);
				});
			}
		}

		return result;
	}

}
