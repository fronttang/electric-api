package com.rosenzest.base.constant;

import cn.hutool.core.lang.RegexPool;

/**
 * 正则表达式常量
 * 
 * @author fronttang
 * @date 2021/07/26
 */
public interface RegExpConstants extends RegexPool {

    /**
     * 身份证号码(15位+18位)
     */
    String IDCARD =
        "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$";

    /**
     * 车牌号码
     */
    String CAR_NO =
        "^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$";

    /**
     * 图片格式
     */
    String IMAGE = ".(png|jpg|gif|jpeg|webp)$";
}
