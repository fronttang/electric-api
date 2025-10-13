package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB5;
import com.rosenzest.electric.vo.IOwnerUnitDanger;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B5)
public class FormB5DangerHandler implements IFormbDangerHandler {

	private static final String VENUE_TYPE_1 = "浴室";

	private static final String VENUE_TYPE_OTHER = "其他";

	@Override
	public String getLevel(IOwnerUnitDanger vo) {

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

		String venueType = getVenueType(vo);

		if (VENUE_TYPE_1.equalsIgnoreCase(venueType)) {
			if (QUALIFIED.equalsIgnoreCase(result)) {
				description = "安装高度检测结果，符合要求；";
			} else if (FAILURE.equalsIgnoreCase(result)) {
				description = "等潮湿场所插座安装高度低于1.5m，不符合要求。";
			}
		} else if (VENUE_TYPE_OTHER.equalsIgnoreCase(venueType)) {
			description = "";
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {

		String suggestions = "";
		String result = getResult(vo);
		String venueType = getVenueType(vo);
		if (VENUE_TYPE_1.equalsIgnoreCase(venueType)) {
			if (QUALIFIED.equalsIgnoreCase(result)) {
				suggestions = "/";
			} else if (FAILURE.equalsIgnoreCase(result)) {
				suggestions = "等潮湿场所插座应采用密封型并带保护接地线触头的保护型插座，安装高度不低于1.5m";
			}
		} else if (VENUE_TYPE_OTHER.equalsIgnoreCase(venueType)) {
			suggestions = "";
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDangerVo vo) {

		String location = null;
		FormB5 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";
			String deviceName = StrUtil.isNotBlank(formb.getDeviceName()) ? formb.getDeviceName() : "";

			location = StrUtil.format("{}{}{}{}", buildingName, unitAreaName, location1, deviceName);
		}

		return location;
	}

	private FormB5 getFormb(IOwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB5 formb = data.toJavaObject(FormB5.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(IOwnerUnitDanger vo) {
		FormB5 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getVenueType(OwnerUnitDangerVo vo) {
		FormB5 formb = getFormb(vo);
		if (formb != null) {
			return formb.getVenueType();
		}
		return null;
	}
	
	@Override
	public String getPicture(IOwnerUnitDanger vo) {
		FormB5 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

}
