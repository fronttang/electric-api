package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.electric.entity.DetectTemplateB;
import com.rosenzest.electric.vo.DetectFormVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
public interface IDetectTemplateBService extends IModelBaseService<DetectTemplateB> {

	List<DetectFormVo> getTableBByTemplateId(Long templateId, String type);

}
