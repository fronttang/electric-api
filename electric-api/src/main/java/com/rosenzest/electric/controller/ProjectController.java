package com.rosenzest.electric.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.entity.ProjectWorker;
import com.rosenzest.electric.entity.ProjectWorkerArea;
import com.rosenzest.electric.enums.ProjectWorkerAreaRoleType;
import com.rosenzest.electric.enums.ProjectWorkerType;
import com.rosenzest.electric.enums.UserType;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.IProjectWorkerAreaService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.electric.vo.ProjectAreaVo;
import com.rosenzest.electric.vo.ProjectVo;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Api(tags = "项目")
@RestController
@RequestMapping("/project")
public class ProjectController extends ServerBaseController {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IProjectWorkerService projectWorkerService;

	@Autowired
	private IProjectWorkerAreaService projectWorkerAreaService;

	@ApiOperation(tags = "项目", value = "用户项目区域")
	@GetMapping("/area/list/{projectId}")
	public Result<List<ProjectAreaVo>> list(@PathVariable Long projectId) {

		LoginUser loginUser = getLoginUser();

		Long userId = loginUser.getUserId();

		//
		ProjectWorker projectWorker = projectWorkerService.getProjectWorker(projectId, userId,
				ProjectWorkerType.INSPECTOR.code());

		if (projectWorker == null) {
			return Result.SUCCESS();
		}

		List<ProjectWorkerArea> projectWorkerAreas = projectWorkerAreaService
				.getProjectWorkerArea(projectWorker.getId(), ProjectWorkerAreaRoleType.VIEW);

		Map<String, ProjectAreaVo> districtMap = new HashMap<String, ProjectAreaVo>();
		Map<String, ProjectAreaVo> streetMap = new HashMap<String, ProjectAreaVo>();
		Map<String, ProjectAreaVo> communityMap = new HashMap<String, ProjectAreaVo>();
		Map<String, ProjectAreaVo> hamletMap = new HashMap<String, ProjectAreaVo>();
		if (CollUtil.isNotEmpty(projectWorkerAreas)) {
			projectWorkerAreas.forEach((area) -> {

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

	@TokenRule(project = false)
	@ApiOperation(tags = "项目", value = "用户项目列表")
	@GetMapping("/list")
	public Result<List<ProjectVo>> projectList() {

		LoginUser loginUser = getLoginUser();
		Long detectId = loginUser.getDetectId();

		if (detectId == null) {
			return Result.SUCCESS();
		}

		if (UserType.WORKER.code().equalsIgnoreCase(loginUser.getType())) {

			List<Project> projects = projectService.getProjectByWorkerId(loginUser.getUserId());

			// List<Project> projects = projectService.getProjectByDetectId(detectId);

			List<ProjectVo> projectVoList = BeanUtils.copyList(projects, ProjectVo.class);

			return Result.SUCCESS(projectVoList);
		} else {

			return Result.SUCCESS();
		}
	}
}
