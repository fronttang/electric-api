package com.rosenzest.electric.miniapp.vo;

import com.rosenzest.electric.entity.OwnerUnitDanger;
import com.rosenzest.electric.formb.FormbDangerHandlerFactory;
import com.rosenzest.electric.formb.handler.IFormbDangerHandler;
import com.rosenzest.electric.vo.IOwnerUnitDanger;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AreaUserDangerVo extends OwnerUnitDanger implements IOwnerUnitDanger {

	private static final long serialVersionUID = 1L;

	/**
	 * 区县
	 */
	private String district;

	/**
	 * 区县
	 */
	private String districtName;

	/**
	 * 街道
	 */
	private String street;

	/**
	 * 街道
	 */
	private String streetName;

	/**
	 * 社区
	 */
	private String community;

	/**
	 * 社区
	 */
	private String communityName;

	/**
	 * 村
	 */
	private String hamlet;

	/**
	 * 村
	 */
	private String hamletName;
	
	public String getLevel() {
		// B表隐患位置处理
		if ("B".equalsIgnoreCase(this.getFormType())) {
			if (StrUtil.isNotBlank(this.getFormCode())) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.getFormCode());
				if (formbDangerHander != null) {
					return formbDangerHander.getLevel(this);
				}
			}
		}

		return this.level;
	}

	public String getStatus() {
		if ("B".equalsIgnoreCase(this.getFormType())) {
			String result = getResult();
			if (IFormbDangerHandler.QUALIFIED.equals(result)) {
				// 非隐患
				return "9";
			} else if (IFormbDangerHandler.FAILURE.equals(result)) {
				return this.status;
			} else {
				return "";
			}
		}
		return this.status;
	}

	public String getResult() {
		if ("B".equalsIgnoreCase(this.getFormType())) {
			if (StrUtil.isNotBlank(this.getFormCode())) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.getFormCode());
				if (formbDangerHander != null) {
					return formbDangerHander.getResult(this);
				}
			}
		}
		return null;
	}

}
