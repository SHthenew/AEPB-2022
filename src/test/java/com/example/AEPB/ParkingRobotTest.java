package com.example.AEPB;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @ParameterizedTest
    @CsvSource({
            "1, 1, 1, 0",
            "0.1, 0.2, 0.3, 2",
            "0.2, 0.3, 0.05, 1",
            "1, 0.2, 0.2, 0"
    })
    void should_get_ticket_when_parking_given_empty_ratio_and_normal_car(
            @AggregateWith(ParkedNumberAndParkingOrderAggregate.class)
            EmptyRatioAndParkingOrder emptyRatioAndParkingOrder) {
        // given
        IntStream.range(0, parkingLots.size())
                .forEach(i ->
                        fillLotToEmptyRatio(parkingLots.get(i), emptyRatioAndParkingOrder.getEmptyRatio().get(i)));
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        // when
        Ticket ticket = parkingRobot.parkingCar(car);

        // then
        assertEquals(car, parkingLots.get(emptyRatioAndParkingOrder.getParkingOrder()).pickUpCar(ticket));
    }

    @Test
    void should_pick_up_failed_when_pick_given_a_car_in_parking_lot_and_robot() {
        // given
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        Ticket ticket = parkingRobot.parkingCar(car);

        // when
        PickUpException thrown = assertThrows(PickUpException.class, () -> parkingRobot.pickUp(ticket));

        // then
        assertEquals("robot can not pick up", thrown.getMessage());
    }

    @Test
    void should_parking_failed_when_parking_car_given_parking_lot_is_all_full() {
        // given
        parkingLots.forEach(lot -> fillLotToEmptyRatio(lot, 0.0));
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        //when
        ParkingCarException thrown = assertThrows(ParkingCarException.class,
                () -> parkingRobot.parkingCar(car));

        // then
        assertEquals("all parking lots is full", thrown.getMessage());
    }

    @Test
    void should_parking_failed_when_parking_given_the_car_having_same_plate_number_in_plot() {
        // given
        Car firstCar = Car.builder().plateNo("京A88888").build();
        parkingRobot.parkingCar(firstCar);

        // when
        Car secondCar = Car.builder().plateNo("京A88888").build();
        RuntimeException thrown = assertThrows(ParkingCarException.class, () -> parkingRobot.parkingCar(secondCar));

        // then
        assertEquals("have duplicated car in parking lot", thrown.getMessage());
    }

    private void fillLotToEmptyRatio(ParkingLot parkingLot, Double emptyRatio) {
        int parkingSize = (int) Math.floor((1 - emptyRatio) * parkingLot.getMaxCapacity());

        IntStream.range(0, parkingSize)
                .forEach(i -> parkingLot.parkingCar(Car.builder().plateNo(UUID.randomUUID().toString()).build()));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class EmptyRatioAndParkingOrder {
        private final List<Double> emptyRatio;
        private final Integer parkingOrder;

    }

    private static class ParkedNumberAndParkingOrderAggregate implements ArgumentsAggregator {
        @Override
        public EmptyRatioAndParkingOrder aggregateArguments(
                ArgumentsAccessor arguments, ParameterContext context) throws ArgumentsAggregationException {
            List<Double> emptyRatio = IntStream.range(0, arguments.size() - 1)
                    .mapToObj(arguments::getDouble)
                    .collect(Collectors.toList());
            Integer parkingOrder = arguments.getInteger(arguments.size() - 1);
            return new EmptyRatioAndParkingOrder(emptyRatio, parkingOrder);
        }
    }
}
