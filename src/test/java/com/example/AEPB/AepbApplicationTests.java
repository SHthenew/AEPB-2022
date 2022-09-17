package com.example.AEPB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HelloTest {

	private ParkingLotService parkingLot;
	static private final Integer parkingSize = 100;

	@BeforeEach
	void setUp() {
		parkingLot = new ParkingLotService(parkingSize);
	}


	@Test
	void should_no_null() {

		Hello hello = new Hello();
		assertNotNull(hello);
	}

	@Test
	void should_get_tickt_when_parking_a_normal_car() {
		// given
		Car normalCar = Car.builder().build();

		// when
		Ticket ticket = parkingLot.parkingCar(normalCar);

		// then
		assertNotNull(ticket);
	}
}
