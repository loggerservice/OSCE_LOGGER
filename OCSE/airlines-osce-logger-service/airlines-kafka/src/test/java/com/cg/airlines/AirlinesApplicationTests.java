package com.cg.airlines;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.airlines.bean.Flights;
import com.cg.airlines.repository.FlightRepository;
import com.cg.airlines.service.FlightService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirlinesApplicationTests {
	
	@Mock
	FlightRepository flightRepository;
	
	@InjectMocks
	FlightService flightService;
	
	
		@Test
		public void flights() throws Exception {
			Flights flights = new Flights();
			flights.setFlightId(123);
			flights.setFlightName("Airindia");
			flights.setDepCity("Hyderabad");
			flights.setArrCity("Banglore");
			flights.setDepDate("19-04-2019");
			flights.setArrDate("20-04-2019");
			flights.setDepTime("11:30AM");
			flights.setArrTime("2:30AM");
			flights.setBusinessSeats(200);
			flights.setEconomySeats(250);
			flights.setFirstclassSeats(120);
			
			when(flightRepository.save(flights)).thenReturn(flights);
			
			Flights flightsactual = flightService.flightDetails(flights);
			System.out.println(flights.toString());
			System.out.println(flightsactual.toString());
			assertEquals(flights, flightsactual);

		}
		
		@Test
		
		public void getDetails() throws Exception
		{
			Flights flights = new Flights();
			flights.setFlightId(11);
			flights.setFlightName("Indigo");
			flights.setDepCity("Banglore");
			flights.setArrCity("Hyderabad");
			flights.setDepDate("26-04-2019");
			flights.setArrDate("27-04-2019");
			flights.setDepTime("11:20AM");
			flights.setArrTime("2:30AM");
			flights.setBusinessSeats(200);
			flights.setEconomySeats(250);
			flights.setFirstclassSeats(120);
			

			when(flightRepository.getOne(12)).thenReturn(flights);
			List<Flights> flights1 = flightService.getAllFlights();
			assertNotEquals(flights, flights1);
	     }
			
		}
