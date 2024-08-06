package com.rosenzest.electric.mapper;

import java.util.List;

import com.rosenzest.electric.dto.ProjectAreaDto;
import com.rosenzest.electric.entity.ProjectArea;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * 项目区域 Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
public interface ProjectAreaMapper extends ModelBaseMapper<ProjectArea> {

	List<ProjectAreaDto> getProjectAreas(String projectId);

	List<ProjectAreaDto> getProjectAreasByAreaUser(AreaUserInfoVo userInfo);

}
