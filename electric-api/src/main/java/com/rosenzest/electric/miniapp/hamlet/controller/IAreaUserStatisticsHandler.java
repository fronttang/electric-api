package com.rosenzest.electric.miniapp.hamlet.controller;

import java.util.List;

import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.electric.miniapp.vo.OwnerUnitDangerStatisticsVo;

public interface IAreaUserStatisticsHandler {

	/**
	 * 数据统计
	 * 
	 * @param userInfo
	 * @return
	 */
	List<OwnerUnitDangerStatisticsVo> statistics(AreaUserInfoVo userInfo);
}
