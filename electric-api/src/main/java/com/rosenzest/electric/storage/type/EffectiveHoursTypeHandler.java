package com.rosenzest.electric.storage.type;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.rosenzest.electric.storage.entity.PhotovoltaicConfig.EffectiveHours;
import com.rosenzest.model.base.type.ListTypeHandler;

public class EffectiveHoursTypeHandler extends ListTypeHandler<EffectiveHours> {

	@Override
	protected TypeReference<List<EffectiveHours>> specificType() {
		return new TypeReference<List<EffectiveHours>>() {
		};
	}

}
