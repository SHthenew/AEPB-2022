package com.example.AEPB;

import java.util.Comparator;
import java.util.List;

public class SmartParkingBoy extends ParkingBoy {
    public SmartParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    protected ParkingLot pickParkingLot() {
        return parkingLots.stream()
                .filter(ParkingLot::haveCapacity)
                .max(Comparator.comparingInt(ParkingLot::remainCapacity))
                .orElseThrow(() -> new ParkingCarException("all parking lots is full"));
    }

}
