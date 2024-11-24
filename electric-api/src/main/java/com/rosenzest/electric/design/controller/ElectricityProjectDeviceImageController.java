package com.rosenzest.electric.design.controller;

import static cn.hutool.core.bean.BeanUtil.copyProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.base.ListResult;
import com.rosenzest.base.Result;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.electric.controller.ElectricBaseController;
import com.rosenzest.electric.design.dto.ElectricityProjectDeviceImageDto;
import com.rosenzest.electric.design.dto.ElectricityProjectDeviceImageQuery;
import com.rosenzest.electric.design.dto.ElectricityProjectDeviceImages;
import com.rosenzest.electric.design.entity.ElectricityProjectDeviceImage;
import com.rosenzest.electric.design.service.IElectricityProjectDeviceImageService;
import com.rosenzest.electric.design.vo.ElectricityProjectDeviceImageVo;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.enums.UserType;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 设备照片集 前端控制器
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@TokenRule(project = false, userType = UserType.ELECTRIC)
@Api(tags = "电力设计")
@RestController
@RequestMapping("/electricity/project/device/image")
public class ElectricityProjectDeviceImageController extends ElectricBaseController {

	@Autowired
	private IElectricityProjectDeviceImageService deviceImageService;

	@ApiOperation(tags = "电力设计", value = "项目设备照片集合列表")
	@PostMapping("/list")
	public ListResult<ElectricityProjectDeviceImageVo> list(@RequestBody ElectricityProjectDeviceImageQuery query) {

		Page<ElectricityProjectDeviceImage> queryList = deviceImageService.queryList(query);

		List<ElectricityProjectDeviceImage> records = queryList.getRecords();
		if (CollUtil.isNotEmpty(records)) {
			List<ElectricityProjectDeviceImageVo> datas = BeanUtils.copyList(records,
					ElectricityProjectDeviceImageVo.class);
			return ListResult.SUCCESS(queryList.getTotal(), datas);
		}

		return ListResult.SUCCESS(0L, new ArrayList<ElectricityProjectDeviceImageVo>());
	}

	@ApiOperation(tags = "电力设计", value = "添加/修改项目设备照片集合")
	@PostMapping("")
	public Result<ElectricityProjectDeviceImageVo> add(@RequestBody @Valid ElectricityProjectDeviceImageDto data) {

		ElectricityProjectDeviceImage entity = new ElectricityProjectDeviceImage();
		BeanUtils.copyProperties(data, entity);

		if (Objects.isNull(data.getId())) {
			entity.setCreateBy(String.valueOf(getUserId()));
		}

		if (deviceImageService.saveOrUpdate(entity)) {
			ElectricityProjectDeviceImageVo vo = copyProperties(entity, ElectricityProjectDeviceImageVo.class);
			return Result.SUCCESS(vo);
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "电力设计", value = "删除项目设备照片集合")
	@DeleteMapping("/{imageId}")
	public Result<?> delete(@PathVariable Long imageId) {

		if (deviceImageService.removeById(imageId)) {
			return Result.SUCCESS();
		}
		return Result.ERROR();
	}

	@ApiOperation(tags = "电力设计", value = "查看照片")
	@GetMapping("/{imageId}")
	public Result<List<ElectricityProjectDeviceImages>> images(@PathVariable Long imageId) {

		List<ElectricityProjectDeviceImages> result = new ArrayList<ElectricityProjectDeviceImages>();

		ElectricityProjectDeviceImage image = deviceImageService.getById(imageId);

		if (Objects.nonNull(image)) {
			result = image.getImages();
		}

		return Result.SUCCESS(result);
	}

	@ApiOperation(tags = "电力设计", value = "添加/修改照片")
	@PostMapping("/{imageId}")
	public Result<List<ElectricityProjectDeviceImages>> images(@PathVariable Long imageId,
			@RequestBody @Valid List<ElectricityProjectDeviceImages> images) {

		ElectricityProjectDeviceImage image = deviceImageService.getById(imageId);

		if (Objects.isNull(image)) {
			return Result.ERROR();
		}

		ElectricityProjectDeviceImage update = new ElectricityProjectDeviceImage();
		update.setId(imageId);
		update.setImages(images);

		if (deviceImageService.updateById(update)) {
			return Result.SUCCESS();
		}

		return Result.ERROR();
	}
}
