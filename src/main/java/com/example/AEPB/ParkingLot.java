package com.example.AEPB;


import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParkingLot {


    private final Map<Ticket, Car> parkingSpace = new HashMap<>();
    @Getter
    private final int maxCapacity;

    public ParkingLot(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    private void validCar(Car car) {
        if (Objects.isNull(car)) {
            throw new ParkingCarException("the car can not be null");
        }

        if (Objects.isNull(car.getPlateNo())) {
            throw new ParkingCarException("the car plate number can not be null");
        }

        if (Strings.isBlank(car.getPlateNo())) {
            throw new ParkingCarException("the car plate number can not be empty");
        }
        if (containCar(car)) {
            throw new ParkingCarException("the car plate number is duplicate");
        }
    }

    public Ticket parkingCar(Car car) {

        validCar(car);

        if (!haveCapacity()) {
            throw new ParkingCarException("the parking lot is full");
        }

        Ticket ticket = new Ticket();
        parkingSpace.put(ticket, car);

        return ticket;
    }

    public boolean haveCapacity() {
        return parkingSpace.size() < maxCapacity;

    }

    public int remainCapacity() {
        return maxCapacity - parkingSpace.size();
    }

    public double emptyRatio() {
        return 1 - (double) parkingSpace.size() / maxCapacity;
    }

    public boolean containCar(Car car) {
        return parkingSpace.entrySet().stream()
                .anyMatch(entry -> entry.getValue().equals(car));
    }

    public Car pickUpCar(Ticket ticket) {
        if (isInvalidTicket(ticket)) {
            throw new PickUpException("the ticket can not be null");
        }

        Car car = parkingSpace.get(ticket);
        if (Objects.isNull(car)) {
            throw new PickUpException("the ticket number can not be found");
        }

        parkingSpace.remove(ticket);

        return car;
    }

    private boolean isInvalidTicket(Ticket ticket) {
        return Objects.isNull(ticket);
    }

    public boolean ticketInTheLot(Ticket ticket) {
        return parkingSpace.containsKey(ticket);
    }
}
