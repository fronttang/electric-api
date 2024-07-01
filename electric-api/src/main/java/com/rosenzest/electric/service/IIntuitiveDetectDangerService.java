package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.IntuitiveDetectDanger;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 检测表内容隐患 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
public interface IIntuitiveDetectDangerService extends IModelBaseService<IntuitiveDetectDanger> {

	List<IntuitiveDetectDanger> getByDataId(Long id);

}
