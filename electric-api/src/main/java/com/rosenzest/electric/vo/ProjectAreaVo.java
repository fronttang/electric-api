package com.rosenzest.electric.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ProjectAreaVo {

	private String code;
	
	private String name;
	
	private String type;
	
	private List<ProjectAreaVo> sub = new ArrayList<ProjectAreaVo>();
}
