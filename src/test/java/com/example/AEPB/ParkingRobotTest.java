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
    void should_get_ticket_when_parking_given_parked_car_number_and_normal_car(
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
