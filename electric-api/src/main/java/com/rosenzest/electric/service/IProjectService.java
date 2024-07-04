package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.Project;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
public interface IProjectService extends IModelBaseService<Project> {

	/**
	 * 根据检测单位查询项目列表
	 * @return
	 */
	List<Project> getProjectByDetectId(Long detectId);

	/**
	 * 根据工作人员查询项目
	 * @param userId
	 * @return
	 */
	List<Project> getProjectByWorkerId(Long userId);
}
