package com.tripbooking.trip_booking_api.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateBookingRequest {

    @NotBlank(message = "Trip ID is required")
    private String tripId;

    // Constructors
    public CreateBookingRequest() {
    }

    public CreateBookingRequest(String tripId) {
        this.tripId = tripId;
    }

    // Getters and Setters
    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
