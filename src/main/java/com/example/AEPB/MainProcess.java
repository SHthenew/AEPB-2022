package com.example.AEPB;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MainProcess {
    public static void main(String[] args) {
        // Have 3 parking lot
        // Have a car
        // Have GraduateParkingBody
        ParkingLot parkingLotA = new ParkingLot(100);
        ParkingLot parkingLotB = new ParkingLot(100);
        ParkingLot parkingLotC = new ParkingLot(100);
        List<ParkingLot> parkingLots = List.of(parkingLotA, parkingLotB, parkingLotC);
        GraduateParkingBoy graduateParkingBoy = new GraduateParkingBoy(parkingLots);
        Car car = Car.builder().plateNo("京A88888").build();

        log.info("I have a car, the plateNo is {}", car.getPlateNo());

        // parking car into lot, then get a ticket
        Ticket ticket = graduateParkingBoy.parkingCar(car);

        log.info("I parking my car into the lot, then I get the ticket No. is {}", ticket.getTicketNo());

        // i use the ticket to pick up my car
        Car returnedCar = graduateParkingBoy.pickUp(ticket);
        log.info("I pick up my car by ticket, the plateNo is {}", returnedCar.getPlateNo());
    }
}
