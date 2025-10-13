package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB11;
import com.rosenzest.electric.vo.IOwnerUnitDanger;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B11)
public class FormB11DangerHandler implements IFormbDangerHandler {

	// private static String TEST_RESULT_1 = "有接地线";

	private static String TEST_RESULT_2 = "无接地线";

	private static String OTHER_1 = "固定插座火零错位";

	private static String OTHER_2 = "固定插座回路缺零线";

	private static String OTHER_3 = "固定插座相地线错并缺地";

	private static String OTHER_4 = "固定插座无可靠保护接地线";

	@Override
	public String getLevel(IOwnerUnitDanger vo) {

		String level = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			level = "B";
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDangerVo vo) {
		String description = null;
		String result = getResult(vo);
		String other = getOther(vo);
		String testResults = getTestResults(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			if (TEST_RESULT_2.equalsIgnoreCase(testResults)) {
				description = "插座无接地线，不符合规范要求";
			} else if (OTHER_1.equalsIgnoreCase(other)) {
				description = "固定插座火零错位";
			} else if (OTHER_2.equalsIgnoreCase(other)) {
				description = "固定插座回路缺零线";
			} else if (OTHER_3.equalsIgnoreCase(other)) {
				description = "固定插座相地线错并缺地";
			} else if (OTHER_4.equalsIgnoreCase(other)) {
				description = "固定插座无可靠保护接地线";
			}
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {
		String suggestions = null;
		String result = getResult(vo);
		String other = getOther(vo);
		String testResults = getTestResults(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			if (TEST_RESULT_2.equalsIgnoreCase(testResults)) {
				suggestions = "插座回路新增保护接地线";
			} else if (OTHER_1.equalsIgnoreCase(other)) {
				suggestions = "插座按正确接法重新接线，面向插座面板左孔接零线，右孔接火线";
			} else if (OTHER_2.equalsIgnoreCase(other)) {
				suggestions = "检修线路，插座回路新增零线";
			} else if (OTHER_3.equalsIgnoreCase(other)) {
				suggestions = "插座回路新增保护接地线，插座按正确接法重新接线，面对插座左孔接零线，右孔接火线，上孔接地线";
			} else if (OTHER_4.equalsIgnoreCase(other)) {
				suggestions = "插座新增可靠保护接地线";
			}
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDangerVo vo) {

		String location = null;
		FormB11 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, location1);
		}

		return location;
	}

	private FormB11 getFormb(IOwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB11 formb = data.toJavaObject(FormB11.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(IOwnerUnitDanger vo) {
		FormB11 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}
	
	@Override
	public String getPicture(IOwnerUnitDanger vo) {
		FormB11 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

	private String getTestResults(OwnerUnitDangerVo vo) {
		FormB11 formb = getFormb(vo);
		if (formb != null) {
			return formb.getTestResults();
		}
		return null;
	}

	private String getOther(IOwnerUnitDanger vo) {
		FormB11 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOther();
		}
		return null;
	}
}
