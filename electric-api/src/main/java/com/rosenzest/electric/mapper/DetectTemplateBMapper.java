package com.rosenzest.electric.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rosenzest.electric.entity.DetectTemplateB;
import com.rosenzest.electric.vo.DetectFormVo;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
public interface DetectTemplateBMapper extends ModelBaseMapper<DetectTemplateB> {

	List<DetectFormVo> getTableBByTemplateId(@Param("templateId") Long templateId, @Param("type") String type);

}
