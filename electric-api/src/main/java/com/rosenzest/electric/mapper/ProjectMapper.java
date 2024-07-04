package com.rosenzest.electric.mapper;

import java.util.List;

import com.rosenzest.electric.entity.Project;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
public interface ProjectMapper extends ModelBaseMapper<Project> {

	List<Project> getProjectByWorkerId(Long userId);

}
