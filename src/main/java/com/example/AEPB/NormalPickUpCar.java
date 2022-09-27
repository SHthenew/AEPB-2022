package com.example.AEPB;

import java.util.List;

public class NormalPickUpCar implements PickUpCarAgent {

    @Override
    public Car pickUp(Ticket ticket, List<ParkingLot> parkingLots) {
        ParkingLot parkingLot = parkingLots.stream()
                .filter(lot -> lot.ticketInTheLot(ticket))
                .findFirst()
                .orElseThrow(() -> new PickUpException("the ticket is invalid"));

        return parkingLot.pickUpCar(ticket);
    }
}
