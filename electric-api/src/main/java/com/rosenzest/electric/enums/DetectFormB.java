package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DetectFormB implements IEnum<DetectFormB> {

	B1("B1", "B1（带电设备红外检测-非变压器）"), BB1("BB1", "B1（带电设备红外检测-变压器）"), B2("B2", "B2（接地电阻值检测）"),
	B3("B3", "B3（变配电装置距可燃物距离检测）"), B4("B4", "B4（照明灯具距可燃物距离检测）"), B5("B5", "B5（开关、插座安装高度检测）"),
	B6("B6", "B6（室内漏电保护开关检测）"), B7("B7", "B7（楼栋总漏保检测）"), B8("B8", "B8（楼层总漏保检测）"), B9("B9", "B9（电气设备对地电压）"),
	B10("B10", "B10（计量电能表规格）"), B11("B11", "B11（室内插座接线）"), B12("B12", "B12（浴室热水器漏电保护开关）"), B13("B13", "B13（绝缘电阻检测）"),
	B14("B14", "B14（电气火灾监控探测器）"),

	;

	final String code;

	final String name;
}
