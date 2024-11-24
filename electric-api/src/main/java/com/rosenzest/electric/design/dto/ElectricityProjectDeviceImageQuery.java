package com.rosenzest.electric.design.dto;

import javax.validation.constraints.NotNull;

import com.rosenzest.electric.dto.PageQuery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * 设备照片集
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ElectricityProjectDeviceImageQuery extends PageQuery {

	/**
	 * 项目ID
	 */
	@NotNull(message = "项目ID不能为空")
	@ApiModelProperty(value = "项目ID", required = true)
	private Long projectId;

	/**
	 * 项目设备id
	 */
	@NotNull(message = "项目设备ID不能为空")
	@ApiModelProperty(value = "项目设备id", required = true)
	private Long deviceId;

	/**
	 * 照片集名称
	 */
	@ApiModelProperty("搜索关键字")
	private String keyword;

}
