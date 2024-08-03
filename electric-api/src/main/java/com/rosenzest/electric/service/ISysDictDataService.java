package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.SysDictData;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
public interface ISysDictDataService extends IModelBaseService<SysDictData> {

	/**
	 * 业务字典数据列表
	 * 
	 * @return
	 */
	List<SysDictData> getBusinessDictData();

	/**
	 * 品牌厂家字典
	 * 
	 * @param detectId
	 * @return
	 */
	List<SysDictData> getBrandDict(Long detectId, String keyword);

	/**
	 * 根据类型查字典数据
	 * 
	 * @return
	 */
	List<SysDictData> getDictDataByType(String dictType);
}
