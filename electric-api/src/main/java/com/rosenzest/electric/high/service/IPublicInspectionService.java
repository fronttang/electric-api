package com.rosenzest.electric.high.service;

import com.rosenzest.electric.high.dto.PublicInspectionDto;
import com.rosenzest.electric.high.entity.PublicInspection;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 公共查阅 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
public interface IPublicInspectionService extends IModelBaseService<PublicInspection> {

	PublicInspection findPublicInspection(PublicInspectionDto data);

	PublicInspection savePublicInspection(PublicInspectionDto data);

}
