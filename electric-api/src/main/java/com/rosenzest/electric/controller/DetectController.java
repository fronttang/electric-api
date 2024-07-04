package com.rosenzest.electric.controller;

import java.util.Collections;
import java.util.Comparator;
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

import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.dto.UnitAreaDangerDto;
import com.rosenzest.electric.entity.IntuitiveDetect;
import com.rosenzest.electric.entity.IntuitiveDetectDanger;
import com.rosenzest.electric.entity.IntuitiveDetectData;
import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.service.IDetectTemplateBService;
import com.rosenzest.electric.service.IIntuitiveDetectDangerService;
import com.rosenzest.electric.service.IIntuitiveDetectDataService;
import com.rosenzest.electric.service.IIntuitiveDetectService;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.vo.DetectDataDangerVo;
import com.rosenzest.electric.vo.DetectDataVo;
import com.rosenzest.electric.vo.DetectTableVo;
import com.rosenzest.electric.vo.UnitAreaDangerVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "检测表")
@RestController
@RequestMapping("/detect")
public class DetectController extends ServerBaseController {

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

	@ApiOperation(tags = "检测表", value = "项目检测表列表")
	@GetMapping("/table/{projectId}")
	public Result<List<DetectTableVo>> tableList(@PathVariable Long projectId) {

		// 当前检测员是否有项目权限 TODO

		Project project = projectService.getById(projectId);

		// 项目检测表模板ID
		Long templateId = project.getTemplateId();

		// 直观检测表 A类 C类
		List<IntuitiveDetect> intuitiveDetect = intuitiveDetectService.getIntuitiveDetectByTemplateId(templateId);

		List<DetectTableVo> results = BeanUtils.copyList(intuitiveDetect, DetectTableVo.class);

		// 仪器检测 B类表

		List<DetectTableVo> tableBList = templateBService.getTableBByTemplateId(templateId, "1");
		results.addAll(tableBList);

		Collections.sort(results, Comparator.comparing(DetectTableVo::getType));

		return Result.SUCCESS(results);
	}

	@ApiOperation(tags = "检测表", value = "检测表内容")
	@GetMapping("/data/{tableId}")
	public Result<List<DetectDataVo>> dataList(@PathVariable String tableId) {

		List<IntuitiveDetectData> detectDatas = detectDataService.getByDetectId(Long.valueOf(tableId));
		if (CollUtil.isNotEmpty(detectDatas)) {

			List<DetectDataVo> results = BeanUtils.copyList(detectDatas, DetectDataVo.class);

			results.forEach((data) -> {
				// 查询 data danger
				List<IntuitiveDetectDanger> detectDanger = detectDangerService.getByDataId(data.getId());
				if (CollUtil.isNotEmpty(detectDanger)) {
					List<DetectDataDangerVo> dangers = BeanUtils.copyList(detectDanger, DetectDataDangerVo.class);
					data.setDangers(dangers);
				}
			});
			return Result.SUCCESS(results);
		}
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表", value = "隐患列表（公共区域/户）")
	@GetMapping("/area/danger/{unitAreaId}")
	public Result<List<UnitAreaDangerVo>> areaDangerList(Long unitAreaId) {

		List<OwnerUnitDanger> areaDangers = unitDangerService.getByUnitAreaId(unitAreaId);
		List<UnitAreaDangerVo> results = BeanUtils.copyList(areaDangers, UnitAreaDangerVo.class);

		return Result.SUCCESS(results);
	}

	@ApiOperation(tags = "检测表", value = "添加/修改隐患（公共区域/户）")
	@PostMapping("/area/danger")
	public Result<?> saveAreaDanger(@RequestBody @Valid UnitAreaDangerDto danger) {

		if (danger.getDangerId() != null) {
			IntuitiveDetectDanger detectDanger = detectDangerService.getById(danger.getDangerId());
			if (detectDanger != null) {
				danger.setLevel(detectDanger.getLevel());
				danger.setDescription(detectDanger.getDescription());
				danger.setSuggestions(detectDanger.getSuggestions());
			}
		}
		OwnerUnitDanger areaDanger = new OwnerUnitDanger();
		BeanUtils.copyProperties(danger, areaDanger);
		unitDangerService.saveOrUpdate(areaDanger);

		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表", value = "删除隐患（公共区域/户）")
	@DeleteMapping("/area/danger/{dangerId}")
	public Result<?> deleteAreaDanger(@PathVariable Long dangerId) {

		unitDangerService.removeById(dangerId);

		return Result.SUCCESS();
	}
}
