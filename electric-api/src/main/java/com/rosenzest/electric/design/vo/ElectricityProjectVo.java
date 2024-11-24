package com.rosenzest.electric.design.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 电力设计项目
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@Data
public class ElectricityProjectVo {

	/**
	 * 项目id
	 */
	private Long id;

	/**
	 * 项目名称
	 */
	private String name;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("创建时间")
	private Date createTime;

}
