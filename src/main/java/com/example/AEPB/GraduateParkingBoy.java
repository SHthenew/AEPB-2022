package com.example.AEPB;

import java.util.List;
import java.util.Optional;

public class GraduateParkingBoy extends ParkingRole {

    public GraduateParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    protected Optional<ParkingLot> pickParkingLot() {
        return parkingLots.stream()
                .filter(ParkingLot::haveCapacity)
                .findFirst();
    }
}
