package com.tripbooking.trip_booking_api.controller;

import com.tripbooking.trip_booking_api.dto.CreateCustomTripRequest;
import com.tripbooking.trip_booking_api.dto.TripSearchRequest;
import com.tripbooking.trip_booking_api.model.CustomTrip;
import com.tripbooking.trip_booking_api.model.Trip;
import com.tripbooking.trip_booking_api.service.TripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*") // Allow all origins for now
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping("/search")
    public ResponseEntity<List<Trip>> searchTrips(@Valid @RequestBody TripSearchRequest request) {
        List<Trip> trips = tripService.searchTrips(
                request.getOrigin(),
                request.getDestination(),
                request.getDate());
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Trip> getTripById(@PathVariable String tripId) {
        Optional<Trip> trip = tripService.getTripById(tripId);
        return trip.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/packages")
    public ResponseEntity<List<Trip>> getAllPredefinedPackages() {
        List<Trip> packages = tripService.getAllPredefinedPackages();
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/custom")
    public ResponseEntity<List<CustomTrip>> getMyCustomTrips(
            @RequestHeader("X-User-UID") String userUid) {
        List<CustomTrip> customTrips = tripService.getCustomTripsByCreator(userUid);
        return ResponseEntity.ok(customTrips);
    }

    @PostMapping("/custom")
    public ResponseEntity<CustomTrip> createCustomTrip(
            @RequestHeader("X-User-UID") String userUid,
            @Valid @RequestBody CreateCustomTripRequest request) {

        CustomTrip trip = tripService.createCustomTrip(
                request.getDestination(),
                request.getOrigin(),
                request.getDepartureDate(),
                request.getArrivalDate(),
                request.getAvailableSeats(),
                userUid);

        if (request.getSpecialRequests() != null) {
            trip.setSpecialRequests(request.getSpecialRequests());
            tripService.saveTrip(trip);
        }

        return ResponseEntity.ok(trip);
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips);
    }
}
