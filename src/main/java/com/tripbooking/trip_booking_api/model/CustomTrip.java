package com.tripbooking.trip_booking_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("CUSTOM")
public class CustomTrip extends Trip {

    @Column(nullable = false)
    private String creatorUid; // Firebase UID

    @Column(length = 2000)
    private String specialRequests; // JSON string or comma-separated

    // Constructors
    public CustomTrip() {
        super();
    }

    public CustomTrip(String tripId, String destination, String origin,
            LocalDate departureDate, LocalDate arrivalDate,
            Integer availableSeats, Double basePrice, String creatorUid) {
        super(tripId, destination, origin, departureDate, arrivalDate, availableSeats, basePrice);
        this.creatorUid = creatorUid;
    }

    // Getters and Setters
    public String getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }
}
