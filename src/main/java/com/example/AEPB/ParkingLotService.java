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

    public Car pickUpCar(Ticket ticket) {
        ParkingSpace parkingSpace = parkingSpaces.stream()
                .filter(space -> space.haveCar() && space.getTicket().sameTicket(ticket))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("not find car."));
        return parkingSpace.pickUpCar();
    }
}
