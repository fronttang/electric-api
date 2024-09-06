package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB4;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B4)
public class FormB4DangerHandler implements IFormbDangerHandler {

	@Override
	public String getLevel(OwnerUnitDangerVo vo) {

		String level = null;
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			level = "";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			level = "B";
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDangerVo vo) {

		String description = null;
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			description = "距可燃物距离检测结果，符合要求；";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			description = "距可燃物距离检测，距离小于0.3m，不符合要求；";
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {

		String suggestions = null;
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			suggestions = "/";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			suggestions = "清除周围堆放的可燃物";
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDangerVo vo) {

		String location = null;
		FormB4 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";
			String deviceName = StrUtil.isNotBlank(formb.getDeviceName()) ? formb.getDeviceName() : "";

			location = StrUtil.format("{}{}{}{}", buildingName, unitAreaName, location1, deviceName);
		}

		return location;
	}

	private FormB4 getFormb(OwnerUnitDangerVo vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB4 formb = data.toJavaObject(FormB4.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(OwnerUnitDangerVo vo) {
		FormB4 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

}
