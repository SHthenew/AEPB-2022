package com.example.AEPB;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
}
