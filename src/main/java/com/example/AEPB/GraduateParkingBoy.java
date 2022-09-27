package com.example.AEPB;

import java.util.List;
import java.util.Optional;

public class GraduateParkingBoy implements ParkingCar, PickUpCar {
    // parking los
    // parking agent (agent1-> normal agent2 -> smart)
    // pick up agent (agent1 -> normal agent2 -> smart)

    private final List<ParkingLot> parkingLots;

    public GraduateParkingBoy(List<ParkingLot> parkingLots) {
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
        ParkingLot parkingLot = parkingLots.stream()
                .filter(lot -> lot.ticketInTheLot(ticket))
                .findFirst()
                .orElseThrow(() -> new PickUpException("the ticket is invalid"));

        return parkingLot.pickUpCar(ticket);
    }

    private Optional<ParkingLot> pickParkingLot() {
        return parkingLots.stream()
                .filter(ParkingLot::haveCapacity)
                .findFirst();
    }

    private boolean containCar(Car car) {
        return parkingLots.stream()
                .anyMatch(lot -> lot.containCar(car));
    }
}
