package com.tripbooking.trip_booking_api.service;

import com.tripbooking.trip_booking_api.model.Booking;
import com.tripbooking.trip_booking_api.model.BookingStatus;
import com.tripbooking.trip_booking_api.model.Trip;
import com.tripbooking.trip_booking_api.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TripService tripService;

    @Transactional
    public Optional<Booking> createBooking(String userUid, String tripId) {
        Optional<Trip> tripOpt = tripService.getTripById(tripId);
        if (tripOpt.isEmpty()) {
            return Optional.empty();
        }

        Trip trip = tripOpt.get();
        if (trip.getAvailableSeats() > 0) {
            Booking booking = new Booking(UUID.randomUUID().toString(), userUid, trip);
            booking = bookingRepository.save(booking);

            // Update trip availability
            tripService.updateTripAvailability(tripId, 1);

            return Optional.of(booking);
        }

        return Optional.empty();
    }

    @Transactional
    public boolean cancelBooking(String bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findByBookingId(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            if (booking.getStatus() != BookingStatus.CANCELLED) {
                booking.cancel();
                bookingRepository.save(booking);

                // Update trip availability
                Trip trip = booking.getTrip();
                trip.setAvailableSeats(trip.getAvailableSeats() + 1);
                tripService.saveTrip(trip);

                return true;
            }
        }
        return false;
    }

    public List<Booking> getBookingsByUser(String userUid) {
        return bookingRepository.findByUserUidOrderByBookingTimeDesc(userUid);
    }

    public Optional<Booking> getBookingById(String bookingId) {
        return bookingRepository.findByBookingId(bookingId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
