package com.bakery.repository;

import com.bakery.model.CustomCakeBooking;
import com.bakery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomCakeBookingRepository extends JpaRepository<CustomCakeBooking, Long> {
    List<CustomCakeBooking> findByUser(User user);
}
