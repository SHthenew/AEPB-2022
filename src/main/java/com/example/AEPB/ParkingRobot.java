package com.example.AEPB;

import com.example.AEPB.ParkingCar.ParkingCar;
import com.example.AEPB.ParkingCar.ParkingCarAgent;
import com.example.AEPB.ParkingCar.RobotParkingCarAgent;
import com.example.AEPB.PickUpCar.PickUpException;

import java.util.List;

public class ParkingRobot implements ParkingCar {

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

    public Car pickUp(Ticket ticket) {
        throw new PickUpException("robot can not pick up");
    }
}
