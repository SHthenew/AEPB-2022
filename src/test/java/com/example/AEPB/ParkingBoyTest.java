package com.example.AEPB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingBoyTest {

    private List<ParkingLot> parkingLots;
    private ParkingBoy parkingBoy;

    @BeforeEach
    void setUp() {
        ParkingLot parkingLotA = new ParkingLot(100, 1, "A");
        ParkingLot parkingLotB = new ParkingLot(100, 2, "B");
        ParkingLot parkingLotC = new ParkingLot(100, 3, "C");
        parkingLots = List.of(parkingLotA, parkingLotB, parkingLotC);
        parkingBoy = new ParkingBoy(parkingLots);
    }

    @Test
    void should_get_ticket_and_park_into_first_when_parking_given_3_have_space_parking_lot_and_normal_car() {
        // given
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        // when
        Ticket ticket = parkingBoy.parkingCar(car);
        // then
        assertEquals(parkingLots.get(0).getName(), ticket.getParkingLotName());
    }

    @Test
    void should_get_ticket_and_park_into_2nd_lot_when_parking_given_3_parking_lot_and_first_full_and_second_have_space_and_normal_car() {
        // given
        ParkingLot firstLot = parkingLots.get(0);
        IntStream.range(0, firstLot.getMaxCapacity())
                .forEach(i -> firstLot.parkingCar(Car.builder().plateNo(UUID.randomUUID().toString()).build()));
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        // when
        Ticket ticket = parkingBoy.parkingCar(car);

        // then
        assertEquals(parkingLots.get(1).getName(), ticket.getParkingLotName());
    }

}
