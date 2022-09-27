package com.example.AEPB;

import java.util.List;

public interface ParkingCarAgent {
    Ticket parkingCar(Car car, List<ParkingLot> parkingLots);
}
