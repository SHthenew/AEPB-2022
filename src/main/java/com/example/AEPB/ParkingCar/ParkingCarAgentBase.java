package com.example.AEPB.ParkingCar;

import com.example.AEPB.Car;
import com.example.AEPB.Exception.ParkingCarException;
import com.example.AEPB.ParkingLot;
import com.example.AEPB.Ticket;

import java.util.List;
import java.util.Optional;

public abstract class ParkingCarAgentBase {
    protected abstract Optional<ParkingLot> pickParkingLot(List<ParkingLot> parkingLots);

    protected Ticket parkingCarBase(Car car, List<ParkingLot> lots) {
        if (containCar(lots, car)) {
            throw new ParkingCarException("have duplicated car in parking lot");
        }

        ParkingLot parkingLot = pickParkingLot(lots)
                .orElseThrow(() -> new ParkingCarException("all parking lots is full"));

        return parkingLot.parkingCar(car);
    }

    private boolean containCar(List<ParkingLot> parkingLots, Car car) {
        return parkingLots.stream()
                .anyMatch(lot -> lot.containCar(car));
    }
}
