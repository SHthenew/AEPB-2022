package com.example.AEPB;

import com.example.AEPB.ParkingCar.ParkingCar;
import com.example.AEPB.ParkingCar.ParkingCarAgent;
import com.example.AEPB.PickUpCar.PickUpCar;
import com.example.AEPB.PickUpCar.PickUpCarAgent;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DynamicParking implements ParkingCar, PickUpCar {

    private final List<ParkingLot> parkingLots;
    private final ParkingCarAgent parkingCarAgent;
    private final PickUpCarAgent pickUpCarAgent;

    @Override
    public Ticket parkingCar(Car car) {
        return parkingCarAgent.parkingCar(car, parkingLots);
    }

    @Override
    public Car pickUp(Ticket ticket) {
        return pickUpCarAgent.pickUp(ticket, parkingLots);
    }
}
