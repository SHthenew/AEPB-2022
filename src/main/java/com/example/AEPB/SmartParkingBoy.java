package com.example.AEPB;

import java.util.List;

public class SmartParkingBoy implements ParkingCar, PickUpCar {
    private final List<ParkingLot> parkingLots;

    private final ParkingCarAgent parkingCarAgent;
    private final PickUpCarAgent pickUpCarAgent;

    public SmartParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        this.parkingCarAgent = new SmartParkingCarAgent();
        this.pickUpCarAgent = new NormalPickUpCar();
    }

    @Override
    public Ticket parkingCar(Car car) {
        return parkingCarAgent.parkingCar(car, parkingLots);
    }

    @Override
    public Car pickUp(Ticket ticket) {
        return pickUpCarAgent.pickUp(ticket, parkingLots);
    }
}
