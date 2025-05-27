package com.tripbooking.trip_booking_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripbooking.trip_booking_api.model.Booking;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingId(String bookingId);

    List<Booking> findByUserUid(String userUid);

    List<Booking> findByUserUidOrderByBookingTimeDesc(String userUid);
}
