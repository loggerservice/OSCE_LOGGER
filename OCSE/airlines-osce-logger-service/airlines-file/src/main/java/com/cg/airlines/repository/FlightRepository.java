package com.cg.airlines.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cg.airlines.bean.Flights;


@Repository
public interface FlightRepository extends JpaRepository<Flights, Integer>
{
   
	/****** Update and Manage the Flight Schedules based On Flight Id ********/
	@Transactional
	@Modifying
	@Query("UPDATE Flights  m SET m.depDate=:depDate, m.arrDate=:arrDate, m.depTime=:depTime, m.arrTime=:arrTime where m.flightId =:flightId")
	int updateFilghtDetails(@Param(value="flightId") int flightId,@Param(value="depDate") String depDate, @Param(value="arrDate") String arrDate,@Param(value="depTime") String depTime,@Param(value="arrTime") String arrTime );
    
	/********* Update and Manage the Flight Information based On Flight Id ***********/
	@Transactional
	@Modifying
	@Query("UPDATE Flights  n SET n.flightName=:flightName, n.businessSeats=:businessSeats, n.economySeats=:economySeats, n.firstclassSeats=:firstclassSeats where n.flightId =:flightId")
	int manageFlightDetails(@Param(value="flightId") int flightId, @Param(value="flightName") String flightName, @Param(value="businessSeats") int businessSeats, @Param(value="economySeats") int economySeats, @Param(value="firstclassSeats") int firstclassSeats);

	
	/******** View List of Flights on particular day to particular destination*******/
	@Query("SELECT b from Flights b where b.depDate=:depDate AND b.arrCity=:arrCity" )
	List<Flights> findDetails(@Param(value = "depDate") String depDate, @Param(value = "arrCity") String arrCity);

	
	@Transactional
	@Modifying
	@Query("UPDATE Flights n SET  n.firstclassSeats=n.firstclassSeats-1 where n.flightId =:flightId")
	void updateFistClass(@Param(value="flightId") int flightId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Flights p SET  p.businessSeats=p.businessSeats-1 where p.flightId =:flightId")
	void updateBusinessClass(@Param(value="flightId") int flightId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Flights o SET  o.economySeats=o.economySeats-1  where o.flightId =:flightId")
	void updateEconomyClass(@Param(value="flightId") int flightId);
	
	

}