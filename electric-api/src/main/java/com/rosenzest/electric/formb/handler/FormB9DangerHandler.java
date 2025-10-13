package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB9;
import com.rosenzest.electric.vo.IOwnerUnitDanger;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B9)
public class FormB9DangerHandler implements IFormbDangerHandler {

	@Override
	public String getLevel(IOwnerUnitDanger vo) {
		String level = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			level = "A";
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDangerVo vo) {
		String description = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			description = StrUtil.format("金属外壳对地电压{}V，存在危险电压，不符合要求", getVoltage(vo));
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {
		String suggestions = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			suggestions = "检查故障点，消除危险电压。";
		}
		return suggestions;
	}
	
	@Override
	public Boolean isImportant(OwnerUnitDangerVo vo) {
		Boolean important = false;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			important = true;
		}
		return important;
	}

	@Override
	public String getInfoLocation(OwnerUnitDangerVo vo) {

		String location = null;
		FormB9 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, location1);
		}

		return location;
	}

	private FormB9 getFormb(IOwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB9 formb = data.toJavaObject(FormB9.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(IOwnerUnitDanger vo) {
		FormB9 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getVoltage(OwnerUnitDangerVo vo) {
		FormB9 formb = getFormb(vo);
		if (formb != null) {
			return formb.getVoltage();
		}
		return null;
	}
	
	@Override
	public String getPicture(IOwnerUnitDanger vo) {
		FormB9 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

}
