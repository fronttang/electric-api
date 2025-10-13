package com.rosenzest.electric.formb.handler;

import com.rosenzest.electric.vo.IOwnerUnitDanger;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

public interface IFormbDangerHandler {

	public static final String QUALIFIED = "合格";

	public static final String FAILURE = "不合格";

	/**
	 * 隐患等级
	 */
	String getLevel(IOwnerUnitDanger vo);

	/**
	 * 隐患描述
	 */
	String getDescription(OwnerUnitDangerVo vo);

	/**
	 * 整改建议
	 */
	String getSuggestions(OwnerUnitDangerVo vo);

	/**
	 * 位置
	 */
	String getInfoLocation(OwnerUnitDangerVo vo);

	/**
	 * 结果
	 */
	String getResult(IOwnerUnitDanger vo);
	
	String getPicture(IOwnerUnitDanger vo);
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	default Boolean isImportant(OwnerUnitDangerVo vo) {
		return false;
	}
}
