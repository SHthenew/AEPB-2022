package com.example.AEPB.ParkingCar;

import com.example.AEPB.Car;
import com.example.AEPB.ParkingCarException;
import com.example.AEPB.ParkingLot;
import com.example.AEPB.Ticket;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class GraduateParkingCarAgent implements ParkingCarAgent {


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
                .findFirst();
    }

    private boolean containCar(List<ParkingLot> parkingLots, Car car) {
        return parkingLots.stream()
                .anyMatch(lot -> lot.containCar(car));
    }
}
