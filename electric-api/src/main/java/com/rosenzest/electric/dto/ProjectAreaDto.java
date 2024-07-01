package com.rosenzest.electric.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class ProjectAreaDto {

	/**
     * 项目ID
     */
    private Long projectId;
	
	/**
     * 区县
     */
    private String district;
    
    /**
     * 区县
     */
    private String districtName;

    /**
     * 街道
     */
    private String street;
    
    /**
     * 街道
     */
    private String streetName;

    /**
     * 社区
     */
    private String community;
    
    /**
     * 社区
     */
    private String communityName;

    /**
     * 村
     */
    private String hamlet; 
    
    /**
     * 村
     */
    private String hamletName;
}
