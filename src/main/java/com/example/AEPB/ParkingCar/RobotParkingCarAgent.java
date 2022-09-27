package com.example.AEPB.ParkingCar;

import com.example.AEPB.Car;
import com.example.AEPB.ParkingLot;
import com.example.AEPB.Ticket;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RobotParkingCarAgent extends ParkingCarAgentBase implements ParkingCarAgent {
    @Override
    public Ticket parkingCar(Car car, List<ParkingLot> lots) {
        return parkingCarBase(car, lots);
    }

    protected Optional<ParkingLot> pickParkingLot(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                .filter(ParkingLot::haveCapacity)
                .max(Comparator.comparingDouble(ParkingLot::emptyRatio));
    }
}
