package com.example.AEPB;

import java.util.List;

public interface PickUpCarAgent {
    public Car pickUp(Ticket ticket, List<ParkingLot> lots);
}
