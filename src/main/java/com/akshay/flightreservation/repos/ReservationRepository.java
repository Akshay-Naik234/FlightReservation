package com.akshay.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.flightreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

}
