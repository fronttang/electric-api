package com.rosenzest.electric.station.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.entity.IntuitiveDetectDanger;
import com.rosenzest.electric.entity.IntuitiveDetectData;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.service.IIntuitiveDetectDangerService;
import com.rosenzest.electric.service.IIntuitiveDetectDataService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.vo.DetectDataDangerVo;
import com.rosenzest.electric.vo.DetectDataVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "检测表(充电站)")
@RestController
@RequestMapping("/detect/charging/station")
public class ChargingStationDetectController extends ServerBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IIntuitiveDetectDataService detectDataService;

	@Autowired
	private IIntuitiveDetectDangerService detectDangerService;

	@Autowired
	private IOwnerUnitDangerService unitDangerService;

	@ApiOperation(tags = "检测表(充电站)", value = "检测表")
	@GetMapping("/form")
	public Result<List<DetectDataVo>> formList(@RequestParam(value = "unitId") Long unitId,
			@RequestParam(value = "pileId", required = false) Long pileId,
			@RequestParam(value = "module", required = false) String module) {

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit == null) {
			return Result.SUCCESS();
		}

		Project project = projectService.getById(ownerUnit.getProjectId());

		if (project == null) {
			return Result.SUCCESS();
		}
		// 项目检测表模板ID
		Long templateId = project.getTemplateId();

		List<IntuitiveDetectData> detectDatas = detectDataService.getByTemplateIdAndDetectModule(templateId, module);
		if (CollUtil.isNotEmpty(detectDatas)) {

			List<DetectDataVo> formDatas = BeanUtils.copyList(detectDatas, (d) -> {
				DetectDataVo vo = new DetectDataVo();
				BeanUtils.copyProperties(d, vo);
				try {
					vo.setDetectTitle(Long.valueOf(d.getDetectModule()));
				} catch (Exception e) {
				}
				return vo;
			});

			formDatas.forEach((data) -> {

				Integer danger = unitDangerService.countByUnitIdAndDataIdAndPileId(unitId, data.getId(), pileId);
				data.setDanger(danger);

				// 查询 data danger
				List<IntuitiveDetectDanger> detectDanger = detectDangerService.getByDataId(data.getId());
				if (CollUtil.isNotEmpty(detectDanger)) {
					List<DetectDataDangerVo> formDatadangers = BeanUtils.copyList(detectDanger,
							DetectDataDangerVo.class);
					data.setDangers(formDatadangers);
				}
			});

			return Result.SUCCESS(formDatas);
		}
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表(充电站)", value = "检测表(按项目查)")
	@GetMapping("/form/project")
	public Result<List<DetectDataVo>> formListByProject(@RequestParam(value = "projectId") Long projectId) {

		Project project = projectService.getById(projectId);

		if (project == null) {
			return Result.SUCCESS();
		}

		boolean isChargingStation = ProjectType.CHARGING_STATION.code().equalsIgnoreCase(project.getType());

		if (!isChargingStation) {
			return Result.SUCCESS();
		}

		// 项目检测表模板ID
		Long templateId = project.getTemplateId();

		List<IntuitiveDetectData> detectDatas = detectDataService.getByTemplateIdAndDetectModule(templateId, null);
		if (CollUtil.isNotEmpty(detectDatas)) {

			// List<DetectDataVo> formDatas = BeanUtils.copyList(detectDatas,
			// DetectDataVo.class);
			List<DetectDataVo> formDatas = BeanUtils.copyList(detectDatas, (d) -> {
				DetectDataVo vo = new DetectDataVo();
				BeanUtils.copyProperties(d, vo);
				try {
					vo.setDetectTitle(Long.valueOf(d.getDetectModule()));
				} catch (Exception e) {
				}
				return vo;
			});

			formDatas.forEach((data) -> {

				// 查询 data danger
				List<IntuitiveDetectDanger> detectDanger = detectDangerService.getByDataId(data.getId());
				if (CollUtil.isNotEmpty(detectDanger)) {
					List<DetectDataDangerVo> formDatadangers = BeanUtils.copyList(detectDanger,
							DetectDataDangerVo.class);
					data.setDangers(formDatadangers);
				}
			});

			return Result.SUCCESS(formDatas);
		}
		return Result.SUCCESS();
	}
}
