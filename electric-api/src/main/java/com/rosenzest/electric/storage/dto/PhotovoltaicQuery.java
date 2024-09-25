package com.rosenzest.electric.storage.dto;

import com.rosenzest.electric.dto.PageQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PhotovoltaicQuery extends PageQuery {

	/**
	 * 搜索关键字
	 */
	private String keyword;

}
