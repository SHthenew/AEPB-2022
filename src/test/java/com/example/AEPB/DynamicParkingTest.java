package com.example.AEPB;

import com.example.AEPB.ParkingCar.GraduateParkingCarAgent;
import com.example.AEPB.ParkingCar.SmartParkingCarAgent;
import com.example.AEPB.PickUpCar.NormalPickUpCarAgent;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

class DynamicParkingTest {

    private List<ParkingLot> parkingLots;
    private DynamicParking dynamicParking;

    @BeforeEach
    void setUp() {
        ParkingLot parkingLotA = new ParkingLot(100);
        ParkingLot parkingLotB = new ParkingLot(100);
        ParkingLot parkingLotC = new ParkingLot(100);
        parkingLots = List.of(parkingLotA, parkingLotB, parkingLotC);
        // equal GradualBoy
        dynamicParking = new DynamicParking(parkingLots, new GraduateParkingCarAgent(), new NormalPickUpCarAgent());
        // equal SmartBoy
        dynamicParking = new DynamicParking(parkingLots, new SmartParkingCarAgent(), new NormalPickUpCarAgent());
    }

}
