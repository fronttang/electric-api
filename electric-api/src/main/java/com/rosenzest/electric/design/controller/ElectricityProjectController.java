package com.rosenzest.electric.design.controller;

import static cn.hutool.core.bean.BeanUtil.copyProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.base.ListResult;
import com.rosenzest.base.Result;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.controller.ElectricBaseController;
import com.rosenzest.electric.design.dto.ElectricityProjectDto;
import com.rosenzest.electric.design.dto.ElectricityProjectQuery;
import com.rosenzest.electric.design.entity.ElectricityProject;
import com.rosenzest.electric.design.service.IElectricityProjectService;
import com.rosenzest.electric.design.vo.ElectricityProjectVo;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.enums.UserType;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 电力设计项目 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@TokenRule(project = false, userType = UserType.ELECTRIC)
@Api(tags = "电力设计")
@RestController
@RequestMapping("/electricity/project")
public class ElectricityProjectController extends ElectricBaseController {

	@Autowired
	private IElectricityProjectService electricityProjectService;

	@ApiOperation(tags = "电力设计", value = "项目列表")
	@PostMapping("/list")
	public ListResult<ElectricityProjectVo> list(@RequestBody ElectricityProjectQuery query) {

		Page<ElectricityProject> queryList = electricityProjectService.queryList(query);

		List<ElectricityProject> records = queryList.getRecords();
		if (CollUtil.isNotEmpty(records)) {
			List<ElectricityProjectVo> datas = BeanUtils.copyList(records, ElectricityProjectVo.class);
			return ListResult.SUCCESS(queryList.getTotal(), datas);
		}

		return ListResult.SUCCESS(0L, new ArrayList<ElectricityProjectVo>());
	}

	@ApiOperation(tags = "电力设计", value = "添加/修改项目")
	@PostMapping("")
	public Result<ElectricityProjectVo> add(@RequestBody @Valid ElectricityProjectDto data) {

		ElectricityProject entity = new ElectricityProject();
		BeanUtils.copyProperties(data, entity);

		if (Objects.isNull(data.getId())) {
			entity.setCreateBy(String.valueOf(getUserId()));
		}

		if (electricityProjectService.saveOrUpdate(entity)) {
			ElectricityProjectVo vo = copyProperties(entity, ElectricityProjectVo.class);
			return Result.SUCCESS(vo);
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "电力设计", value = "删除项目")
	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable Long id) {

		ElectricityProject project = electricityProjectService.getById(id);

		if (!project.getCreateBy().equalsIgnoreCase(String.valueOf(getUserId()))) {
			throw new BusinessException(400, "没有权限");
		}

		if (electricityProjectService.removeById(id)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}
}
