package com.rosenzest.electric.high.vo;

import javax.validation.constraints.NotBlank;

import com.rosenzest.electric.high.dto.BaseHighDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UnitSmallVo extends BaseHighDto {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 检测地址
	 */
	@ApiModelProperty("检测地址")
	private String address;

	/**
	 * 联系人/负责人/业主/楼栋长
	 */
	@ApiModelProperty("业主")
	private String contact;

	/**
	 * 联系电话
	 */
	@ApiModelProperty("联系电话")
	private String phone;

	/**
	 * 档案编号
	 */
	@ApiModelProperty("档案编号")
	private String code;

	/**
	 * 所属网格
	 */
	@ApiModelProperty("所属网格")
	private String grid;

	/**
	 * 业主单元类型,同项目类型
	 */
	// private String type;

	/**
	 * 检测单位ID
	 */
	// private Long detectId;

	/**
	 * 检测单位名称
	 */
	// private String detectName;

	/**
	 * 项目名称
	 */
	// private String projectName;

	/**
	 * 单位类型,见字典:industrial_unit_type
	 */
	@ApiModelProperty("单位类型,见字典:small_unit_type")
	private String unitType;

	/**
	 * 区县
	 */
	@ApiModelProperty(value = "区", required = true)
	@NotBlank(message = "区不能为空")
	private String district;

	/**
	 * 街道
	 */
	@ApiModelProperty(value = "街道", required = true)
	@NotBlank(message = "街道不能为空")
	private String street;

	/**
	 * 社区
	 */
	@ApiModelProperty(value = "社区", required = true)
	@NotBlank(message = "社区不能为空")
	private String community;

	/**
	 * 建筑面积
	 */
	@ApiModelProperty("使用面积")
	private String acreage;

	/**
	 * 签订安全承诺书1是0否
	 */
	private String safetyCommitment;

	/**
	 * 张贴违规住人海报1是0否
	 */
	private String illegalResidence;

	/**
	 * ABC干粉灭火器数量
	 */
	private Integer abcDpfeQuantity;

	/**
	 * ABC干粉灭火器是否完好1是0否
	 */
	private String abcDpfeStatus;

	/**
	 * 应急照明灯有无1有0无
	 */
	private String emergencyLightingHas;

	/**
	 * 应急照明灯是否完好1是0否
	 */
	private String emergencyLightingStatus;

	/**
	 * 疏散指示标志有无1有0无
	 */
	private String evacuationSignsHas;

	/**
	 * 疏散指示标志是否完好1是0否
	 */
	private String evacuationSignsStatus;

	/**
	 * 独立式感烟探测器有无1有0无
	 */
	private String smokeDetectorHas;

	/**
	 * 独立式感烟探测器是否完好1是0否
	 */
	private String smokeDetectorStatus;

	/**
	 * 消防软管卷盘有无1有0无
	 */
	private String fireHoseReelsHas;

	/**
	 * 消防软管卷盘是否完好1是0否
	 */
	private String fireHoseReelsStatus;

	/**
	 * 防火分隔设施有无1有0无
	 */
	private String fireSeparationHas;

	/**
	 * 防火分隔设施是否完好1是0否
	 */
	private String fireSeparationStatus;

	/**
	 * 营业执照
	 */
	private String businessLicense;

	/**
	 * 门头照
	 */
	private String doorPic;

}
