package com.rosenzest.electric.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rosenzest.electric.dto.OwnerUnitQuery;
import com.rosenzest.electric.dto.OwnerUnitReviewQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.owner.vo.OwnerUnitListVo;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitReviewVo;
import com.rosenzest.electric.vo.OwnerUnitVo;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * 业主单元 Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
public interface OwnerUnitMapper extends ModelBaseMapper<OwnerUnit> {

	List<InitialOwnerUnitVo> queryInitialList(OwnerUnitQuery query);

	OwnerUnitVo getOwnerUnitById(Long unitId);

	List<OwnerUnitReviewVo> queryReviewList(OwnerUnitReviewQuery query);

	List<OwnerUnitListVo> getOwnerUnitListByOwner(@Param("userId") Long userId, @Param("projectId") Long projectId);

}
