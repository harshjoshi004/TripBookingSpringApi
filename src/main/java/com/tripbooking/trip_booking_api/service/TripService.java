package com.tripbooking.trip_booking_api.service;

import com.tripbooking.trip_booking_api.model.Trip;
import com.tripbooking.trip_booking_api.model.CustomTrip;
import com.tripbooking.trip_booking_api.model.PredefinedPackage;
import com.tripbooking.trip_booking_api.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    public List<Trip> searchTrips(String origin, String destination, LocalDate date) {
        return tripRepository.findTripsBy(origin, destination, date);
    }

    public Optional<Trip> getTripById(String tripId) {
        return tripRepository.findByTripId(tripId);
    }

    public Trip saveTrip(Trip trip) {
        if (trip.getTripId() == null) {
            trip.setTripId(UUID.randomUUID().toString());
        }
        return tripRepository.save(trip);
    }

    public boolean updateTripAvailability(String tripId, int seatsBooked) {
        Optional<Trip> tripOpt = getTripById(tripId);
        if (tripOpt.isPresent()) {
            Trip trip = tripOpt.get();
            if (trip.getAvailableSeats() >= seatsBooked) {
                trip.setAvailableSeats(trip.getAvailableSeats() - seatsBooked);
                tripRepository.save(trip);
                return true;
            }
        }
        return false;
    }

    public PredefinedPackage createPredefinedPackage(String destination, String origin,
            LocalDate departureDate, LocalDate arrivalDate,
            Integer availableSeats, String packageName,
            String description) {
        PredefinedPackage trip = new PredefinedPackage(
                UUID.randomUUID().toString(),
                destination, origin, departureDate, arrivalDate,
                availableSeats, 500.0, packageName, description);
        return (PredefinedPackage) saveTrip(trip);
    }

    public CustomTrip createCustomTrip(String destination, String origin,
            LocalDate departureDate, LocalDate arrivalDate,
            Integer availableSeats, String creatorUid) {
        CustomTrip trip = new CustomTrip(
                UUID.randomUUID().toString(),
                destination, origin, departureDate, arrivalDate,
                availableSeats, 300.0, creatorUid);
        return (CustomTrip) saveTrip(trip);
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public List<Trip> getAllPredefinedPackages() {
        return tripRepository.findAllPredefinedPackages();
    }

    public List<CustomTrip> getCustomTripsByCreator(String creatorUid) {
        return tripRepository.findCustomTripsByCreatorUid(creatorUid);
    }

    public boolean removeTrip(String tripId) {
        Optional<Trip> tripOpt = getTripById(tripId);
        if (tripOpt.isPresent()) {
            tripRepository.delete(tripOpt.get());
            return true;
        }
        return false;
    }
}
