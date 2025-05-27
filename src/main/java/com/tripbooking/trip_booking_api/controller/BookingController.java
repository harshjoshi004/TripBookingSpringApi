package com.tripbooking.trip_booking_api.controller;

import com.tripbooking.trip_booking_api.dto.CreateBookingRequest;
import com.tripbooking.trip_booking_api.model.Booking;
import com.tripbooking.trip_booking_api.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*") // Allow all origins for now
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestHeader("X-User-UID") String userUid,
            @Valid @RequestBody CreateBookingRequest request) {

        Optional<Booking> booking = bookingService.createBooking(userUid, request.getTripId());
        return booking.map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/my")
    public ResponseEntity<List<Booking>> getMyBookings(
            @RequestHeader("X-User-UID") String userUid) {
        List<Booking> bookings = bookingService.getBookingsByUser(userUid);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable String bookingId) {
        Optional<Booking> booking = bookingService.getBookingById(bookingId);
        return booking.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable String bookingId,
            @RequestHeader("X-User-UID") String userUid) {

        // Verify the booking belongs to the user
        Optional<Booking> booking = bookingService.getBookingById(bookingId);
        if (booking.isEmpty() || !booking.get().getUserUid().equals(userUid)) {
            return ResponseEntity.notFound().build();
        }

        boolean cancelled = bookingService.cancelBooking(bookingId);
        return cancelled ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}
