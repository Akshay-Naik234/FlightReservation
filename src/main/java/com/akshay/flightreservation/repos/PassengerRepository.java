package com.akshay.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.flightreservation.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long>{

}
