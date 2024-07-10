package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rosenzest.base.PageList;
import com.rosenzest.electric.dto.OwnerUnitBuildingQuery;
import com.rosenzest.electric.dto.OwnerUnitBuildingReivewQuery;
import com.rosenzest.electric.dto.ReportDetectStatusDto;
import com.rosenzest.electric.entity.OwnerUnitBuilding;
import com.rosenzest.electric.enums.ReviewStatus;
import com.rosenzest.electric.mapper.OwnerUnitBuildingMapper;
import com.rosenzest.electric.service.IOwnerUnitBuildingService;
import com.rosenzest.electric.vo.InitialOwnerUnitBuildingVo;
import com.rosenzest.electric.vo.OwnerUnitBuildingReviewVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Service
public class OwnerUnitBuildingServiceImpl extends ModelBaseServiceImpl<OwnerUnitBuildingMapper, OwnerUnitBuilding>
		implements IOwnerUnitBuildingService {

	@Override
	public List<InitialOwnerUnitBuildingVo> queryInitialList(OwnerUnitBuildingQuery query, PageList pageList) {

		Page<InitialOwnerUnitBuildingVo> startPage = PageHelper.startPage(pageList.getPageNum(),
				pageList.getPageSize());
		startPage.setReasonable(false);
		List<InitialOwnerUnitBuildingVo> list = this.baseMapper.queryInitialList(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

	@Override
	public List<OwnerUnitBuildingReviewVo> queryReviewList(OwnerUnitBuildingReivewQuery query, PageList pageList) {
		Page<OwnerUnitBuildingReviewVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
		startPage.setReasonable(false);
		List<OwnerUnitBuildingReviewVo> list = this.baseMapper.queryReviewList(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

	@Override
	public boolean updateBuildingReviewStatus(Long buildingId) {
		return this.updateBuildingReviewStatus(buildingId, null);
	}

	@Override
	public boolean updateBuildingReviewStatus(Long buildingId, ReviewStatus status) {
		if (buildingId == null) {
			return false;
		}
		OwnerUnitBuilding building = new OwnerUnitBuilding();
		building.setId(buildingId);
		building.setReviewStatus(getBuildingReviewStatus(buildingId).code());
		if (status != null) {
			building.setReviewStatus(status.code());
		}
		return this.updateById(building);
	}

	@Override
	public ReviewStatus getBuildingReviewStatus(Long buildingId) {

		ReportDetectStatusDto reviewStatus = this.baseMapper.getBuildingReviewStatus(buildingId);
		if (reviewStatus.getDangers() > 0 && reviewStatus.getDangers() == reviewStatus.getFinishs()) {
			return ReviewStatus.FINISH;
		} else if (reviewStatus.getRectifications() > 0) {
			return ReviewStatus.RECTIFIED;
		} else if (reviewStatus.getReexaminations() > 0) {
			return ReviewStatus.RE_EXAMINATION;
		} else {
			return ReviewStatus.RECTIFIED;
		}
	}

}
