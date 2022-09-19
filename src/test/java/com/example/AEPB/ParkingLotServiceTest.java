package com.example.AEPB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotServiceTest {

    static private final Integer parkingSize = 100;
    private ParkingLotService parkingLot;

    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLotService(parkingSize);
    }

    @Test
    void should_get_tickt_when_parking_given_a_normal_car() {
        // given
        Car normalCar = Car.builder().plateNo("12345").build();

        // when
        Ticket ticket = parkingLot.parkingCar(normalCar);

        // then
        assertNotNull(ticket);
    }

    @Test
    void should_get_car_when_pick_up_given_a_ticket() {
        // given
        Car normalCar = Car.builder().plateNo("12345").build();
        Ticket ticket = parkingLot.parkingCar(normalCar);

        // when
        Car returnedCar = parkingLot.pickUpCar(ticket);

        // then
        assertNotNull(returnedCar);
        assertEquals(normalCar.getPlateNo(), returnedCar.getPlateNo());
    }

    @Test
    void  should_parking_failed_when_parking_given_parking_lot_full() {
        // given
        for (int i = 0; i < parkingSize; i++) {
            Car distinctCar = Car.builder().plateNo(UUID.randomUUID().toString()).build();
            parkingLot.parkingCar(distinctCar);
        }
        Car normalCar = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        // when
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> parkingLot.parkingCar(normalCar));

        // then
        assertEquals("the parking lot is full", thrown.getMessage());
    }
}
