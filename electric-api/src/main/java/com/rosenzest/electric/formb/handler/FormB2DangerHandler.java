package com.rosenzest.electric.formb.handler;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.FormbDangerHandler;
import com.rosenzest.electric.formb.dto.FormB2;
import com.rosenzest.electric.vo.IOwnerUnitDanger;
import com.rosenzest.electric.vo.OwnerUnitDangerVo;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler(DetectFormB.B2)
public class FormB2DangerHandler implements IFormbDangerHandler {

	/**
	 * 楼栋
	 */
	public static final String DEVICE_TYPE_BUILDING = "楼栋";

	/**
	 * 变配电装置
	 */
	public static final String DEVICE_TYPE = "变配电装置";

	/**
	 * TN-C系统
	 */
	public static final String SYSTEM_TN_C = "TN-C系统";

	/**
	 * TN-S
	 */
	public static final String SYSTEM_TN_S = "TN-S";

	/**
	 * TN-C-S
	 */
	public static final String SYSTEM_TN_C_S = "TN-C-S";

	/**
	 * 无测试地点
	 */
	public static final String SYSTEM_NULL = "无测试地点";

	@Override
	public String getLevel(IOwnerUnitDanger vo) {

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

		String deviceType = getDeviceType(vo);
		if (DEVICE_TYPE_BUILDING.equalsIgnoreCase(deviceType)) {
			String system = getGroundingSystem(vo);

			if (SYSTEM_TN_C.equalsIgnoreCase(system)) {
				description = "楼栋使用TN-C系统，不符合要求；";
			} else if (SYSTEM_TN_S.equalsIgnoreCase(system) || SYSTEM_TN_C_S.equalsIgnoreCase(system)) {
				if (QUALIFIED.equalsIgnoreCase(result)) {
					description = StrUtil.format("楼栋接地系统接地电阻为{}Ω，符合规范要求", getResistance(vo));
				} else if (FAILURE.equalsIgnoreCase(result)) {
					description = StrUtil.format("楼栋接地系统接地电阻为{}Ω，不符合规范要求", getResistance(vo));
				}
			} else if (SYSTEM_NULL.equalsIgnoreCase(system)) {
				description = "楼栋接地系统未按要求设置测试点，不符合规范要求；";
			}
		} else if (DEVICE_TYPE.equalsIgnoreCase(deviceType)) {
			String system = getGroundingSystem(vo);

			if (SYSTEM_TN_S.equalsIgnoreCase(system)) {
				if (QUALIFIED.equalsIgnoreCase(result)) {
					description = StrUtil.format("配电房变压器接地系统接地电阻为{}Ω，符合规范要求", getResistance(vo));
				} else if (FAILURE.equalsIgnoreCase(result)) {
					description = StrUtil.format("配电房变压器接地系统接地电阻为{}Ω，不符合规范要求", getResistance(vo));
				}
			}
		}

		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDangerVo vo) {
		String suggestions = null;
		String result = getResult(vo);

		String deviceType = getDeviceType(vo);
		if (DEVICE_TYPE_BUILDING.equalsIgnoreCase(deviceType)) {
			String system = getGroundingSystem(vo);

			if (SYSTEM_TN_C.equalsIgnoreCase(system)) {
				suggestions = "按照规范设置接地体，接地电阻不宜超过10Ω";
			} else if (SYSTEM_TN_S.equalsIgnoreCase(system) || SYSTEM_TN_C_S.equalsIgnoreCase(system)) {
				if (QUALIFIED.equalsIgnoreCase(result)) {
					suggestions = "";
				} else if (FAILURE.equalsIgnoreCase(result)) {
					suggestions = "重新规范安装接地体，接地电阻不宜超过10Ω";
				}
			} else if (SYSTEM_NULL.equalsIgnoreCase(system)) {
				suggestions = "接地装置在地面以上的部分，应按设计要求设置测试点，测试点不应被外墙饰面遮蔽，且应有明显标识";
			}
		} else if (DEVICE_TYPE.equalsIgnoreCase(deviceType)) {
			String system = getGroundingSystem(vo);

			if (SYSTEM_TN_S.equalsIgnoreCase(system)) {
				if (QUALIFIED.equalsIgnoreCase(result)) {
					suggestions = "";
				} else if (FAILURE.equalsIgnoreCase(result)) {
					suggestions = "重新规范安装接地体，接地电阻不宜超过10Ω";
				}
			}
		}

		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDangerVo vo) {

		String location = null;
		FormB2 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getUnitAreaName()) ? vo.getUnitAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, location1);
		}

		return location;
	}

	private FormB2 getFormb(IOwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB2 formb = data.toJavaObject(FormB2.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(IOwnerUnitDanger vo) {
		FormB2 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getDeviceType(OwnerUnitDangerVo vo) {
		FormB2 formb = getFormb(vo);
		if (formb != null) {
			return formb.getDeviceType();
		}
		return null;
	}

	private String getGroundingSystem(OwnerUnitDangerVo vo) {
		FormB2 formb = getFormb(vo);
		if (formb != null) {
			return formb.getGroundingSystem();
		}
		return null;
	}

	private String getResistance(OwnerUnitDangerVo vo) {
		FormB2 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResistance();
		}
		return null;
	}
	
	@Override
	public String getPicture(IOwnerUnitDanger vo) {
		FormB2 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

}
