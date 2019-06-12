package com.cg.airlines.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.airlines.bean.Bookings;
import com.cg.airlines.bean.Flights;
import com.cg.airlines.bean.Passengers;
import com.cg.airlines.repository.BookingsRepository;
import com.cg.airlines.repository.FlightRepository;
import com.cg.airlines.repository.PassengersRepository;

@Service
public class FlightService {
	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private BookingsRepository bookingsRepository;

	@Autowired
	private PassengersRepository passengersRepository;

	/******************************* List of Flights Added**************************/

	public Flights flightDetails(Flights flights) {
		return flightRepository.save(flights);
	}

	/***************************** View the list of flights ***********************/

	public List<Flights> getAllFlights() {

		return flightRepository.findAll();

	}

	/******* View List of Flights on particular day to particular destination *****/

	public List<Flights> getDetails(String depDate, String arrCity) {

		return flightRepository.findDetails(depDate, arrCity);
	}

	public Flights getDetails(int flightId) {

		return flightRepository.getOne(flightId);
	}
	/********* Update and Manage the Flight Schedules based On Flight Id ********/

	public Flights updateSchedule(Flights flights, int flightId) {
		Flights existingFlights = (Flights) flightRepository.getOne(flightId);
		flights.setFlightId(flightId);
		BeanUtils.copyProperties(flights, existingFlights);
		flightRepository.updateFilghtDetails(flightId, existingFlights.getDepDate(), existingFlights.getArrDate(),
				existingFlights.getDepTime(), existingFlights.getArrTime());
		return flightRepository.getOne(flightId);
	}

	/******** Update and Manage the Flight Information based On Flight Id***********/

	public Flights manageSchedules(Flights flights, int flightId) {
		Flights existingFlights = (Flights) flightRepository.getOne(flightId);
		flights.setFlightId(flightId);
		BeanUtils.copyProperties(flights, existingFlights);
		flightRepository.manageFlightDetails(flightId, existingFlights.getFlightName(),
				existingFlights.getBusinessSeats(), existingFlights.getEconomySeats(),
				existingFlights.getFirstclassSeats());
		return flightRepository.getOne(flightId);
	}

	/*********************** List of bookings Added ****************************/

		  public Bookings bookingDetails(Bookings bookings) {
			  bookings.setFlights(flightRepository.getOne(bookings.getFlights().getFlightId()));
				Bookings bookingsInfo=bookingsRepository.save(bookings);
				if("firstclass".equals(bookingsInfo.getClassType()))
					flightRepository.updateFistClass(bookingsInfo.getFlights().getFlightId());
				else if("businessclass".equals(bookingsInfo.getClassType()))
					flightRepository.updateBusinessClass(bookingsInfo.getFlights().getFlightId());
				else if("economyclass".equals(bookingsInfo.getClassType()))
				    flightRepository.updateEconomyClass(bookingsInfo.getFlights().getFlightId());
				return bookingsInfo;
				}
	
		  
	/********************* Fetch the reservation details **************************/

	public Bookings fetchDetails(int bookingId) {

		return bookingsRepository.getOne(bookingId);
	}

	/******************** Update the Reservation Details **************************/

	public Bookings updateDetails(Bookings bookings) {

	return bookingsRepository.save(bookings);
	}
	

	/****************** Delete the bookings Details*******************************/

	public String deleteMyBooking(int bookingId) {
		bookingsRepository.deleteById(bookingId);
		String message = "Deleted Successfully";
		return message;
	}

	/******************** View Bookings of all the Flights *********************/

	public List<Bookings> getAllBookings() {

		return bookingsRepository.findAll();
	}

	/********** View bookings of a specific Flight based on Flight Id *********/

	public List<Bookings> specificFlight(int flightId) {

		return bookingsRepository.findByFlights(flightRepository.getOne(flightId));
	}

	/********************* List of Passengers Added ***************************/

	public Passengers passengersList(Passengers passengers) {
		passengers.setFlight(flightRepository.getOne(passengers.getFlight().getFlightId()));
		return passengersRepository.save(passengers);
	}

	/************************ View List of Passengers **************************/

	public List<Passengers> getAllPassengers() {

		return passengersRepository.findAll();
	}

	/******* View List of Passengers of a specific Flight based on Flight Id ******/

	public List<Passengers> specificPassenger(int flightId) {

		return passengersRepository.findByflight(flightRepository.getOne(flightId));
	}
	
	/*******************View Overall Flight Occupancy Details************************/

	public Bookings occupancyDetails(Bookings bookings) {
		
		bookings.setFlights(flightRepository.getOne(bookings.getFlights().getFlightId()));
		Bookings bookingsInfo=bookingsRepository.save(bookings);
		if("firstclass".equals(bookingsInfo.getClassType()))
			flightRepository.updateFistClass(bookingsInfo.getFlights().getFlightId());
		else if("businessclass".equals(bookingsInfo.getClassType()))
			flightRepository.updateEconomyClass(bookingsInfo.getFlights().getFlightId());
		else if("economyclass".equals(bookingsInfo.getClassType()))
		    flightRepository.updateBusinessClass(bookingsInfo.getFlights().getFlightId());
		return bookingsInfo;
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * }
	 */
}

