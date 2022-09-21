package com.example.AEPB;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParkingBoyTest {
    @Test
    void should_get_ticket_when_parking_given_3_have_sapcec_parking_lot_and_normal_car() {
        // given
        ParkingLot parkingLotA = new ParkingLot(100, 1, "A");
        ParkingLot parkingLotB = new ParkingLot(100, 2, "B");
        ParkingLot parkingLotC = new ParkingLot(100, 3, "C");
        ParkingBoy parkingBoy = new ParkingBoy(List.of(parkingLotA, parkingLotB, parkingLotC));
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        // when
        Ticket ticket = parkingBoy.parkingCar(car);
        // then
        assertNotNull(ticket);
        assertEquals(parkingLotA.getName(), ticket.getParkingLotName());
    }
}
