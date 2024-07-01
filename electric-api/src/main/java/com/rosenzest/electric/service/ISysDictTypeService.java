package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.SysDictType;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
public interface ISysDictTypeService extends IModelBaseService<SysDictType> {

	/**
	 * 业务字典数据列表
	 * @return
	 */
	List<SysDictType> getBusinessDictType();

}
