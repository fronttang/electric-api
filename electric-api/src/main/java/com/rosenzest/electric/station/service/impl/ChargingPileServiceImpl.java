package com.rosenzest.electric.station.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rosenzest.base.PageList;
import com.rosenzest.electric.station.dto.ChargingPileQuery;
import com.rosenzest.electric.station.entity.ChargingPile;
import com.rosenzest.electric.station.mapper.ChargingPileMapper;
import com.rosenzest.electric.station.service.IChargingPileService;
import com.rosenzest.electric.station.vo.ChargingPileVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-18
 */
@Service
public class ChargingPileServiceImpl extends ModelBaseServiceImpl<ChargingPileMapper, ChargingPile>
		implements IChargingPileService {

	@Override
	public List<ChargingPileVo> queryList(ChargingPileQuery query, PageList pageList) {

		Page<ChargingPileVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
		startPage.setReasonable(false);
		List<ChargingPileVo> list = this.baseMapper.queryList(query);
		pageList.setTotalNum(startPage.getTotal());
		return list;
	}

}
