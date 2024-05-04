package com.akshay.flightreservation.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshay.flightreservation.dto.ReservationUpdateRequest;
import com.akshay.flightreservation.entities.Reservation;
import com.akshay.flightreservation.repos.ReservationRepository;
import com.akshay.flightreservation.util.PDFGenerator;

@RestController
@CrossOrigin
public class ReservationRestController {
	@Autowired
	ReservationRepository reservationRepository;
	
	private static Logger logger = LoggerFactory.getLogger(ReservationRestController.class);

	@RequestMapping("/reservations/{id}")
	public Reservation findReservation(@PathVariable("id") Long id) {
		logger.info("Inside findReservation() for id : "+id );
		Optional<Reservation> optional = reservationRepository.findById(id);
		Reservation reservation = optional.get();
		System.out.println(reservation.toString());
		return reservation;
	}
	
	@RequestMapping("/reservations")
	public Reservation updateReservation(@RequestBody ReservationUpdateRequest request) {
		logger.info("Inside updateReservation() for "+request);
		Optional<Reservation> optional = reservationRepository.findById(request.getId());
		Reservation reservation = optional.get();
		reservation.setCheckedIn(request.isCheckedIn());
		reservation.setId(request.getId());
		reservation.setNumberOfBags(request.getNumberOfBags());
		logger.info("Saving Reservation");
		Reservation updatedReservation = reservationRepository.save(reservation);
		return updatedReservation;
		
	}
}
