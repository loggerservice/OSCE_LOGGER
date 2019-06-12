package com.cg.airlines.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.airlines.bean.Bookings;
import com.cg.airlines.bean.Flights;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
	
	/********** View bookings of a specific Flight based on Flight Id *********/

	List<Bookings> findByFlights(Flights flightId);

	
	/*
	@Transactional
	@Modifying
	@Query("UPDATE Bookings  b SET b.custEmail=:custEmail, b.numberPassengers=:numberPassengers, b.classType=:classType, b.totalFare=:totalFare, b.seatNumbers=:seatNumbers, b.creditcardInfo=:creditcardInfo, b.srcCity=:b.srcCity, b.destCity=:b.destCity   where b.bookingId =:bookingId")
	Bookings updateReservation(Bookings bookings, @Param(value="custEmail") String custEmail,  @Param(value="numberPassengers") int numberPassengers, @Param(value="classType") String classType, @Param(value="totalFare") int totalFare,  @Param(value="seatNumbers") String seatNumbers,  @Param(value="creditcardInfo") String creditcardInfo, @Param(value="srcCity") String srcCity, @Param(value="destCity") String destCity);
	*/

}
