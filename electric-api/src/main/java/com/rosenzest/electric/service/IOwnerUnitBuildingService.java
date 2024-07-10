package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.base.PageList;
import com.rosenzest.electric.dto.OwnerUnitBuildingQuery;
import com.rosenzest.electric.dto.OwnerUnitBuildingReivewQuery;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.vo.InitialOwnerUnitBuildingVo;
import com.rosenzest.electric.vo.OwnerUnitBuildingReviewVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
public interface IOwnerUnitBuildingService extends IModelBaseService<OwnerUnitBuilding> {

	List<InitialOwnerUnitBuildingVo> queryInitialList(OwnerUnitBuildingQuery query, PageList pageList);

	List<OwnerUnitBuildingReviewVo> queryReviewList(OwnerUnitBuildingReivewQuery query, PageList pageList);

	boolean updateBuildingReviewStatus(Long buildingId, ReviewStatus status);

	boolean updateBuildingReviewStatus(Long buildingId);

	ReviewStatus getBuildingReviewStatus(Long buildingId);

}
