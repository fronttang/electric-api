package com.rosenzest.electric.design.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.electric.design.dto.ElectricityProjectQuery;
import com.rosenzest.electric.design.entity.ElectricityProject;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 电力设计项目 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
public interface IElectricityProjectService extends IModelBaseService<ElectricityProject> {

	Page<ElectricityProject> queryList(ElectricityProjectQuery query);

}
