package com.akshay.flightreservation.services;

import com.akshay.flightreservation.dto.ReservationRequest;
import com.akshay.flightreservation.entities.Reservation;

public interface ReservationService {
	public Reservation bookFlight(ReservationRequest request);
}
