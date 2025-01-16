package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB10;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B10)
public class FormB10DangerHandler implements IFormbDangerHandler {

	@Override
	public String getLevel(OwnerUnitDangerVo vo) {
		String level = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			level = "B";
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDangerVo vo) {
		String description = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			description = "计量电能表最大负荷电流小于20A,不满足负荷需求";
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {
		String suggestions = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			suggestions = "更换符合户内用电负荷规格的计量电能表";
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
		FormB10 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, location1);
		}

		return location;
	}

	private FormB10 getFormb(OwnerUnitDangerVo vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB10 formb = data.toJavaObject(FormB10.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(OwnerUnitDangerVo vo) {
		FormB10 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

}
