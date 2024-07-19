package com.rosenzest.electric.high.vo;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 缺失设备
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Slf4j
@Data
public class OwnerUnitMissDeviceVo {

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 业主单元ID
	 */
	@ApiModelProperty("业主单元ID")
	private Long unitId;

	/**
	 * 楼层
	 */
	@ApiModelProperty("楼层")
	private String floor;

	/**
	 * 类型1楼层设备2楼栋设备
	 */
	@ApiModelProperty("类型1楼层设备2楼栋设备")
	private String type;

	/**
	 * 缺失设备
	 */
	@ApiModelProperty("缺失设备")
	private JSONObject device;

	/**
	 * 缺失设备总数
	 * 
	 * @return
	 */
	@ApiModelProperty("缺失设备总数")
	public Integer getTotalDevices() {
		BigDecimal totalDevices = new BigDecimal(0);
		if (this.device != null) {

			Set<Entry<String, Object>> entrySet = this.device.entrySet();
			Iterator<Entry<String, Object>> iterator = entrySet.iterator();

			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				try {
					totalDevices = totalDevices.add(new BigDecimal(String.valueOf(entry.getValue())));
				} catch (Exception e) {
					log.error("", e);
				}
			}
		}
		return totalDevices.intValue();
	}

}
