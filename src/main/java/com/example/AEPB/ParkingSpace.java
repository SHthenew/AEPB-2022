package com.example.AEPB;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class ParkingSpace {
    private Car car;
    private Ticket ticket;

    public boolean canParking() {
        return Objects.isNull(car);
    }

    public void parkingCar(@NonNull Ticket ticket, @NonNull Car car) {
        this.ticket = ticket;
        this.car = car;
    }

    public boolean haveCar() {
        return Objects.nonNull(car) && Objects.nonNull(ticket);
    }

    public Car pickUpCar() {
        Car pickedCar = car;
        car = null;
        ticket = null;
        return pickedCar;
    }
}
