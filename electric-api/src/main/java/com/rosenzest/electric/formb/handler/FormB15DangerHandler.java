package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB15;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B15)
public class FormB15DangerHandler implements IFormbDangerHandler {

	private static final String DEVICE_NAME_1 = "插座和照明开关";

	private static final String DEVICE_NAME_2 = "空调插座和插头";

	@Override
	public String getLevel(OwnerUnitDangerVo vo) {

		String level = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			level = "A";
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDangerVo vo) {

		String description = null;
		String result = getResult(vo);
		String deviceName = getDeviceName(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			if (DEVICE_NAME_1.equalsIgnoreCase(deviceName)) {
				description = "固定插座处温升超过45K，存在温度缺陷";
			} else if (DEVICE_NAME_2.equalsIgnoreCase(deviceName)) {
				description = "空调电源线插头和插座接触处温升超过60K，存在温度缺陷";
			}
		}

		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {

		String suggestions = null;
		String result = getResult(vo);
		String deviceName = getDeviceName(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			if (DEVICE_NAME_1.equalsIgnoreCase(deviceName)) {
				suggestions = "检查固定插座接线是否接触不良或更换固定插座";
			} else if (DEVICE_NAME_2.equalsIgnoreCase(deviceName)) {
				suggestions = "空调更换电源线插头及插座，插头与插座的接触良好";
			}
		}

		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDangerVo vo) {

		String location = null;
		FormB15 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, location1);
		}

		return location;
	}

	private FormB15 getFormb(OwnerUnitDangerVo vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB15 formb = data.toJavaObject(FormB15.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(OwnerUnitDangerVo vo) {
		FormB15 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getDeviceName(OwnerUnitDangerVo vo) {
		FormB15 formb = getFormb(vo);
		if (formb != null) {
			return formb.getDeviceName();
		}
		return null;
	}

}
