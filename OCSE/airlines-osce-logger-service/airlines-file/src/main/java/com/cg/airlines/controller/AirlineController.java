package com.cg.airlines.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.airlines.bean.Bookings;
import com.cg.airlines.bean.Flights;
import com.cg.airlines.bean.Passengers;
import com.cg.airlines.exception.AirlinesException;
import com.cg.airlines.service.FlightService;

@RestController

public class AirlineController {

	@Autowired
	private FlightService flightService;
	
	

	/****************************** List of Flights Added*****************************************/

	@RequestMapping(value = "/flightDetails", method = RequestMethod.POST)
	public Flights flightDetails(@RequestBody Flights flights) throws AirlinesException {
		try {
			return flightService.flightDetails(flights);
		} catch (Exception e) {
			throw new AirlinesException(400, e.getMessage());
		}
	}

	/****************************** View the list of flights*************************************/

	@RequestMapping("/flightDetails")
	public List<Flights> getAllFlights() throws AirlinesException {
		try {
			return flightService.getAllFlights();
		} catch (Exception e) {
			throw new AirlinesException(204, e.getMessage());
		}

	}

	/************** View List of Flights on particular day to particular destination*************/

	@RequestMapping("/flightDetail/filter")
	public List<Flights> getDetails(@RequestParam(required = false) String depDate,
			@RequestParam(required = false) String arrCity) {
		return flightService.getDetails(depDate, arrCity);
	}

	/************** Update and Manage the Flight Schedules based On Flight Id******************/

	@RequestMapping(method = RequestMethod.PUT, value = "/flightDetails/{flightId}")
	public Flights updateSchedule(@RequestBody Flights flights, @PathVariable int flightId) throws AirlinesException {
		try {
			return flightService.updateSchedule(flights, flightId);
		} catch (Exception e) {
			throw new AirlinesException(204, e.getMessage());
		}
	}

	/************** Update and Manage the Flight Information based On Flight Id***************/

	@RequestMapping(method = RequestMethod.PUT, value = "/flightDetail/{flightId}")
	public Flights manageSchedules(@RequestBody Flights flights, @PathVariable int flightId) {
		return flightService.manageSchedules(flights, flightId);

	}

	/******************* List of bookings Added / Request for ticket booking*****************/

	@RequestMapping(value = "/bookings", method = RequestMethod.POST)
	public Bookings bookingDetails(@RequestBody Bookings bookings) throws AirlinesException {
		try {
			return flightService.bookingDetails(bookings);
		} catch (Exception e) {
			throw new AirlinesException(400, e.getMessage());
		}
	}

	/************************** Fetch the reservation details*********************************/

	@RequestMapping(value = "/flightDetails/fetch/{bookingId}")
	public Bookings fetchDetails(@PathVariable int bookingId) throws AirlinesException {
		try {
			return flightService.fetchDetails(bookingId);
		} catch (Exception e) {
			throw new AirlinesException(204, e.getMessage());
		}

	}

	/********************** Update the Reservation Details***********************************/

	@RequestMapping(value = "/update/{bookingId}", method = RequestMethod.PUT)
	public Bookings updateDetails(@RequestBody Bookings bookings) throws AirlinesException {
		try {
			return flightService.updateDetails(bookings);
		} catch (Exception e) {
			throw new AirlinesException(204, e.getMessage());
		}

	}

	/******************* Delete the bookings Details****************************************/

	@RequestMapping(value = "/bookings/{bookingId}", method = RequestMethod.DELETE)
	public String deleteMyBooking(@PathVariable int bookingId) throws AirlinesException {
		try {
			return flightService.deleteMyBooking(bookingId);
		} catch (Exception e) {
			throw new AirlinesException(204, e.getMessage());
		}

	}

	/************************* View Bookings of all the Flights**************************/

	@RequestMapping("/bookings")
	public List<Bookings> getAllBookings() throws AirlinesException {
		try {
			return flightService.getAllBookings();
		} catch (Exception e) {
			throw new AirlinesException(204, e.getMessage());
		}

	}

	/***************** View bookings of a specific Flight based on Flight Id *************/

	@RequestMapping(value = "/bookings/{flightId}")
	public List<Bookings> specificFlight(@PathVariable int flightId) throws AirlinesException {
		try {
			return flightService.specificFlight(flightId);
		} catch (Exception e) {
			throw new AirlinesException(204, e.getMessage());
		}

	}

	/************************** List of Passengers Added ********************************/

	@RequestMapping(value = "/passengers", method = RequestMethod.POST)
	public Passengers passengersList(@RequestBody Passengers passengers) throws AirlinesException {
		try {
			return flightService.passengersList(passengers);
		} catch (Exception e) {
			throw new AirlinesException(400, e.getMessage());
		}
	}

	/*************************** View List of Passengers********************************/

	@RequestMapping("/passengers")
	public List<Passengers> getAllPassengers() {
		return flightService.getAllPassengers();
	}

	/********* View List of Passengers of a specific Flight based on Flight Id**********/

	@RequestMapping(value = "/passengers/{flightId}")
	public List<Passengers> specificPassenger(@PathVariable int flightId) throws AirlinesException {
		try {
			return flightService.specificPassenger(flightId);
		} catch (Exception e) {
			throw new AirlinesException(204, e.getMessage());
		}

	}

	/******************** View Overall Flight Occupancy Details************************/

	@RequestMapping("/occupancy/{flightId}")
	public Flights occupancyDetails(@PathVariable("flightId") int flightId) throws AirlinesException {
		try {
			return flightService.getDetails(flightId);
		} catch (Exception e) {
			throw new AirlinesException(204, e.getMessage());
		}

	}

}
