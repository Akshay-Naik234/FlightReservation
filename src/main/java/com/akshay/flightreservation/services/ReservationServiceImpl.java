package com.akshay.flightreservation.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.akshay.flightreservation.dto.ReservationRequest;
import com.akshay.flightreservation.entities.Flight;
import com.akshay.flightreservation.entities.Passenger;
import com.akshay.flightreservation.entities.Reservation;
import com.akshay.flightreservation.repos.FlightRepository;
import com.akshay.flightreservation.repos.PassengerRepository;
import com.akshay.flightreservation.repos.ReservationRepository;
import com.akshay.flightreservation.util.EmailUtil;
import com.akshay.flightreservation.util.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Value("${com.akshay.flightreservation.itinerary.dirpath}")
	private String ITINERARY_DIR = "F:\\Spring_Tool_Suite\\Reservation\\reservation";

	@Autowired
	FlightRepository flightRepository;
	
	private static Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Autowired
	PassengerRepository passengerRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	PDFGenerator pdfGenerator;
	
	@Autowired
	EmailUtil emailUtil;
	@Transactional
	public Reservation bookFlight(ReservationRequest request) {
		logger.info("Inside bookFlight()");
		
		long flightId = request.getFlightId();
		logger.info("Fetching flight for flight id : "+flightId );
		Optional<Flight> optionalEntity = flightRepository.findById(flightId);
		Flight flight = optionalEntity.get();
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());
		logger.info("Saving the passenger : "+passenger);
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		logger.info("Saving the reservation : "+reservation);
		Reservation savedReservation = reservationRepository.save(reservation);
		
		String filePath = ITINERARY_DIR +savedReservation.getId()+".pdf";
		logger.info("Generating the itinerary");
		pdfGenerator.generateItinerary(savedReservation, filePath);
		logger.info("Emailing the itinerary");
		emailUtil.sendItinerary(passenger.getEmail(), filePath);
		
		return savedReservation;
	}

}
