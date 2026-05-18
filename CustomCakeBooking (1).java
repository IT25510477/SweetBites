package com.bakery.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "custom_bookings")
public class CustomCakeBooking extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "custom_cake_id")
    private CustomCake customCake;

    private LocalDate deliveryDate;
    private String bookingStatus; // "PENDING", "CONFIRMED", "CANCELLED"
    
    @ElementCollection
    @CollectionTable(name = "booking_inspiration_images", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "image_url")
    private java.util.List<String> inspirationImageUrls = new java.util.ArrayList<>();
    
    @Column(columnDefinition = "TEXT")
    private String specialNotes;

    public CustomCakeBooking() {}

    public CustomCakeBooking(User user, CustomCake customCake, LocalDate deliveryDate) {
        this.user = user;
        this.customCake = customCake;
        this.deliveryDate = deliveryDate;
        this.bookingStatus = "PENDING";
    }

    // --- Getters & Setters ---
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public CustomCake getCustomCake() { return customCake; }
    public void setCustomCake(CustomCake customCake) { this.customCake = customCake; }

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    public java.util.List<String> getInspirationImageUrls() { return inspirationImageUrls; }
    public void setInspirationImageUrls(java.util.List<String> inspirationImageUrls) { this.inspirationImageUrls = inspirationImageUrls; }

    public String getSpecialNotes() { return specialNotes; }
    public void setSpecialNotes(String specialNotes) { this.specialNotes = specialNotes; }
}
