package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB13;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B13)
public class FormB13DangerHandler implements IFormbDangerHandler {

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
		if (FAILURE.equalsIgnoreCase(result)) {
			description = "线路绝缘电阻＜0.5MΩ，不满足规范要求。";
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {

		String suggestions = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			suggestions = "更换绝缘电阻不合格的线路";
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDangerVo vo) {

		String location = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
		return location;
	}

	private FormB13 getFormb(OwnerUnitDangerVo vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB13 formb = data.toJavaObject(FormB13.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(OwnerUnitDangerVo vo) {
		FormB13 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	@Override
	public String getPicture(OwnerUnitDangerVo vo) {
		FormB13 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}
}
