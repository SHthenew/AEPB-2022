package com.example.AEPB;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RobotParkingCarAgent implements ParkingCarAgent {
    @Override
    public Ticket parkingCar(Car car, List<ParkingLot> lots) {
        if (containCar(lots, car)) {
            throw new ParkingCarException("have duplicated car in parking lot");
        }

        ParkingLot parkingLot = pickParkingLot(lots)
                .orElseThrow(() -> new ParkingCarException("all parking lots is full"));

        return parkingLot.parkingCar(car);
    }

    private Optional<ParkingLot> pickParkingLot(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                .filter(ParkingLot::haveCapacity)
                .max(Comparator.comparingDouble(ParkingLot::emptyRatio));
    }

    private boolean containCar(List<ParkingLot> parkingLots, Car car) {
        return parkingLots.stream()
                .anyMatch(lot -> lot.containCar(car));
    }
}
