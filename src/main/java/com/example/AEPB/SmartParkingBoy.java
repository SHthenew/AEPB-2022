package com.example.AEPB;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SmartParkingBoy {
    private List<ParkingLot> parkingLots;

    public SmartParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots.stream()
                .sorted(Comparator.comparingInt(ParkingLot::getOrder))
                .collect(Collectors.toList());
    }


    public Ticket parkingCar(Car car) {

        if (containCar(car)) {
            throw new ParkingCarException("have duplicated car in parking lot");
        }

        ParkingLot parkingLot = parkingLots.stream()
                .filter(ParkingLot::haveCapacity)
                .max(Comparator.comparingInt(ParkingLot::remainCapacity))
                .orElseThrow(() -> new ParkingCarException("all parking lots is full"));

        return parkingLot.parkingCar(car);
    }

    private boolean containCar(Car car) {
        return parkingLots.stream()
                .anyMatch(lot -> lot.containCar(car));
    }
}
