package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType implements IEnum<UserType>  {
	
	
	SYSTEM("00", "平台用户"),
	DETECT_UNIT_ADMIN("01", "检测单位管理员"),
	WORKER("02", "工作人员账号"),
	AREA_USER("03", "街区账号"),
	GRADMAN("04", "网格员"),
	OWNER_UNIT("05", "业主单元账号"),
	;


    final String code;

    final String name;
}
