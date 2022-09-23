package com.example.AEPB;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ParkingBoy {
    List<ParkingLot> parkingLots;

    public ParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots.stream()
                .sorted(Comparator.comparingInt(ParkingLot::getOrder))
                .collect(Collectors.toList());
    }

    protected abstract ParkingLot pickParkingLot();

    public Ticket parkingCar(Car car) {

        if (containCar(car)) {
            throw new ParkingCarException("have duplicated car in parking lot");
        }

        ParkingLot parkingLot = pickParkingLot();

        return parkingLot.parkingCar(car);
    }

    public Car pickUp(Ticket ticket) {

        ParkingLot parkingLot = parkingLots.stream()
                .filter(lot -> lot.ticketInTheLot(ticket))
                .findFirst()
                .orElseThrow(() -> new PickUpException(""));

        return parkingLot.pickUpCar(ticket);
    }

    private boolean containCar(Car car) {
        return parkingLots.stream()
                .anyMatch(lot -> lot.containCar(car));
    }


}
