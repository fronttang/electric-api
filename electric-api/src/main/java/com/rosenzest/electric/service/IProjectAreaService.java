package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.dto.ProjectAreaDto;
import com.rosenzest.electric.entity.ProjectArea;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 项目区域 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
public interface IProjectAreaService extends IModelBaseService<ProjectArea> {

	List<ProjectAreaDto> getProjectAreas(String projectId);

}
