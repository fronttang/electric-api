package com.rosenzest.electric.storage.dto;

import com.rosenzest.electric.dto.PageQuery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyStorageQuery extends PageQuery {

	/**
	 * 搜索关键字
	 */
	@ApiModelProperty("搜索关键字")
	private String keyword;

}
