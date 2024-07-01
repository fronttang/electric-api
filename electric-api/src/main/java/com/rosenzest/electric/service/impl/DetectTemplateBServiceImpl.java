package com.rosenzest.electric.service.impl;

import com.rosenzest.electric.entity.DetectTemplateB;
import com.rosenzest.electric.mapper.DetectTemplateBMapper;
import com.rosenzest.electric.service.IDetectTemplateBService;
import com.rosenzest.electric.vo.DetectTableVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
@Service
public class DetectTemplateBServiceImpl extends ModelBaseServiceImpl<DetectTemplateBMapper, DetectTemplateB> implements IDetectTemplateBService {

	
	@Override
	public List<DetectTableVo> getTableBByTemplateId(Long templateId, String type) {
		return this.baseMapper.getTableBByTemplateId(templateId, type);
	}

}
