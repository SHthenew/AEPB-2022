package com.example.AEPB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmartParkingBoyTest {
    private List<ParkingLot> parkingLots;
    private SmartParkingBoy smartParkingBoy;

    @BeforeEach
    void setUp() {
        ParkingLot parkingLotA = new ParkingLot(100, "A");
        ParkingLot parkingLotB = new ParkingLot(100, "B");
        ParkingLot parkingLotC = new ParkingLot(100, "C");
        parkingLots = List.of(parkingLotA, parkingLotB, parkingLotC);
        smartParkingBoy = new SmartParkingBoy(parkingLots);
    }

    @Test
    void should_get_ticket_and_park_into_first_when_parking_given_3_have_same_space_of_parking_lot_and_normal_car() {
        // given
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        // when
        Ticket ticket = smartParkingBoy.parkingCar(car);
        // then
        assertEquals(parkingLots.get(0).getName(), ticket.getParkingLotName());
    }


}
