package com.akshay.flightreservation.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.akshay.flightreservation.dto.ReservationRequest;
import com.akshay.flightreservation.entities.Flight;
import com.akshay.flightreservation.entities.Reservation;
import com.akshay.flightreservation.repos.FlightRepository;
import com.akshay.flightreservation.services.ReservationService;

@Controller
public class ReservationController {
	
	@Autowired
	FlightRepository flightRepository;
	
	private static Logger logger = LoggerFactory.getLogger(ReservationController.class);

	
	@Autowired
	ReservationService reservationService;
	
	@RequestMapping("showCompleteReservation")
	public String showCompleteReservation(@RequestParam("flightId") Long flightId,ModelMap model) {
		logger.info("showCompleteReservation() invoked with the flight Id : "+flightId);
		Optional<Flight> optionalEntity =  flightRepository.findById(flightId);
		if (optionalEntity.isPresent()) {
			Flight flight = optionalEntity.get();
			model.addAttribute("flight",flight);
			logger.info("Flight is : "+flight);
			return "completeReservation";
		}
		else {
			return "displayFlights";
		}
	}
	
	@RequestMapping(value = "/completeReservation",method=RequestMethod.POST)
	public String completeReservation(ReservationRequest request,ModelMap model) {
		logger.info("completeReservation() "+ request);
		Reservation reservation = reservationService.bookFlight(request);
		model.addAttribute("msg","Reservation Created successfully and the id is " + reservation.getId());
		return "reservationConfirmation";
		
	}
		
}
