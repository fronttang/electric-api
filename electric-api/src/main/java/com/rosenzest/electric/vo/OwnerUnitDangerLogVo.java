package com.rosenzest.electric.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author fronttang
 * @since 2024-07-08
 */
@Data
public class OwnerUnitDangerLogVo {

	/**
	 * 日志ID
	 */
	@ApiModelProperty("日志ID")
	private Long id;

	/**
	 * 业主单元隐患ID
	 */
	@ApiModelProperty("隐患ID")
	private Long dangerId;

	/**
	 * 操作员
	 */
	@ApiModelProperty("操作员")
	private String operator;

	/**
	 * 操作员ID
	 */
	@ApiModelProperty("操作员ID")
	private Long operatorId;

	/**
	 * 操作类型1初检2整改3复检4无法检测
	 */
	@ApiModelProperty("操作类型1初检2整改3复检4无法检测,见字典:danger_log_type")
	private String operationType;

	/**
	 * 操作员角色
	 */
	@ApiModelProperty("操作员角色")
	private String operatorRole;

	/**
	 * 操作图片
	 */
	@ApiModelProperty("操作图片")
	private String operationPic;

	/**
	 * 内容
	 */
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 备注,驳回原因等
	 */
	@ApiModelProperty("备注,驳回原因等")
	private String remark;

	/**
	 * 操作时间
	 */
	@ApiModelProperty("操作时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
