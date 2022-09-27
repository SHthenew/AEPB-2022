package com.example.AEPB.PickUpCar;

import com.example.AEPB.Car;
import com.example.AEPB.ParkingLot;
import com.example.AEPB.Ticket;

import java.util.List;

public interface PickUpCarAgent {
    public Car pickUp(Ticket ticket, List<ParkingLot> lots);
}
