package com.example.AEPB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        fillLot(firstLot, firstLot.getMaxCapacity());
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        // when
        Ticket ticket = parkingBoy.parkingCar(car);

        // then
        assertEquals(parkingLots.get(1).getName(), ticket.getParkingLotName());
    }

    @Test
    void should_parking_failed_when_parking_car_given_parking_lot_is_all_full() {
        // given
        parkingLots.forEach(lot -> fillLot(lot, lot.getMaxCapacity()));
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        //when
        ParkingCarException thrown = assertThrows(ParkingCarException.class,
                () -> parkingBoy.parkingCar(car));

        // then
        assertEquals("all parking lots is full", thrown.getMessage());
    }

    @Test
    void should_get_car_when_pick_up_given_parked_ticket() {
        // given
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        Ticket ticket = parkingBoy.parkingCar(car);

        // when
        Car returnedCar = parkingBoy.pickUp(ticket);

        // then
        assertEquals(car, returnedCar);
    }

    @Test
    void should_parking_failed_when_parking_given_1st_remain_1_space_and_2nd_empty_and_duplicated_car() {
        fillLot(parkingLots.get(0), parkingLots.get(0).getMaxCapacity() - 1);
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        Ticket ticket = parkingBoy.parkingCar(car);

        // when
        ParkingCarException thrown = assertThrows(ParkingCarException.class,
                () -> parkingBoy.parkingCar(car));

        // then
        assertEquals("have duplicated car in parking lot", thrown.getMessage());
    }

    @Test
    void should_park_into_1st_lot_when_parking_car_given_1st_lot_full_and_pick_up_car_from_1st_lot_and_2nd_3th_lot_not_full() {
        // given
        fillLot(parkingLots.get(0), parkingLots.get(0).getMaxCapacity() - 1);
        Ticket ticket = parkingLots.get(0).parkingCar(Car.builder().plateNo(UUID.randomUUID().toString()).build());
        fillLot(parkingLots.get(1), parkingLots.get(1).getMaxCapacity() - 1);

        parkingBoy.pickUp(ticket);

        // when
        Ticket ticket2 = parkingBoy.parkingCar(Car.builder().plateNo(UUID.randomUUID().toString()).build());

        // then
        assertEquals(parkingLots.get(0).getName(), ticket2.getParkingLotName());
    }

    private void fillLot(ParkingLot parkingLot, int size) {
        IntStream.range(0, size)
                .forEach(i -> parkingLot.parkingCar(Car.builder().plateNo(UUID.randomUUID().toString()).build()));
    }

}
