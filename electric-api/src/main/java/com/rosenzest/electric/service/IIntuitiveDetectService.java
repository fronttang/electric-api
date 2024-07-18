package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.IntuitiveDetect;
import com.rosenzest.electric.enums.HighRiskType;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 直观检测表标题 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
public interface IIntuitiveDetectService extends IModelBaseService<IntuitiveDetect> {

	List<IntuitiveDetect> getIntuitiveDetectByTemplateId(Long templateId, HighRiskType type);

}
