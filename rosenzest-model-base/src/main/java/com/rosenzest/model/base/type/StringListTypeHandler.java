package com.rosenzest.model.base.type;

import java.util.List;

import com.alibaba.fastjson.TypeReference;

public class StringListTypeHandler extends ListTypeHandler<String> {

	@Override
	protected TypeReference<List<String>> specificType() {
		return new TypeReference<List<String>>() {
		};
	}

}
