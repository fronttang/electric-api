package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB6;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B6)
public class FormB6DangerHandler implements IFormbDangerHandler {

	private static String ACTION_FILL = "填写";

	private static String ACTION_1000 = ">1000";

	private static String ACTION_NULL = "不动作";

	// private static String MANUALTEST_ACTION = "手动动作";

	private static String MANUALTEST_NO_ACTION = "手动不动作";

	private static String OTHER_UN = "未安装";

	@Override
	public String getLevel(OwnerUnitDangerVo vo) {
		String level = null;
		String action = getAction(vo);
		String result = getResult(vo);
		String manualTest = getManualTest(vo);
		String other = getOther(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (ACTION_FILL.equalsIgnoreCase(action) || ACTION_1000.equalsIgnoreCase(action)
					|| ACTION_NULL.equalsIgnoreCase(action)) {
				// 填写/>1000/不动作
				level = "A";
			} else if (OTHER_UN.equalsIgnoreCase(other)) {
				// 未安装
				level = "B";
			} else if (MANUALTEST_NO_ACTION.equalsIgnoreCase(manualTest)) {
				// 手动不动作
				level = "A";
			}
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDangerVo vo) {
		String description = null;
		String action = getAction(vo);
		String result = getResult(vo);
		String manualTest = getManualTest(vo);
		String other = getOther(vo);

		if (QUALIFIED.equalsIgnoreCase(result)) {

			if (ACTION_FILL.equalsIgnoreCase(action)) {
				// 填写
				description = "剩余电流动作保护装置检测结果，符合要求。";
			}
		} else if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (ACTION_FILL.equalsIgnoreCase(action) || ACTION_1000.equalsIgnoreCase(action)
					|| ACTION_NULL.equalsIgnoreCase(action)) {
				// 填写/>1000/不动作
				description = "漏电保护开关动作时间大于100ms，不符合要求；";
			} else if (OTHER_UN.equalsIgnoreCase(other)) {
				// 未安装
				description = "未安装额定动作电流30mA漏电保护开关";
			} else if (MANUALTEST_NO_ACTION.equalsIgnoreCase(manualTest)) {
				// 手动不动作
				description = "漏电保护开关手动测试不动作";
			}
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {
		String suggestions = null;
		String action = getAction(vo);
		String result = getResult(vo);
		String manualTest = getManualTest(vo);
		String other = getOther(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (ACTION_FILL.equalsIgnoreCase(action) || ACTION_1000.equalsIgnoreCase(action)
					|| ACTION_NULL.equalsIgnoreCase(action)) {
				// 填写/>1000/不动作
				suggestions = "更换新的漏电保护开关且动作时间不大于100ms";
			} else if (OTHER_UN.equalsIgnoreCase(other)) {
				// 未安装
				suggestions = "室内应安装额定动作电流为30mA漏电保护开关";
			} else if (MANUALTEST_NO_ACTION.equalsIgnoreCase(manualTest)) {
				// 手动不动作
				suggestions = "更换与线路用电负荷相匹配且动作可靠的漏电保护开关";
			}
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

//		String location = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
//		return location;
//		
		String location = null;
		FormB6 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, location1);
		}

		return location;
	}

	private FormB6 getFormb(OwnerUnitDangerVo vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB6 formb = data.toJavaObject(FormB6.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(OwnerUnitDangerVo vo) {
		FormB6 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getAction(OwnerUnitDangerVo vo) {
		FormB6 formb = getFormb(vo);
		if (formb != null) {
			return formb.getAction();
		}
		return null;
	}

	private String getManualTest(OwnerUnitDangerVo vo) {
		FormB6 formb = getFormb(vo);
		if (formb != null) {
			return formb.getManualTest();
		}
		return null;
	}

	private String getOther(OwnerUnitDangerVo vo) {
		FormB6 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOther();
		}
		return null;
	}
	
	@Override
	public String getPicture(OwnerUnitDangerVo vo) {
		FormB6 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}
}
