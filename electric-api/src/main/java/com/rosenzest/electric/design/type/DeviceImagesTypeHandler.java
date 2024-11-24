package com.rosenzest.electric.design.type;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.rosenzest.electric.design.dto.ElectricityProjectDeviceImages;
import com.rosenzest.model.base.type.ListTypeHandler;

public class DeviceImagesTypeHandler extends ListTypeHandler<ElectricityProjectDeviceImages> {

	@Override
	protected TypeReference<List<ElectricityProjectDeviceImages>> specificType() {
		return new TypeReference<List<ElectricityProjectDeviceImages>>() {
		};
	}

}
