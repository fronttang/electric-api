package com.rosenzest.electric.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.ListResult;
import com.rosenzest.base.PageList;
import com.rosenzest.base.Result;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.base.util.SnowFlakeUtil;
import com.rosenzest.electric.dto.InitialOwnerUnitSettingDto;
import com.rosenzest.electric.dto.OwnerUnitAreaDto;
import com.rosenzest.electric.dto.OwnerUnitQueryDto;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitArea;
import com.rosenzest.electric.service.IOwnerUnitAreaService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitAreaVo;
import com.rosenzest.server.base.controller.ServerBaseController;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "业主单元")
@RestController
@RequestMapping("/unit")
public class OwnerUnitController extends ServerBaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;
	
	@Autowired
	private IOwnerUnitAreaService ownerUnitAreaService;
	
	@ApiOperation(tags = "业主单元", value = "公共区域/户列表")
	@GetMapping("/area/list")
	public Result<List<OwnerUnitAreaVo>> listArea(@RequestParam(name = "unitId") Long unitId, @RequestParam(name = "type", required = false) String type){
		
		List<OwnerUnitAreaVo> unitAreas = ownerUnitAreaService.queryUnitAreaByType(unitId, type);
		
		return Result.SUCCESS(unitAreas);
	}
	
	@ApiOperation(tags = "业主单元", value = "新增/修改公共区域/户")
	@PostMapping("/area")
	public Result<?> saveArea(@RequestBody OwnerUnitAreaDto data){
		
		OwnerUnitArea entity = new OwnerUnitArea();
		BeanUtils.copyProperties(data, entity);
		
		ownerUnitAreaService.saveOrUpdate(entity);
		
		return Result.SUCCESS();
	}
	
	@ApiOperation(tags = "业主单元", value = "删除公共区域/户")
	@DeleteMapping("/area/{id}")
	public Result<?> deleteArea(Long id){
		
		ownerUnitAreaService.removeById(id);
		
		return Result.SUCCESS();
	}
	
	@ApiOperation(tags = "业主单元", value = "初检列表")
	@GetMapping("/initial/list")
	public ListResult<InitialOwnerUnitVo> list(@Valid OwnerUnitQueryDto query){
		
		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		List<InitialOwnerUnitVo>  unitList = ownerUnitService.queryInitialList(query, pageList);
		
		return ListResult.SUCCESS(pageList.getTotalNum(), unitList);
	}
	
	@ApiOperation(tags = "业主单元", value = "设置初检状态")
	@PostMapping("/setting")
	public Result<?> setting(@RequestBody @Valid InitialOwnerUnitSettingDto data){
		OwnerUnit ownerUnit = ownerUnitService.getById(data.getId());
		if(ownerUnit == null) {
			throw new BusinessException(400, "业主单元不存在");
		}
		
		OwnerUnit update = new OwnerUnit();
		update.setId(data.getId());
		update.setIsDangerNotice(data.getIsDangerNotice());
		update.setIsHouseholdRate(data.getIsHouseholdRate());
		update.setIsTest(data.getIsTest());
		update.setIsTestReason(data.getIsTestReason());
		
		if("1".equalsIgnoreCase(data.getIsHouseholdRate())){
			update.setInitialTestStatus("2");
		} else if("1".equalsIgnoreCase(data.getIsTest())) {
			update.setInitialTestStatus("1");
		}
		
		if(StrUtil.isBlank(ownerUnit.getInitialTestNo())) {
			update.setInitialTestNo(SnowFlakeUtil.uniqueString());
		}
		
		boolean save = ownerUnitService.saveOrUpdate(update);
		if(save) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}
}
