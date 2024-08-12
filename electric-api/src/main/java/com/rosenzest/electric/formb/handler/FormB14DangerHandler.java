package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB14;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B14)
public class FormB14DangerHandler implements IFormbDangerHandler {

	@Override
	public String getLevel(OwnerUnitDangerVo vo) {

		String level = null;
		String residualCurrentResult = getResidualCurrentResult(vo);
		if (FAILURE.equalsIgnoreCase(residualCurrentResult)) {
			level = "A";
		}

		String alarmTimeResult = getAlarmTimeResult(vo);
		if (FAILURE.equalsIgnoreCase(alarmTimeResult)) {
			level = "A";
		}

		return level;
	}

	@Override
	public String getDescription(OwnerUnitDangerVo vo) {

		String description = null;
		String residualCurrentResult = getResidualCurrentResult(vo);
		if (FAILURE.equalsIgnoreCase(residualCurrentResult)) {
			description = "电气火灾监控探测器剩余电流报警功能测试时，探测器未在30s内发出报警信号。";
		}

		String alarmTimeResult = getAlarmTimeResult(vo);
		if (FAILURE.equalsIgnoreCase(alarmTimeResult)) {
			description = "电气火灾监控探测器温度报警功能测试时，探测器未在30s内发出报警信号。";
		}

		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {

		String suggestions = null;
		String residualCurrentResult = getResidualCurrentResult(vo);
		if (FAILURE.equalsIgnoreCase(residualCurrentResult)) {
			suggestions = "电气火灾监控探测器剩余电流报警功能测试时，探测器未在30s内发出报警信号。";
		}

		String alarmTimeResult = getAlarmTimeResult(vo);
		if (FAILURE.equalsIgnoreCase(alarmTimeResult)) {
			suggestions = "电气火灾监控探测器温度报警功能测试时，探测器未在30s内发出报警信号。";
		}

		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDangerVo vo) {

		String location = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
		return location;
	}

	private FormB14 getFormb(OwnerUnitDangerVo vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB14 formb = data.toJavaObject(FormB14.class);
				return formb;
			}
		}
		return null;
	}

	private String getResidualCurrentResult(OwnerUnitDangerVo vo) {
		FormB14 formb = getFormb(vo);
		if (formb != null) {
			if (formb.getResidualCurrent() != null) {
				return formb.getResidualCurrent().getResult();
			}
		}
		return null;
	}

	private String getAlarmTimeResult(OwnerUnitDangerVo vo) {
		FormB14 formb = getFormb(vo);
		if (formb != null) {
			if (formb.getAlarmTime() != null) {
				return formb.getAlarmTime().getResult();
			}
		}
		return null;
	}
}
