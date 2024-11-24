package com.rosenzest.electric.design.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rosenzest.electric.design.dto.ElectricityProjectDeviceImages;
import com.rosenzest.electric.design.type.DeviceImagesTypeHandler;
import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 设备照片集
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@Data
@TableName(autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
public class ElectricityProjectDeviceImage extends BaseEntity<ElectricityProjectDeviceImage> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 项目设备id
	 */
	private Long deviceId;

	/**
	 * 照片集名称
	 */
	private String imageName;

	/**
	 * 照片集类型
	 */
	private String imageType;

	/**
	 * 照片/视频
	 */
	@TableField(typeHandler = DeviceImagesTypeHandler.class)
	private List<ElectricityProjectDeviceImages> images;

}
