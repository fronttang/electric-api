package com.rosenzest.electric.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rosenzest.base.PageList;
import com.rosenzest.electric.dto.OwnerUnitQueryDto;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.mapper.OwnerUnitMapper;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 业主单元 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
@Service
public class OwnerUnitServiceImpl extends ModelBaseServiceImpl<OwnerUnitMapper, OwnerUnit> implements IOwnerUnitService {

	@Override
	public List<InitialOwnerUnitVo> queryInitialList(OwnerUnitQueryDto query, PageList pageList) {
		
		Page<InitialOwnerUnitVo> startPage = PageHelper.startPage(pageList.getPageNum(), pageList.getPageSize());
        List<InitialOwnerUnitVo> list = this.baseMapper.queryInitialList(query);
        pageList.setTotalNum(startPage.getTotal());
        return list;
	}

}
