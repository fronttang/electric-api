package com.rosenzest.electric.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import cn.hutool.core.util.NumberUtil;

public class BigDecimalUtil extends NumberUtil {

	public static String roundHalfUp(BigDecimal value, int scale) {
		return round(value, scale, RoundingMode.HALF_UP).toPlainString();
	}

	public static BigDecimal sqrt(BigDecimal value, int scale) {
		BigDecimal num2 = BigDecimal.valueOf(2);
		int precision = 100;
		MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
		BigDecimal deviation = value;
		int cnt = 0;
		while (cnt < precision) {
			deviation = (deviation.add(value.divide(deviation, mc))).divide(num2, mc);
			cnt++;
		}
		deviation = deviation.setScale(scale, RoundingMode.HALF_EVEN);
		return deviation;
	}
}
