package com.example.AEPB;

import java.util.List;

public class ParkingRobot implements ParkingCar, PickUpCar {

    private final List<ParkingLot> parkingLots;

    private final ParkingCarAgent parkingCarAgent;

    public ParkingRobot(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        this.parkingCarAgent = new RobotParkingCarAgent();
    }

    @Override
    public Ticket parkingCar(Car car) {
        return parkingCarAgent.parkingCar(car, parkingLots);
    }

    @Override
    public Car pickUp(Ticket ticket) {
        throw new PickUpException("robot can not pick up");
    }
}
