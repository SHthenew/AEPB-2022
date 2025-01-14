package com.example.AEPB;

import com.example.AEPB.Exception.ParkingCarException;
import com.example.AEPB.Exception.PickUpException;
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

class SmartParkingBoyTest {
    private List<ParkingLot> parkingLots;
    private SmartParkingBoy smartParkingBoy;

    @BeforeEach
    void setUp() {
        ParkingLot parkingLotA = new ParkingLot(100);
        ParkingLot parkingLotB = new ParkingLot(100);
        ParkingLot parkingLotC = new ParkingLot(100);
        parkingLots = List.of(parkingLotA, parkingLotB, parkingLotC);
        smartParkingBoy = new SmartParkingBoy(parkingLots);
    }

    @Test
    void should_get_ticket_and_park_into_first_when_parking_given_3_have_same_space_of_parking_lot_and_normal_car() {
        // given
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        // when
        Ticket ticket = smartParkingBoy.parkingCar(car);

        // then
        assertEquals(car, parkingLots.get(0).pickUpCar(ticket));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 0",
            "10, 2, 3, 1",
            "10, 2, 2, 1",
            "10, 3, 2, 2"
    })
    void should_get_ticket_when_parking_given_parked_car_number_and_normal_car(
            @AggregateWith(ParkedNumberAndParkingOrderAggregate.class)
            ParkedNumberAndParkingOrder parkedNumberAndParkingOrder) {
        // given
        IntStream.range(0, parkingLots.size())
                .forEach(i -> FillLotUtils.fillLot(parkingLots.get(i), parkedNumberAndParkingOrder.getParkedNumbers().get(i)));
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();

        // when
        Ticket ticket = smartParkingBoy.parkingCar(car);

        // then
        assertEquals(car, parkingLots.get(parkedNumberAndParkingOrder.getParkingOrder()).pickUpCar(ticket));
    }

    @Test
    void should_get_car_when_pick_up_given_the_ticket_to_smart_boy() {
        // given
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        Ticket ticket = smartParkingBoy.parkingCar(car);

        // when
        Car returnedCar = smartParkingBoy.pickUp(ticket);

        // then
        assertEquals(car, returnedCar);
    }

    @Test
    void should_get_car_failed_when_pick_up_given_a_used_ticket_to_smart_boy() {
        // given
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        Ticket ticket = smartParkingBoy.parkingCar(car);
        smartParkingBoy.pickUp(ticket);

        // when
        PickUpException thrown = assertThrows(PickUpException.class, () -> smartParkingBoy.pickUp(ticket));

        // then
        assertEquals("the ticket is invalid", thrown.getMessage());
    }

    @Test
    void should_parking_car_failed_when_parking_given_a_car_duplicate_parking() {
        // given
        Car car = Car.builder().plateNo(UUID.randomUUID().toString()).build();
        smartParkingBoy.parkingCar(car);

        // when
        ParkingCarException thrown = assertThrows(ParkingCarException.class, () -> smartParkingBoy.parkingCar(car));

        // then
        assertEquals("have duplicated car in parking lot", thrown.getMessage());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class ParkedNumberAndParkingOrder {
        private final List<Integer> parkedNumbers;
        private final Integer parkingOrder;

    }

    private static class ParkedNumberAndParkingOrderAggregate implements ArgumentsAggregator {
        @Override
        public ParkedNumberAndParkingOrder aggregateArguments(
                ArgumentsAccessor arguments, ParameterContext context) throws ArgumentsAggregationException {
            List<Integer> parkedNumbers = IntStream.range(0, arguments.size() - 1)
                    .mapToObj(arguments::getInteger)
                    .collect(Collectors.toList());
            Integer parkingOrder = arguments.getInteger(arguments.size() - 1);
            return new ParkedNumberAndParkingOrder(parkedNumbers, parkingOrder);
        }
    }


}
