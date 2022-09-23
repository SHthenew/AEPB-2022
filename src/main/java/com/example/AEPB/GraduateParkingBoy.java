package com.example.AEPB;

import java.util.List;

public class GraduateParkingBoy extends ParkingBoy {

    public GraduateParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    protected ParkingLot pickParkingLot() {
        return parkingLots.stream()
                .filter(ParkingLot::haveCapacity)
                .findFirst()
                .orElseThrow(() -> new ParkingCarException("all parking lots is full"));
    }
}
