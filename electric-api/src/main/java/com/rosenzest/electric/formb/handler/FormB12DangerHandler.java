package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB12;
import com.rosenzest.electric.vo.IOwnerUnitDanger;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B12)
public class FormB12DangerHandler implements IFormbDangerHandler {

	private static String OTHER_1 = "未安装";

	private static String OTHER_2 = "测试不动作";

	@Override
	public String getLevel(IOwnerUnitDanger vo) {
		String level = null;
		String result = getResult(vo);
		String other = getOther(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (OTHER_1.equalsIgnoreCase(other)) {
				level = "B";
			} else if (OTHER_2.equalsIgnoreCase(other)) {
				level = "A";
			}
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDangerVo vo) {
		String description = null;
		String result = getResult(vo);
		String other = getOther(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (OTHER_1.equalsIgnoreCase(other)) {
				description = "热水器电源回路未安装额定剩余动作电流为10mA的漏电保护开关";
			} else if (OTHER_2.equalsIgnoreCase(other)) {
				description = "漏电保护开关测试不动作";
			}
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {
		String suggestions = null;
		String result = getResult(vo);
		String other = getOther(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (OTHER_1.equalsIgnoreCase(other)) {
				suggestions = "浴室热水器电源回路加装额定剩余动作电流为10mA的漏电保护开关";
			} else if (OTHER_2.equalsIgnoreCase(other)) {
				suggestions = "更换规格相同的漏电保护开关";
			}
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDangerVo vo) {

		String location = null;
		FormB12 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, location1);
		}

		return location;
	}

	private FormB12 getFormb(IOwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB12 formb = data.toJavaObject(FormB12.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(IOwnerUnitDanger vo) {
		FormB12 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getOther(IOwnerUnitDanger vo) {
		FormB12 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOther();
		}
		return null;
	}
	
	@Override
	public String getPicture(IOwnerUnitDanger vo) {
		FormB12 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

}
