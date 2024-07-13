package com.rosenzest.electric.high.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UnitRentalHouseDto extends BaseHighDto {

	/**
	 * ID
	 */
	@ApiModelProperty("ID,添加是不传,修改时传")
	private Long id;

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
	 * 联系人/负责人/业主/楼栋长
	 */
	@ApiModelProperty("楼栋长")
	private String contact;

	/**
	 * 联系电话
	 */
	@ApiModelProperty("联系电话")
	private String phone;

	/**
	 * 建筑面积
	 */
	@ApiModelProperty("建筑面积")
	private String acreage;

	/**
	 * 建筑层数
	 */
	@ApiModelProperty("建筑层数")
	private Integer layers;

	/**
	 * 户数
	 */
	@ApiModelProperty("租户（套间）数")
	private Integer doorNumber;

	/**
	 * 高风险类型
	 */
	// private String highRiskType;

	/**
	 * 是否张贴电动自行车禁止入内标识
	 */
	@ApiModelProperty("是否张贴电动自行车禁止入内标识")
	private String isBicycles;

	/**
	 * 是否张贴消防通道严禁堵塞标识
	 */
	@ApiModelProperty("是否张贴消防通道严禁堵塞标识")
	private String isFirePassages;

	/**
	 * ABC干粉灭火器数量
	 */
	@ApiModelProperty("ABC干粉灭火器数量")
	private Integer abcDpfeQuantity;

	/**
	 * ABC干粉灭火器是否完好1是0否
	 */
	@ApiModelProperty("ABC干粉灭火器是否完好1是0否")
	private String abcDpfeStatus;

	/**
	 * 应急照明灯有无1有0无
	 */
	@ApiModelProperty("应急照明灯有无1有0无")
	private String emergencyLightingHas;

	/**
	 * 应急照明灯是否完好1是0否
	 */
	@ApiModelProperty("应急照明灯是否完好1是0否")
	private String emergencyLightingStatus;

	/**
	 * 疏散指示标志有无1有0无
	 */
	@ApiModelProperty("疏散指示标志有无1有0无")
	private String evacuationSignsHas;

	/**
	 * 疏散指示标志是否完好1是0否
	 */
	@ApiModelProperty("疏散指示标志是否完好1是0否")
	private String evacuationSignsStatus;

	/**
	 * 独立式感烟探测器有无1有0无
	 */
	@ApiModelProperty("独立式感烟探测器有无1有0无")
	private String smokeDetectorHas;

	/**
	 * 独立式感烟探测器是否完好1是0否
	 */
	@ApiModelProperty("独立式感烟探测器是否完好1是0否")
	private String smokeDetectorStatus;

	/**
	 * 消防软管卷盘有无1有0无
	 */
	@ApiModelProperty("消防软管卷盘有无1有0无")
	private String fireHoseReelsHas;

	/**
	 * 消防软管卷盘是否完好1是0否
	 */
	@ApiModelProperty("消防软管卷盘是否完好1是0否")
	private String fireHoseReelsStatus;

	/**
	 * 室内消火栓系统有无1有0无
	 */
	@ApiModelProperty("室内消火栓系统有无1有0无")
	private String hydrantSystemHas;

	/**
	 * 室内消火栓系统是否完好1是0否
	 */
	@ApiModelProperty("室内消火栓系统是否完好1是0否")
	private String hydrantSystemStatus;

	/**
	 * 火灾自动报警系统有无1有0无
	 */
	@ApiModelProperty("火灾自动报警系统有无1有0无")
	private String alarmSystemHas;

	/**
	 * 火灾自动报警系统是否完好1是0否
	 */
	@ApiModelProperty("火灾自动报警系统是否完好1是0否")
	private String alarmSystemStatus;

	/**
	 * 防火分隔设施有无1有0无
	 */
	@ApiModelProperty("防火分隔设施有无1有0无")
	private String fireSeparationHas;

	/**
	 * 防火分隔设施是否完好1是0否
	 */
	@ApiModelProperty("防火分隔设施是否完好1是0否")
	private String fireSeparationStatus;

	/**
	 * 营业执照
	 */
	@ApiModelProperty("营业执照")
	private String businessLicense;

	/**
	 * 门头照
	 */
	@ApiModelProperty("门头照")
	private String doorPic;

}
