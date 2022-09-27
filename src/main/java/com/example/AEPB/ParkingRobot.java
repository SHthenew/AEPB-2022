package com.example.AEPB;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ParkingRobot extends ParkingBoy {

    public ParkingRobot(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    protected Optional<ParkingLot> pickParkingLot() {
        return parkingLots.stream()
                .filter(ParkingLot::haveCapacity)
                .max(Comparator.comparing(ParkingLot::emptyRatio));
    }
}
