package com.example.AEPB;

import com.example.AEPB.ParkingCar.SmartParkingCarAgent;
import com.example.AEPB.PickUpCar.NormalPickUpCarAgent;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MainProcess {
    public static void main(String[] args) {
        MainProcess mainProcess = new MainProcess();
        log.info("-------------------------------------");
        log.info("Running process 1");
        mainProcess.process1();
        log.info("-------------------------------------");
        log.info("Running process 2");
        mainProcess.process2();
    }

    public void process1() {
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

    public void process2() {
        // Have 3 parking lot
        // Have a car
        // Have GraduateParkingBody
        ParkingLot parkingLotA = new ParkingLot(100);
        ParkingLot parkingLotB = new ParkingLot(100);
        ParkingLot parkingLotC = new ParkingLot(100);
        List<ParkingLot> parkingLots = List.of(parkingLotA, parkingLotB, parkingLotC);
        DynamicParking dynamicParking = new DynamicParking(parkingLots, new SmartParkingCarAgent(), new NormalPickUpCarAgent());
        Car car = Car.builder().plateNo("京A88888").build();

        log.info("I have a car, the plateNo is {}", car.getPlateNo());

        // parking car into lot, then get a ticket
        Ticket ticket = dynamicParking.parkingCar(car);

        log.info("I parking my car into the lot, then I get the ticket No. is {}", ticket.getTicketNo());

        // i use the ticket to pick up my car
        Car returnedCar = dynamicParking.pickUp(ticket);
        log.info("I pick up my car by ticket, the plateNo is {}", returnedCar.getPlateNo());
    }

}
