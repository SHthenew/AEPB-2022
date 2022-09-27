package com.example.AEPB;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ParkingRobot implements ParkingCar, PickUpCar {

    private final List<ParkingLot> parkingLots;

    public ParkingRobot(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    @Override
    public Ticket parkingCar(Car car) {
        if (containCar(car)) {
            throw new ParkingCarException("have duplicated car in parking lot");
        }

        ParkingLot parkingLot = pickParkingLot()
                .orElseThrow(() -> new ParkingCarException("all parking lots is full"));

        return parkingLot.parkingCar(car);
    }

    @Override
    public Car pickUp(Ticket ticket) {
        throw new PickUpException("robot can not pick up");
    }

    private Optional<ParkingLot> pickParkingLot() {
        return parkingLots.stream()
                .filter(ParkingLot::haveCapacity)
                .max(Comparator.comparingDouble(ParkingLot::emptyRatio));
    }

    private boolean containCar(Car car) {
        return parkingLots.stream()
                .anyMatch(lot -> lot.containCar(car));
    }
}
