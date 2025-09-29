package com.rosenzest.electric.miniapp.gridman.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.ListResult;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.base.Result;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.enums.TerminalType;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.miniapp.dto.MiniAppOwnerUnitQuery;
import com.rosenzest.electric.miniapp.owner.executor.OwnerUnitBelongPermissionExecutor;
import com.rosenzest.electric.miniapp.vo.GridmanDangerStatisticsVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitDangerStatisticsVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitOverviewVo;
import com.rosenzest.electric.miniapp.vo.StatisticsHighVo;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.vo.ProjectAreaVo;
import com.rosenzest.server.base.annotation.Permission;
import com.rosenzest.server.base.annotation.PermissionParam;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.controller.ServerBaseController;
import com.rosenzest.server.base.enums.RequestParamType;
import com.rosenzest.server.base.enums.UserType;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "网格员端")
@TokenRule(terminal = TerminalType.MINIAPP, userType = UserType.GRADMAN)
@RestController
@RequestMapping("/miniapp/gridman/")
public class GridmanController extends ServerBaseController {

	@Autowired
	private IOwnerUnitDangerService dangerService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitBuildingService ownerUnitBuildingService;

	@Autowired
	private IProjectService projectService;

	@ApiOperation(tags = "网格员端", value = "首页")
	@GetMapping("/index")
	public Result<GridmanDangerStatisticsVo> statistics() {

		LoginUser loginUser = getLoginUser();

		GridmanDangerStatisticsVo result = dangerService.statisticsByGridman(loginUser);

		return Result.SUCCESS(result);
	}

	@ApiOperation(tags = "网格员端", value = "业主单元列表")
	@PostMapping("/owner/unit")
	public ListResult<OwnerUnitDangerStatisticsVo> unitList(@RequestBody @Valid MiniAppOwnerUnitQuery query) {
		LoginUser loginUser = getLoginUser();

		query.setProjectId(loginUser.getProjectId());
		query.setUserId(loginUser.getUserId());

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		List<OwnerUnitDangerStatisticsVo> result = ownerUnitService.getOwnerUnitDangerStatisticsByGridman(query,
				pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), result);
	}

	@Permission(type = OwnerUnitBelongPermissionExecutor.class, param = {
			@PermissionParam(name = "unitId", type = RequestParamType.PATH_VARIABLE) })
	@ApiOperation(tags = "网格员端", value = "业主单元楼栋列表")
	@GetMapping("/owner/unit/building/{unitId}")
	public Result<List<OwnerUnitDangerStatisticsVo>> ownerUnitBuildingList(@PathVariable Long unitId,
			@RequestParam(required = false) String keyword) {

		// LoginUser loginUser = getLoginUser();

		// if (!ownerUnitService.checkOwnerUnitManager(unitId, loginUser.getUserId())) {
		// throw new BusinessException(ResultEnum.FORBIDDEN);
		// }

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit == null) {
			throw new BusinessException(ResultEnum.FORBIDDEN);
		}

		List<OwnerUnitDangerStatisticsVo> result = ownerUnitBuildingService.getOwnerUnitBuildingDangerStatistics(unitId,
				keyword);

		return Result.SUCCESS(result);
	}

	@ApiOperation(tags = "网格员端", value = "区域字典")
	@GetMapping("/area")
	public Result<List<ProjectAreaVo>> areaDict() {
		LoginUser loginUser = getLoginUser();

		List<OwnerUnitOverviewVo> ownerUnits = ownerUnitService.getOwnerUnitListByGridman(loginUser.getUserId(),
				loginUser.getProjectId());

		Map<String, ProjectAreaVo> districtMap = new HashMap<String, ProjectAreaVo>();
		Map<String, ProjectAreaVo> streetMap = new HashMap<String, ProjectAreaVo>();
		Map<String, ProjectAreaVo> communityMap = new HashMap<String, ProjectAreaVo>();
		Map<String, ProjectAreaVo> hamletMap = new HashMap<String, ProjectAreaVo>();
		if (CollUtil.isNotEmpty(ownerUnits)) {
			ownerUnits.forEach((area) -> {

				if (StrUtil.isNotBlank(area.getDistrict())) {
					ProjectAreaVo districtVo = districtMap.get(area.getDistrict());
					if (districtVo == null) {
						districtVo = new ProjectAreaVo();
						districtVo.setCode(area.getDistrict());
						districtVo.setName(area.getDistrictName());
						districtVo.setType("district");
						districtMap.put(area.getDistrict(), districtVo);
					}

					if (StrUtil.isNotBlank(area.getStreet())) {
						ProjectAreaVo streetVo = streetMap.get(area.getStreet());
						if (streetVo == null) {
							streetVo = new ProjectAreaVo();
							streetVo.setCode(area.getStreet());
							streetVo.setName(area.getStreetName());
							streetVo.setType("street");
							streetMap.put(area.getStreet(), streetVo);
							districtVo.getSub().add(streetVo);
						}
						if (StrUtil.isNotBlank(area.getCommunity())) {
							ProjectAreaVo communityVo = communityMap.get(area.getCommunity());
							if (communityVo == null) {
								communityVo = new ProjectAreaVo();
								communityVo.setCode(area.getCommunity());
								communityVo.setName(area.getCommunityName());
								communityVo.setType("community");
								communityMap.put(area.getCommunity(), communityVo);
								streetVo.getSub().add(communityVo);
							}
							if (StrUtil.isNotBlank(area.getHamlet())) {
								ProjectAreaVo hamletVo = hamletMap.get(area.getHamlet());
								if (hamletVo == null) {
									hamletVo = new ProjectAreaVo();
									hamletVo.setCode(area.getHamlet());
									hamletVo.setName(area.getHamletName());
									hamletVo.setType("hamlet");
									hamletMap.put(area.getHamlet(), hamletVo);
									communityVo.getSub().add(hamletVo);
								}
							}
						}
					}
				}

			});
		}

		List<ProjectAreaVo> result = new ArrayList<ProjectAreaVo>(districtMap.values());
		return Result.SUCCESS(result);
	}

	@ApiOperation(tags = "网格员端", value = "场所统计")
	@GetMapping("/statistics/high")
	public Result<StatisticsHighVo> statisticsHigh(@RequestParam(required = false) String type) {

		LoginUser loginUser = getLoginUser();

		Project project = projectService.getById(loginUser.getProjectId());

		if (!ProjectType.HIGH_RISK.code().equalsIgnoreCase(project.getType())) {
			return Result.SUCCESS();
		}

		StatisticsHighVo result = ownerUnitService.statisticsHighByGridman(loginUser, type);

		return Result.SUCCESS(result);
	}
}
