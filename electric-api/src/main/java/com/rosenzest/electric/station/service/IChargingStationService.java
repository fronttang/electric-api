package com.rosenzest.electric.station.service;

import com.rosenzest.electric.station.dto.ChargingStationDto;

public interface IChargingStationService {

	boolean saveChargingStation(ChargingStationDto data);
}
