package com.example.AEPB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void should_parking_failed_when_parking_given_parking_lot_full() {
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

    @Test
    void should_parking_failed_when_parking_given_a_car_without_plate_number() {
        // given
        Car noPlateNumberCar = Car.builder().plateNo(null).build();

        // when
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> parkingLot.parkingCar(noPlateNumberCar));

        // then
        assertEquals("the car plate number can not be null", thrown.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void should_parking_failed_when_parking_given_a_car_with_empty_plate_number(String plateNo) {
        // given
        Car emptyPlateNumberCar = Car.builder().plateNo(plateNo).build();

        // when
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> parkingLot.parkingCar(emptyPlateNumberCar));

        // then
        assertEquals("the car plate number can not be empty", thrown.getMessage());
    }

    @Test
    void should_parking_failed_when_parking_given_null_car() {
        // given
        Car car = null;

        // when
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> parkingLot.parkingCar(car));

        // then
        assertEquals("the car can not be null", thrown.getMessage());
    }

    @Test
    void should_parking_failed_when_parking_given_the_car_having_same_plate_number_in_plot() {
        // given
        Car firstCar = Car.builder().plateNo("京A88888").build();
        parkingLot.parkingCar(firstCar);

        // when
        Car secondCar = Car.builder().plateNo("京A88888").build();
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> parkingLot.parkingCar(secondCar));

        // then
        assertEquals("the car plate number is duplicate", thrown.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void should_get_car_failed_when_pick_up_given_a_ticket_with_empty_ticket_number(String emptyTicketNo) {
        // given
        Car normalCar = Car.builder().plateNo("京A88888").build();
        Ticket ticket = parkingLot.parkingCar(normalCar);
        Ticket emptyTicket = Ticket.builder().ticketNo(emptyTicketNo).build();

        // when
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> parkingLot.pickUpCar(emptyTicket));

        // then
        assertEquals("the ticket number can not be empty", thrown.getMessage());
    }

    @Test
    void should_get_car_failed_when_pick_up_given_no_ticket() {
        // given
        Car normalCar = Car.builder().plateNo("京A88888").build();
        Ticket ticket = parkingLot.parkingCar(normalCar);

        // when
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> parkingLot.pickUpCar(null));

        // then
        assertEquals("the ticket can not be null", thrown.getMessage());
    }

    @Test
    void should_parking_failed_when_parking_given_null_parking_lot() {
        // given
        parkingLot = null;
        Car normalCar = Car.builder().plateNo("京A88888").build();

        // when
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> parkingLot.parkingCar(normalCar));

        // then
        assertNull(thrown.getMessage());
    }
}
