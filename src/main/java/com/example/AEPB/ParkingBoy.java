package com.example.AEPB;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingBoy {

    private List<ParkingLot> parkingLots;

    public ParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots.stream()
                .sorted(Comparator.comparingInt(ParkingLot::getOrder))
                .collect(Collectors.toList());
    }

    public Ticket parkingCar(Car car) {
        ParkingLot parkingLot = parkingLots.stream()
                .filter(ParkingLot::haveCapacity)
                .findFirst()
                .orElseThrow(() -> new ParkingCarException("all parking lots is full"));

        return parkingLot.parkingCar(car);
    }

    public Car pickUp(Ticket ticket) {

        ParkingLot parkingLot = parkingLots.stream()
                .filter(lot -> lot.ticketInTheLot(ticket))
                .findFirst()
                .orElseThrow(() -> new PickUpException(""));

        return parkingLot.pickUpCar(ticket);
    }
}
