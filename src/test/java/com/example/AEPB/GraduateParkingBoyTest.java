package com.example.AEPB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GraduateParkingBoyTest {

    private List<ParkingLot> parkingLots;
    private GraduateParkingBoy graduateParkingBoy;

    @BeforeEach
    void setUp() {
        ParkingLot parkingLotA = new ParkingLot(100);
        ParkingLot parkingLotB = new ParkingLot(100);
        ParkingLot parkingLotC = new ParkingLot(100);
        parkingLots = List.of(parkingLotA, parkingLotB, parkingLotC);
        graduateParkingBoy = new GraduateParkingBoy(parkingLots);
    }

    @Test
    void should_get_ticket_and_park_into_first_when_parking_given_3_have_space_parking_lot_and_normal_car() {
        // given
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        // when
        Ticket ticket = graduateParkingBoy.parkingCar(car);
        // then
        assertEquals(car, parkingLots.get(0).pickUpCar(ticket));
    }

    @Test
    void should_get_ticket_and_park_into_2nd_lot_when_parking_given_3_parking_lot_and_first_full_and_second_have_space_and_normal_car() {
        // given
        ParkingLot firstLot = parkingLots.get(0);
        FillLotUtils.fillLot(firstLot, firstLot.getMaxCapacity());
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        // when
        Ticket ticket = graduateParkingBoy.parkingCar(car);

        // then
        assertEquals(car, parkingLots.get(1).pickUpCar(ticket));
    }

    @Test
    void should_parking_failed_when_parking_car_given_parking_lot_is_all_full() {
        // given
        parkingLots.forEach(lot -> FillLotUtils.fillLot(lot, lot.getMaxCapacity()));
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        //when
        ParkingCarException thrown = assertThrows(ParkingCarException.class,
                () -> graduateParkingBoy.parkingCar(car));

        // then
        assertEquals("all parking lots is full", thrown.getMessage());
    }

    @Test
    void should_get_car_when_pick_up_given_parked_ticket() {
        // given
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        Ticket ticket = graduateParkingBoy.parkingCar(car);

        // when
        Car returnedCar = graduateParkingBoy.pickUp(ticket);

        // then
        assertEquals(car, returnedCar);
    }

    @Test
    void should_parking_failed_when_parking_given_1st_remain_1_space_and_2nd_empty_and_duplicated_car() {
        FillLotUtils.fillLot(parkingLots.get(0), parkingLots.get(0).getMaxCapacity() - 1);
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        graduateParkingBoy.parkingCar(car);

        // when
        ParkingCarException thrown = assertThrows(ParkingCarException.class,
                () -> graduateParkingBoy.parkingCar(car));

        // then
        assertEquals("have duplicated car in parking lot", thrown.getMessage());
    }

    @Test
    void should_park_into_1st_lot_when_parking_car_given_1st_lot_full_and_pick_up_car_from_1st_lot_and_2nd_3th_lot_not_full() {
        // given
        FillLotUtils.fillLot(parkingLots.get(0), parkingLots.get(0).getMaxCapacity() - 1);
        Ticket ticket = parkingLots.get(0).parkingCar(Car.builder().plateNo(UUID.randomUUID().toString()).build());
        FillLotUtils.fillLot(parkingLots.get(1), parkingLots.get(1).getMaxCapacity() - 1);

        graduateParkingBoy.pickUp(ticket);
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        // when
        Ticket ticket2 = graduateParkingBoy.parkingCar(car);

        // then
        assertEquals(car, parkingLots.get(0).pickUpCar(ticket2));
    }

}
