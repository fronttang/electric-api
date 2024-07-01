package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.IntuitiveDetectData;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 检测表内容 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
public interface IIntuitiveDetectDataService extends IModelBaseService<IntuitiveDetectData> {

	List<IntuitiveDetectData> getByDetectId(Long titleId);

}
