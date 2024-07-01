package com.rosenzest.electric.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量
 */
public class ElectricConstant {
	
	public static final List<String> BUSINESS_DICT_TYPES = new ArrayList<String>();
	
	static {
		// 项目类型
		BUSINESS_DICT_TYPES.add("project_type");
		// 仪器类型
		BUSINESS_DICT_TYPES.add("detect_device_type");
		// 检测表标题类型
		BUSINESS_DICT_TYPES.add("intuitive_detect_type");
		// 建筑使用性质
		BUSINESS_DICT_TYPES.add("building_nature");
		// 检测内容
		BUSINESS_DICT_TYPES.add("detect_content");
		// 检测模块
		BUSINESS_DICT_TYPES.add("detect_module");
		// 物业类型
		BUSINESS_DICT_TYPES.add("property_type");
		// 充电站类型
		BUSINESS_DICT_TYPES.add("charging_station_type");
		// 业主单元类型
		BUSINESS_DICT_TYPES.add("high_risk_type");
		// 账号类型
		BUSINESS_DICT_TYPES.add("user_type");
		// 三小场所单位类型
		BUSINESS_DICT_TYPES.add("small_unit_type");
		// 工业企业单位类型
		BUSINESS_DICT_TYPES.add("industrial_unit_type");
		// 公共场所单位类型
		BUSINESS_DICT_TYPES.add("public_places_unit_type");
		// 直观检测表内容类型
		BUSINESS_DICT_TYPES.add("intuitive_detect_data_type");
		// 隐患等级
		BUSINESS_DICT_TYPES.add("hazard_level");
		// 累积方式
		BUSINESS_DICT_TYPES.add("accumulation_method");
		// 模块类型
		BUSINESS_DICT_TYPES.add("highfirerisk_template_module_type");
		//项目工作人员绑定类型
		BUSINESS_DICT_TYPES.add("project_worker_bind_type");
		// 初检状态
		BUSINESS_DICT_TYPES.add("initial_test_status");
		// 复检状态
		BUSINESS_DICT_TYPES.add("again_test_status");
	}
	
	
	
}
