package com.cg.airlines.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.airlines.bean.Flights;
import com.cg.airlines.bean.Passengers;

@Repository
public interface PassengersRepository extends JpaRepository<Passengers, Integer> {

	/******* View List of Passengers of a specific Flight based on Flight Id ******/

	List<Passengers> findByflight(Flights one);
}
