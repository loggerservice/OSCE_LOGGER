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

}
