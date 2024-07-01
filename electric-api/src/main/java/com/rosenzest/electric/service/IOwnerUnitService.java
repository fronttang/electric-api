package com.rosenzest.electric.service;

import java.util.List;

import com.rosenzest.base.PageList;
import com.rosenzest.electric.dto.OwnerUnitQueryDto;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 业主单元 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-28
 */
public interface IOwnerUnitService extends IModelBaseService<OwnerUnit> {

	List<InitialOwnerUnitVo> queryInitialList(OwnerUnitQueryDto query, PageList pageList);

}
