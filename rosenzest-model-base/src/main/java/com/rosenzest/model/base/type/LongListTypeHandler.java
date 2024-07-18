package com.rosenzest.model.base.type;

import java.util.List;

import com.alibaba.fastjson.TypeReference;

public class LongListTypeHandler extends ListTypeHandler<Long> {

	@Override
	protected TypeReference<List<Long>> specificType() {
		return new TypeReference<List<Long>>() {
		};
	}

}
