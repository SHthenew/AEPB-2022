package com.example.AEPB;


import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotService {

    private final List<ParkingSpace> parkingSpaces;

    public ParkingLotService(Integer parkingSize) {
        parkingSpaces = IntStream.range(0, parkingSize)
                .mapToObj(i -> new ParkingSpace())
                .collect(Collectors.toList());
    }

    private void validCar(Car car) {
        if (Objects.isNull(car)) {
            throw new RuntimeException("the car can not be null");
        }

        if (Objects.isNull(car.getPlateNo())) {
            throw new RuntimeException("the car plate number can not be null");
        }

        if (Strings.isBlank(car.getPlateNo())) {
            throw new RuntimeException("the car plate number can not be empty");
        }
        checkDuplicateCar(car);
    }

    public Ticket parkingCar(Car car) {

        validCar(car);

        Ticket ticket = saveCarIntoParkingSpace(car);

        return ticket;
    }

    private Ticket saveCarIntoParkingSpace(Car car) {
        ParkingSpace parkingSpace = parkingSpaces.stream()
                .filter(ParkingSpace::canParking)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("the parking lot is full"));

        Ticket ticket = new Ticket();

        parkingSpace.parkingCar(ticket, car);
        return ticket;
    }

    private void checkDuplicateCar(Car car) {
        parkingSpaces.stream()
                .filter(ParkingSpace::haveCar)
                .map(space -> space.getCar().getPlateNo())
                .filter(plateNo -> plateNo.equals(car.getPlateNo()))
                .findAny()
                .ifPresent(p -> {
                    throw new RuntimeException("the car plate number is duplicate");
                });
    }

    public Car pickUpCar(Ticket ticket) {
        if (isInvalidTicket(ticket)) {
            throw new RuntimeException("the ticket can not be null");
        }

        ParkingSpace parkingSpace = parkingSpaces.stream()
                .filter(space -> space.haveCar() && space.getTicket().sameTicket(ticket))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("the ticket number can not be found"));
        return parkingSpace.pickUpCar();
    }

    private boolean isInvalidTicket(Ticket ticket) {
        return Objects.isNull(ticket);
    }

    private boolean isInvalidCar(Car car) {
        return Objects.isNull(car);
    }
}
