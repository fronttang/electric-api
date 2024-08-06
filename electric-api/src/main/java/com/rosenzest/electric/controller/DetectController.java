package com.rosenzest.electric.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.Result;
import com.rosenzest.base.enums.EnumUtils;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.entity.IntuitiveDetect;
import com.rosenzest.electric.entity.IntuitiveDetectDanger;
import com.rosenzest.electric.entity.IntuitiveDetectData;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitArea;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.enums.HighRiskType;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.service.IDetectTemplateBService;
import com.rosenzest.electric.service.IIntuitiveDetectDangerService;
import com.rosenzest.electric.service.IIntuitiveDetectDataService;
import com.rosenzest.electric.service.IIntuitiveDetectService;
import com.rosenzest.electric.service.IOwnerUnitAreaService;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.vo.DetectDataDangerVo;
import com.rosenzest.electric.vo.DetectDataVo;
import com.rosenzest.electric.vo.DetectFormVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "检测表")
@RestController
@RequestMapping("/detect")
public class DetectController extends ServerBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IIntuitiveDetectService intuitiveDetectService;

	@Autowired
	private IDetectTemplateBService templateBService;

	@Autowired
	private IIntuitiveDetectDataService detectDataService;

	@Autowired
	private IIntuitiveDetectDangerService detectDangerService;

	@Autowired
	private IOwnerUnitDangerService unitDangerService;

	@Autowired
	private IOwnerUnitAreaService ownerUnitAreaService;

	@Autowired
	private IOwnerUnitBuildingService ownerUnitBuildingService;

	@ApiOperation(tags = "检测表", value = "检测表")
	@GetMapping("/form")
	public Result<List<DetectFormVo>> formList(@RequestParam(value = "unitId") Long unitId,
			@RequestParam(value = "unitAreaId", required = false) Long unitAreaId,
			@RequestParam(value = "buildingId", required = false) Long buildingId) {

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit == null) {
			return Result.SUCCESS();
		}

		Project project = projectService.getById(ownerUnit.getProjectId());

		if (project == null) {
			return Result.SUCCESS();
		}

		String attribution = null;

		if (unitAreaId != null) {
			OwnerUnitArea area = ownerUnitAreaService.getById(unitAreaId);
			if (area != null) {
				attribution = area.getType();
			}
		}

		if (buildingId != null) {
			OwnerUnitBuilding building = ownerUnitBuildingService.getById(buildingId);
			// 配电房
			if (building != null && "1".equalsIgnoreCase(building.getType())) {
				attribution = "3";
			}
		}

		boolean isHighRisk = ProjectType.HIGH_RISK.code().equalsIgnoreCase(project.getType());

		final HighRiskType type = isHighRisk ? EnumUtils.init(HighRiskType.class).fromCode(ownerUnit.getHighRiskType())
				: null;

		// 项目检测表模板ID
		Long templateId = project.getTemplateId();

		List<DetectFormVo> results = new ArrayList<DetectFormVo>();

		// 直观检测表 A类 C类
		List<IntuitiveDetect> intuitiveDetect = intuitiveDetectService.getIntuitiveDetectByTemplateId(templateId,
				attribution, type);

		if (CollUtil.isNotEmpty(intuitiveDetect)) {
			results.addAll(BeanUtils.copyList(intuitiveDetect, DetectFormVo.class));
		}

		results.forEach((form) -> {

			// 查询该表隐患数
			Integer dangers = unitDangerService.countFormDangers(form.getId(), unitId, unitAreaId, buildingId);
			form.setDangers(dangers);

			List<IntuitiveDetectData> detectDatas = detectDataService.getByDetectId(form.getId(), type);
			if (CollUtil.isNotEmpty(detectDatas)) {

				List<DetectDataVo> formDatas = BeanUtils.copyList(detectDatas, DetectDataVo.class);

				formDatas.forEach((data) -> {

					if (isHighRisk) {

						List<IntuitiveDetectData> subDetectDatas = detectDataService.getByViewParent(data.getId(),
								type);
						if (CollUtil.isNotEmpty(subDetectDatas)) {

							List<DetectDataVo> subDatas = BeanUtils.copyList(subDetectDatas, DetectDataVo.class);
							subDatas.forEach((subData) -> {
								// 查询 data danger
								List<IntuitiveDetectDanger> detectDanger = detectDangerService
										.getByDataId(subData.getId());
								if (CollUtil.isNotEmpty(detectDanger)) {
									List<DetectDataDangerVo> formDatadangers = BeanUtils.copyList(detectDanger,
											DetectDataDangerVo.class);
									subData.setDangers(formDatadangers);
								}
							});

							data.setSubDatas(subDatas);
						}
					} else {

						// 查询 data danger
						List<IntuitiveDetectDanger> detectDanger = detectDangerService.getByDataId(data.getId());
						if (CollUtil.isNotEmpty(detectDanger)) {
							List<DetectDataDangerVo> formDatadangers = BeanUtils.copyList(detectDanger,
									DetectDataDangerVo.class);
							data.setDangers(formDatadangers);
						}
					}
				});

				form.setDatas(formDatas);
			}
		});

		if (!isHighRisk) {
			// 仪器检测 B类表

			List<DetectFormVo> formBList = templateBService.getTableBByTemplateId(templateId, "1" + attribution);

			formBList.forEach((form) -> {
				// 查询该表隐患数
				Integer dangers = unitDangerService.countFormbDangers(form.getCode(), unitId, unitAreaId, buildingId);
				form.setDangers(dangers);
			});

			results.addAll(formBList);
		}

		Collections.sort(results, Comparator.comparing(DetectFormVo::getType));

		return Result.SUCCESS(results);
	}

	@ApiOperation(tags = "检测表", value = "检测表(按项目查)")
	@GetMapping("/form/project")
	public Result<List<DetectFormVo>> formListByProject(@RequestParam(value = "projectId") Long projectId,
			@RequestParam(value = "type", required = false) String type) {

		Project project = projectService.getById(projectId);

		if (project == null) {
			return Result.SUCCESS();
		}

		boolean isHighRisk = ProjectType.HIGH_RISK.code().equalsIgnoreCase(project.getType());

		if (isHighRisk && type == null) {
			return Result.SUCCESS();
		}

		final HighRiskType highRiskType = isHighRisk ? EnumUtils.init(HighRiskType.class).fromCode(type) : null;

		// 项目检测表模板ID
		Long templateId = project.getTemplateId();

		List<DetectFormVo> results = new ArrayList<DetectFormVo>();

		// 直观检测表 A类 C类
		List<IntuitiveDetect> intuitiveDetect = intuitiveDetectService.getIntuitiveDetectByTemplateId(templateId, null,
				highRiskType);

		if (CollUtil.isNotEmpty(intuitiveDetect)) {
			results.addAll(BeanUtils.copyList(intuitiveDetect, DetectFormVo.class));
		}

		results.forEach((form) -> {

			// 查询该表隐患数
			// Integer dangers = intuitiveDetectService.getFormDangers(form.getId(), unitId,
			// unitAreaId, buildingId);
			form.setDangers(0);

			List<IntuitiveDetectData> detectDatas = detectDataService.getByDetectId(form.getId(), highRiskType);
			if (CollUtil.isNotEmpty(detectDatas)) {

				List<DetectDataVo> formDatas = BeanUtils.copyList(detectDatas, DetectDataVo.class);

				formDatas.forEach((data) -> {

					if (isHighRisk) {

						List<IntuitiveDetectData> subDetectDatas = detectDataService.getByViewParent(data.getId(),
								highRiskType);
						if (CollUtil.isNotEmpty(subDetectDatas)) {

							List<DetectDataVo> subDatas = BeanUtils.copyList(subDetectDatas, DetectDataVo.class);
							subDatas.forEach((subData) -> {
								// 查询 data danger
								List<IntuitiveDetectDanger> detectDanger = detectDangerService
										.getByDataId(subData.getId());
								if (CollUtil.isNotEmpty(detectDanger)) {
									List<DetectDataDangerVo> formDatadangers = BeanUtils.copyList(detectDanger,
											DetectDataDangerVo.class);
									subData.setDangers(formDatadangers);
								}
							});

							data.setSubDatas(subDatas);
						}
					} else {

						// 查询 data danger
						List<IntuitiveDetectDanger> detectDanger = detectDangerService.getByDataId(data.getId());
						if (CollUtil.isNotEmpty(detectDanger)) {
							List<DetectDataDangerVo> formDatadangers = BeanUtils.copyList(detectDanger,
									DetectDataDangerVo.class);
							data.setDangers(formDatadangers);
						}
					}
				});

				form.setDatas(formDatas);
			}
		});

		if (!isHighRisk) {
			// 仪器检测 B类表

			List<DetectFormVo> viewFormb = templateBService.getViewTableBByTemplateId(templateId);

			Map<String, DetectFormVo> formbMap = new HashMap<String, DetectFormVo>();

			viewFormb.stream().forEach((form) -> {

				DetectFormVo detectFormVo = formbMap.get(form.getCode());
				if (detectFormVo == null) {
					detectFormVo = form;
					formbMap.put(form.getCode(), detectFormVo);
				}

				List<String> attribution = detectFormVo.getAttribution();
				if (attribution == null) {
					attribution = new ArrayList<String>();
				}
				attribution.add(StrUtil.subSuf(form.getAttrType(), 1));
				detectFormVo.setAttribution(attribution);
			});

			List<DetectFormVo> formbVo = new ArrayList<DetectFormVo>(formbMap.values());
			Collections.sort(formbVo, Comparator.comparing(DetectFormVo::getCode));

			results.addAll(formbVo);
		}

		Collections.sort(results, Comparator.comparing(DetectFormVo::getType));

		return Result.SUCCESS(results);
	}

//	@ApiOperation(tags = "检测表", value = "检测表内容")
//	@GetMapping("/data/{formId}")
//	public Result<List<DetectDataVo>> dataList(@PathVariable Long formId) {
//
//		List<IntuitiveDetectData> detectDatas = detectDataService.getByDetectId(formId);
//		if (CollUtil.isNotEmpty(detectDatas)) {
//
//			List<DetectDataVo> results = BeanUtils.copyList(detectDatas, DetectDataVo.class);
//
//			results.forEach((data) -> {
//				// 查询 data danger
//				List<IntuitiveDetectDanger> detectDanger = detectDangerService.getByDataId(data.getId());
//				if (CollUtil.isNotEmpty(detectDanger)) {
//					List<DetectDataDangerVo> dangers = BeanUtils.copyList(detectDanger, DetectDataDangerVo.class);
//					data.setDangers(dangers);
//				}
//			});
//			return Result.SUCCESS(results);
//		}
//		return Result.SUCCESS();
//	}
}
