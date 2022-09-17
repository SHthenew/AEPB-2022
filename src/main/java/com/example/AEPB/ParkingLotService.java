package com.example.AEPB;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotService {

    private final List<ParkingSpace> parkingSpaces;

    public ParkingLotService(Integer parkingSize) {
        parkingSpaces = IntStream.rangeClosed(0, parkingSize)
                .mapToObj(i -> new ParkingSpace())
                .collect(Collectors.toList());
    }

    public Ticket parkingCar(Car car) {
        ParkingSpace parkingSpace = parkingSpaces.stream()
                .filter(ParkingSpace::canParking)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("no more parking space"));

        Ticket ticket = new Ticket();

        parkingSpace.parkingCar(ticket, car);

        return ticket;
    }
}
