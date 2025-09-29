package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB1;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B1)
public class FormB1DangerHandler implements IFormbDangerHandler {

	@Override
	public String getLevel(OwnerUnitDangerVo vo) {

		String level = null;
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			level = "";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			level = "A";
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDangerVo vo) {

		String description = null;
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			description = "带电设备红外温度、电流、电压检测结果，符合要求；";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			description = "带电设备红外检测存在热缺陷，不符合要求；";
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
			suggestions = "更换开关或检查开关接线端子是否有接触不良现象。";
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDangerVo vo) {

		String location = null;
		FormB1 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String deviceName = StrUtil.isNotBlank(formb.getDeviceName()) ? formb.getDeviceName() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, deviceName);
		}

		return location;
	}

	private FormB1 getFormb(OwnerUnitDangerVo vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB1 formb = data.toJavaObject(FormB1.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(OwnerUnitDangerVo vo) {
		FormB1 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
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
	public String getPicture(OwnerUnitDangerVo vo) {
		FormB1 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}
}
