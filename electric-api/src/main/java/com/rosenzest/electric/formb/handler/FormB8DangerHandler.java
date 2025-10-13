package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB8;
import com.rosenzest.electric.vo.IOwnerUnitDanger;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B8)
public class FormB8DangerHandler implements IFormbDangerHandler {

	// private static String TEST_RESULT_1 = "投入使用且动作";

	private static String TEST_RESULT_2 = "未安装";

	private static String TEST_RESULT_3 = "测试不动作";

	// private static String TEST_RESULT_4 = "抄表到户";

	@Override
	public String getLevel(IOwnerUnitDanger vo) {

		String level = null;
		String result = getResult(vo);
		String testResults = getTestResults(vo);

		// 合格
		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (TEST_RESULT_2.equalsIgnoreCase(testResults)) {
				level = "B";
			} else if (TEST_RESULT_3.equalsIgnoreCase(testResults)) {
				level = "A";
			}
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDangerVo vo) {
		String description = null;
		String result = getResult(vo);
		String testResults = getTestResults(vo);

		if (QUALIFIED.equalsIgnoreCase(result)) {
			description = "楼层已安装总漏电保护开关，且可靠动作，符合规范要求";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (TEST_RESULT_2.equalsIgnoreCase(testResults)) {
				description = "楼层未安装总漏电保护开关，不符合规范要求";
			} else if (TEST_RESULT_3.equalsIgnoreCase(testResults)) {
				description = "楼层总漏电保护开关仪器测试不动作";
			}
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {

		String suggestions = null;
		String result = getResult(vo);
		String testResults = getTestResults(vo);

		// 合格
		if (QUALIFIED.equalsIgnoreCase(result)) {
			suggestions = "";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (TEST_RESULT_2.equalsIgnoreCase(testResults)) {
				suggestions = "楼层应安装总漏电保护开关，各级漏电保护开关的动作电流值与动作时间应协调配合，实现具有动作选择性的分级保护。";
			} else if (TEST_RESULT_3.equalsIgnoreCase(testResults)) {
				suggestions = "更换楼层总漏电保护开关，各级漏电保护开关的动作电流值与动作时间应协调配合，实现具有动作选择性的分级保护。";
			}
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDangerVo vo) {

		String location = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
		return location;
	}

	private FormB8 getFormb(IOwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB8 formb = data.toJavaObject(FormB8.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(IOwnerUnitDanger vo) {
		FormB8 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getTestResults(IOwnerUnitDanger vo) {
		FormB8 formb = getFormb(vo);
		if (formb != null) {
			return formb.getTestResults();
		}
		return null;
	}
	
	@Override
	public String getPicture(IOwnerUnitDanger vo) {
		FormB8 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

}
