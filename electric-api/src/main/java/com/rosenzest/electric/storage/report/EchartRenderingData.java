package com.rosenzest.electric.storage.report;

import java.util.List;

import lombok.Data;

@Data
public class EchartRenderingData {

	private Boolean base64 = true;

	private String chartType = "option1";

	private List<EnergyStorageEchartsData> data;
}
