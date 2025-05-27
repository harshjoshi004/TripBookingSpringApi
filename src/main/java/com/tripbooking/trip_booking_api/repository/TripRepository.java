package com.tripbooking.trip_booking_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tripbooking.trip_booking_api.model.CustomTrip;
import com.tripbooking.trip_booking_api.model.Trip;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    Optional<Trip> findByTripId(String tripId);

    @Query("SELECT t FROM Trip t WHERE " +
            "LOWER(t.origin) = LOWER(:origin) AND " +
            "LOWER(t.destination) = LOWER(:destination) AND " +
            "(t.departureDate = :date OR " +
            "(t.departureDate <= :date AND t.arrivalDate >= :date))")
    List<Trip> findTripsBy(@Param("origin") String origin,
            @Param("destination") String destination,
            @Param("date") LocalDate date);

    @Query("SELECT t FROM CustomTrip t WHERE t.creatorUid = :creatorUid")
    List<CustomTrip> findCustomTripsByCreatorUid(@Param("creatorUid") String creatorUid);

    @Query("SELECT t FROM Trip t WHERE TYPE(t) = PredefinedPackage")
    List<Trip> findAllPredefinedPackages();
}
