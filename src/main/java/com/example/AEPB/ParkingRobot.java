package com.example.AEPB;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ParkingRobot extends ParkingRole {

    public ParkingRobot(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    protected Optional<ParkingLot> pickParkingLot() {
        return parkingLots.stream()
                .filter(ParkingLot::haveCapacity)
                .max(Comparator.comparingDouble(ParkingLot::emptyRatio));
    }

    @Override
    public Car pickUp(Ticket ticket) {
        throw new PickUpException("robot can not pick up");
    }
}
