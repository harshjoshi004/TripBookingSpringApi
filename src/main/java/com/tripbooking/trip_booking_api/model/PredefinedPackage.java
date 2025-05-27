package com.tripbooking.trip_booking_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("PREDEFINED")
public class PredefinedPackage extends Trip {

    @Column(nullable = false)
    private String packageName;

    @Column(length = 1000)
    private String description;

    @Column(length = 2000)
    private String inclusions; // JSON string or comma-separated

    // Constructors
    public PredefinedPackage() {
        super();
    }

    public PredefinedPackage(String tripId, String destination, String origin,
            LocalDate departureDate, LocalDate arrivalDate,
            Integer availableSeats, Double basePrice,
            String packageName, String description) {
        super(tripId, destination, origin, departureDate, arrivalDate, availableSeats, basePrice);
        this.packageName = packageName;
        this.description = description;
    }

    // Getters and Setters
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInclusions() {
        return inclusions;
    }

    public void setInclusions(String inclusions) {
        this.inclusions = inclusions;
    }
}
