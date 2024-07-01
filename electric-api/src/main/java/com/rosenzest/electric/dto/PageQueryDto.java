package com.rosenzest.electric.dto;

import com.rosenzest.base.constant.SystemConstants;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class PageQueryDto {

	private int page = SystemConstants.DEFAULT_PAGE;
	
	private int pageSize = SystemConstants.DEFAULT_PAGE_SIZE;
}
