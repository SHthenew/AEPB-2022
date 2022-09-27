package com.example.AEPB.ParkingCar;

import com.example.AEPB.Car;
import com.example.AEPB.ParkingLot;
import com.example.AEPB.Ticket;

import java.util.List;

public interface ParkingCarAgent {
    Ticket parkingCar(Car car, List<ParkingLot> parkingLots);
}
