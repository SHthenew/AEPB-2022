package com.example.AEPB;


import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParkingLot {


    private final Map<Ticket, Car> parkingSpace = new HashMap<>();
    private final int capacity;


    public ParkingLot(int capacity) {
        this.capacity = capacity;
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

        if (parkingSpace.size() >= capacity) {
            throw new RuntimeException("the parking lot is full");
        }

        Ticket ticket = new Ticket();
        parkingSpace.put(ticket, car);

        return ticket;
    }

    private void checkDuplicateCar(Car car) {
        parkingSpace.entrySet().stream()
                .filter(entry -> entry.getValue().equals(car))
                .findAny()
                .ifPresent(p -> {
                    throw new RuntimeException("the car plate number is duplicate");
                });
    }

    public Car pickUpCar(Ticket ticket) {
        if (isInvalidTicket(ticket)) {
            throw new RuntimeException("the ticket can not be null");
        }

        Car car = parkingSpace.get(ticket);
        if (Objects.isNull(car)) {
            throw new RuntimeException("the ticket number can not be found");
        }

        parkingSpace.remove(ticket);

        return car;
    }

    private boolean isInvalidTicket(Ticket ticket) {
        return Objects.isNull(ticket);
    }
}
