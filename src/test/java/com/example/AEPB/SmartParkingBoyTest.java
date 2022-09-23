package com.example.AEPB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmartParkingBoyTest {
    private List<ParkingLot> parkingLots;
    private SmartParkingBoy smartParkingBoy;

    @BeforeEach
    void setUp() {
        ParkingLot parkingLotA = new ParkingLot(100);
        ParkingLot parkingLotB = new ParkingLot(100);
        ParkingLot parkingLotC = new ParkingLot(100);
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
        assertEquals(car, parkingLots.get(0).pickUpCar(ticket));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 0",
            "10, 2, 3, 1",
            "10, 2, 2, 1",
            "10, 3, 2, 2"
    })
    void should_get_ticket_when_parking_given_parked_car_number_and_normal_car(
            int lotANumber, int lotBNumber, int lotCNumber, int parkedOrder) {
        // given
        fillLot(parkingLots.get(0), lotANumber);
        fillLot(parkingLots.get(1), lotBNumber);
        fillLot(parkingLots.get(2), lotCNumber);
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        // when
        Ticket ticket = smartParkingBoy.parkingCar(car);
        // then
        assertEquals(car, parkingLots.get(parkedOrder).pickUpCar(ticket));
    }

    private void fillLot(ParkingLot parkingLot, int size) {
        IntStream.range(0, size)
                .forEach(i -> parkingLot.parkingCar(Car.builder().plateNo(UUID.randomUUID().toString()).build()));
    }

}
