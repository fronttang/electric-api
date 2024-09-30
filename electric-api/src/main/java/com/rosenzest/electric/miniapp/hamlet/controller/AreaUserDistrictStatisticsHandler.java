package com.rosenzest.electric.miniapp.hamlet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.rosenzest.electric.dto.ProjectAreaDto;
import com.rosenzest.electric.entity.SysDictData;
import com.rosenzest.electric.enums.AreaUserType;
import com.rosenzest.electric.miniapp.dto.MiniAppAreaQuery;
import com.rosenzest.electric.miniapp.dto.UnitStatisticsDto;
import com.rosenzest.electric.miniapp.vo.AreaUserDangerVo;
import com.rosenzest.electric.miniapp.vo.AreaUserIndexVo.UnitStatistics;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitDangerStatisticsVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitOverviewVo;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectAreaService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 区账号
 */
@AreaUserStatisticsHandler(AreaUserType.DISTRICT)
public class AreaUserDistrictStatisticsHandler implements IAreaUserStatisticsHandler {

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IProjectAreaService projectAreaService;

	@Override
	public List<OwnerUnitDangerStatisticsVo> statistics(AreaUserInfoVo userInfo, MiniAppAreaQuery areaQuery) {
		// 查询项目 区县下街道
		List<ProjectAreaDto> projectArea = projectAreaService.getProjectArea(areaQuery);
		List<OwnerUnitDangerStatisticsVo> result = new ArrayList<OwnerUnitDangerStatisticsVo>();

		List<SysDictData> hazardLevel = ownerUnitService.getHazardLevel(userInfo.getProjectType());

		if (CollUtil.isNotEmpty(projectArea)) {

			// 按街道分组
			Map<String, List<ProjectAreaDto>> streetMap = projectArea.stream().collect(Collectors.groupingBy((d) -> {
				return StrUtil.format("{}|{}", d.getDistrict(), d.getStreet());
			}, Collectors.toList()));

			streetMap.forEach((street, areas) -> {

				ProjectAreaDto projectAreaDto = areas.get(0);

				// 查询该区域下所有业主单元
				MiniAppAreaQuery query = new MiniAppAreaQuery();
				query.setDistrict(projectAreaDto.getDistrict());
				query.setStreet(projectAreaDto.getStreet());
				query.setProjectId(userInfo.getProjectId());

				List<UnitStatisticsDto> unitStatisticsResult = ownerUnitService.unitStatisticsByArea(query);

				UnitStatistics unitStatistics = new UnitStatistics();

				if (CollUtil.isNotEmpty(unitStatisticsResult)) {

					// 业主单元总数
					unitStatistics.setTotal(unitStatisticsResult.stream().count());

					// 已检测
					long detected = unitStatisticsResult.stream().filter((e) -> Objects.nonNull(e.getReportId()))
							.count();
					unitStatistics.setDetected(detected);

					// 检测中
					long detecting = unitStatisticsResult.stream().filter((e) -> Objects.isNull(e.getReportId()))
							.count();
					unitStatistics.setDetecting(detecting);
				}

				List<AreaUserDangerVo> areaDangers = ownerUnitDangerService.getOwnerUnitDangerByArea(query);

				OwnerUnitOverviewVo ownerUnitOverviewVo = new OwnerUnitOverviewVo();
				ownerUnitOverviewVo.setDistrict(projectAreaDto.getDistrict());
				ownerUnitOverviewVo.setDistrictName(projectAreaDto.getDistrictName());
				ownerUnitOverviewVo.setStreet(projectAreaDto.getStreet());
				ownerUnitOverviewVo.setStreetName(projectAreaDto.getStreetName());

				OwnerUnitDangerStatisticsVo vo = ownerUnitService.buildOwnerUnitDangerStatisticsVo(ownerUnitOverviewVo,
						null, areaDangers, hazardLevel);

				vo.setName(StrUtil.format("{} {}", projectAreaDto.getDistrictName(), projectAreaDto.getStreetName()));

				vo.setUnit(unitStatistics);

				result.add(vo);

			});
		}
		return result;
	}

}
