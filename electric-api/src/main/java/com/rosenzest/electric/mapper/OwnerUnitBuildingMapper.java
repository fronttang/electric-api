package com.rosenzest.electric.mapper;

import java.util.List;

import com.rosenzest.electric.dto.OwnerUnitBuildingQuery;
import com.rosenzest.electric.dto.OwnerUnitBuildingReivewQuery;
import com.rosenzest.electric.dto.ReportDetectStatusDto;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.vo.InitialOwnerUnitBuildingVo;
import com.rosenzest.electric.vo.OwnerUnitBuildingReviewVo;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
public interface OwnerUnitBuildingMapper extends ModelBaseMapper<OwnerUnitBuilding> {

	List<InitialOwnerUnitBuildingVo> queryInitialList(OwnerUnitBuildingQuery query);

	List<OwnerUnitBuildingReviewVo> queryReviewList(OwnerUnitBuildingReivewQuery query);

	ReportDetectStatusDto getBuildingReviewStatus(Long buildingId);

}
