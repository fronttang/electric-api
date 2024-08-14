package com.rosenzest.electric.miniapp.hamlet.controller;

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
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.ProjectAreaDto;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.enums.AreaUserType;
import com.rosenzest.electric.miniapp.dto.MiniAppAreaUserOwnerUnitQuery;
import com.rosenzest.electric.miniapp.dto.MiniAppOwnerUnitQuery;
import com.rosenzest.electric.miniapp.vo.AreaUserIndexVo;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitDangerStatisticsVo;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectAreaService;
import com.rosenzest.electric.service.ISysUserService;
import com.rosenzest.electric.vo.ProjectAreaVo;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.controller.ServerBaseController;
import com.rosenzest.server.base.enums.UserType;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "街区账号")
@TokenRule(terminal = TerminalType.MINIAPP, userType = UserType.AREA_USER)
@RestController
@RequestMapping("/miniapp/area/")
public class AreaUserController extends ServerBaseController {

	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IProjectAreaService projectAreaService;

	@Autowired
	private IOwnerUnitBuildingService ownerUnitBuildingService;

	@ApiOperation(tags = "街区账号", value = "首页")
	@GetMapping("/index")
	public Result<AreaUserIndexVo> index() {

		LoginUser loginUser = getLoginUser();

		AreaUserInfoVo userInfo = sysUserService.getAreaUserInfo(loginUser.getUserId(), loginUser.getProjectId());

		if (userInfo == null) {
			return Result.ERROR();
		}

		AreaUserType areaUserType = getAreaUserType(userInfo);
		if (areaUserType == null) {
			return Result.ERROR();
		}

		AreaUserIndexVo result = ownerUnitService.getAreaUserIndex(userInfo);
		return Result.SUCCESS(result);
	}

	@ApiOperation(tags = "街区账号", value = "业主单元列表")
	@PostMapping("/owner/unit")
	public ListResult<OwnerUnitDangerStatisticsVo> unitList(@RequestBody @Valid MiniAppOwnerUnitQuery query) {
		LoginUser loginUser = getLoginUser();

		query.setProjectId(loginUser.getProjectId());
		query.setUserId(loginUser.getUserId());

		AreaUserInfoVo userInfo = sysUserService.getAreaUserInfo(loginUser.getUserId(), loginUser.getProjectId());

		if (userInfo == null) {
			return ListResult.SUCCESS(0L, null);
		}

		MiniAppAreaUserOwnerUnitQuery unitQuery = new MiniAppAreaUserOwnerUnitQuery();
		BeanUtils.copyProperties(query, unitQuery);

		unitQuery.setUserDistrict(userInfo.getDistrict());
		unitQuery.setUserStreet(userInfo.getStreet());
		unitQuery.setUserCommunity(userInfo.getCommunity());
		unitQuery.setUserHamlet(userInfo.getHamlet());

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		List<OwnerUnitDangerStatisticsVo> result = ownerUnitService.getOwnerUnitDangerStatisticsByAreaUser(unitQuery,
				pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), result);
	}

	@ApiOperation(tags = "街区账号", value = "业主单元楼栋列表")
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

	@ApiOperation(tags = "街区账号", value = "区域字典")
	@GetMapping("/dict")
	public Result<List<ProjectAreaVo>> areaDict() {
		LoginUser loginUser = getLoginUser();

		AreaUserInfoVo userInfo = sysUserService.getAreaUserInfo(loginUser.getUserId(), loginUser.getProjectId());

		if (userInfo == null) {
			return Result.ERROR();
		}

		List<ProjectAreaDto> projectAreas = projectAreaService.getProjectAreasByAreaUser(userInfo);

		Map<String, ProjectAreaVo> districtMap = new HashMap<String, ProjectAreaVo>();
		Map<String, ProjectAreaVo> streetMap = new HashMap<String, ProjectAreaVo>();
		Map<String, ProjectAreaVo> communityMap = new HashMap<String, ProjectAreaVo>();
		Map<String, ProjectAreaVo> hamletMap = new HashMap<String, ProjectAreaVo>();
		if (CollUtil.isNotEmpty(projectAreas)) {
			projectAreas.forEach((area) -> {

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
								communityMap.put(area.getCommunity(), streetVo);
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

	private AreaUserType getAreaUserType(AreaUserInfoVo userInfo) {

		boolean district = StrUtil.isNotBlank(userInfo.getDistrict());
		boolean street = StrUtil.isNotBlank(userInfo.getStreet());
		boolean community = StrUtil.isNotBlank(userInfo.getCommunity());
		boolean hamlet = StrUtil.isNotBlank(userInfo.getHamlet());

		if (!district) {
			// 区为空直接返回空
			return null;
		} else if (district && !street && !community && !hamlet) {
			return AreaUserType.DISTRICT;
		} else if (district && street && !community && !hamlet) {
			return AreaUserType.STREET;
		} else if (district && street && community && !hamlet) {
			return AreaUserType.COMMUNITY;
		} else if (district && street && community && hamlet) {
			return AreaUserType.HAMLET;
		}

		return null;
	}

	@ApiOperation(tags = "街区账号", value = "区域字典")
	@GetMapping("/statistics")
	public Result<List<OwnerUnitDangerStatisticsVo>> statistics() {

		return Result.SUCCESS();
	}
}
