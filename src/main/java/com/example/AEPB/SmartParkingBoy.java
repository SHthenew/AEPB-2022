package com.example.AEPB;

import com.example.AEPB.ParkingCar.ParkingCar;
import com.example.AEPB.ParkingCar.ParkingCarAgent;
import com.example.AEPB.ParkingCar.SmartParkingCarAgent;
import com.example.AEPB.PickUpCar.NormalPickUpCar;
import com.example.AEPB.PickUpCar.PickUpCar;
import com.example.AEPB.PickUpCar.PickUpCarAgent;

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
