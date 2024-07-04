package com.rosenzest.electric.mapper;

import java.util.List;

import com.rosenzest.electric.entity.ProjectWorkerArea;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * 项目工作人员区域 Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
public interface ProjectWorkerAreaMapper extends ModelBaseMapper<ProjectWorkerArea> {

	List<ProjectWorkerArea> getProjectWorkerArea(Long workerId);

}
