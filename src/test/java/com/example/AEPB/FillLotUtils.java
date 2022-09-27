package com.example.AEPB;

import java.util.UUID;
import java.util.stream.IntStream;

public final class FillLotUtils {

    public static void fillLotToEmptyRatio(ParkingLot parkingLot, Double emptyRatio) {
        int parkingSize = (int) Math.floor((1 - emptyRatio) * parkingLot.getMaxCapacity());
        fillLot(parkingLot, parkingSize);
    }

    public static void fillLot(ParkingLot parkingLot, int size) {
        IntStream.range(0, size)
                .forEach(i -> parkingLot.parkingCar(Car.builder().plateNo(UUID.randomUUID().toString()).build()));
    }
}
