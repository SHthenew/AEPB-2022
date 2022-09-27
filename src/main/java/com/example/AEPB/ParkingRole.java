package com.example.AEPB;

import com.example.AEPB.PickUpCar.PickUpException;

import java.util.List;
import java.util.Optional;

public abstract class ParkingRole {
    List<ParkingLot> parkingLots;

    public ParkingRole(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    protected abstract Optional<ParkingLot> pickParkingLot();

    public Ticket parkingCar(Car car) {

        if (containCar(car)) {
            throw new ParkingCarException("have duplicated car in parking lot");
        }

        ParkingLot parkingLot = pickParkingLot()
                .orElseThrow(() -> new ParkingCarException("all parking lots is full"));

        return parkingLot.parkingCar(car);
    }

    public Car pickUp(Ticket ticket) {

        ParkingLot parkingLot = parkingLots.stream()
                .filter(lot -> lot.ticketInTheLot(ticket))
                .findFirst()
                .orElseThrow(() -> new PickUpException("the ticket is invalid"));

        return parkingLot.pickUpCar(ticket);
    }

    private boolean containCar(Car car) {
        return parkingLots.stream()
                .anyMatch(lot -> lot.containCar(car));
    }


}
