package com.example.AEPB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingRobotTest {

    private List<ParkingLot> parkingLots;

    private ParkingRobot parkingRobot;

    @BeforeEach
    void setUp() {
        ParkingLot parkingLotA = new ParkingLot(100);
        ParkingLot parkingLotB = new ParkingLot(100);
        ParkingLot parkingLotC = new ParkingLot(100);
        parkingLots = List.of(parkingLotA, parkingLotB, parkingLotC);
        parkingRobot = new ParkingRobot(parkingLots);
    }

    @Test
    void should_get_ticket_and_parking_into_first_space_when_parking_given_3_empty_parking_lots_and_a_robot() {
        // given
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        // when
        Ticket ticket = parkingRobot.parkingCar(car);

        // then
        assertEquals(car, parkingLots.get(0).pickUpCar(ticket));

    }
}
