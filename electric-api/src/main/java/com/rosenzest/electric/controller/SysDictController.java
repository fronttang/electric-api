package com.rosenzest.electric.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.entity.SysDictData;
import com.rosenzest.electric.entity.SysDictType;
import com.rosenzest.electric.service.ISysDictDataService;
import com.rosenzest.electric.service.ISysDictTypeService;
import com.rosenzest.electric.vo.DictDataVo;
import com.rosenzest.electric.vo.DictTypeVo;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Api(tags = "系统管理")
@RestController
@RequestMapping("/dict")
public class SysDictController {
	
	@Autowired
	private ISysDictDataService dictDataService;
	
	@Autowired
	private ISysDictTypeService dictTypeService;

	@ApiOperation(tags = "系统管理", value = "数据字典")
	@GetMapping("/list")
	public Result<List<DictTypeVo>> list(){
		
		List<SysDictType> dictTypes = dictTypeService.getBusinessDictType();
		List<SysDictData> dictDatas = dictDataService.getBusinessDictData();
		
		Map<String, SysDictType> dictTypesMap = dictTypes.stream().collect(Collectors.toMap(SysDictType::getDictType, Function.identity()));
		
		Map<String, List<SysDictData>> dictDatasMaps = dictDatas.stream().collect(Collectors.groupingBy(SysDictData::getDictType, Collectors.toList()));
		
		List<DictTypeVo> result = new ArrayList<DictTypeVo>();
		
		dictTypesMap.forEach((type, dictType)->{
			
			DictTypeVo typeVo = new DictTypeVo();
			BeanUtils.copyProperties(dictType, typeVo);
			
			List<SysDictData> dataList = dictDatasMaps.get(type);
			if(CollUtil.isNotEmpty(dataList)) {
				List<DictDataVo> dataVoList = BeanUtils.copyList(dataList, DictDataVo.class);
				typeVo.setDictData(dataVoList);
			}
			
			result.add(typeVo);
		});
		
		return Result.SUCCESS(result);
	}
}
