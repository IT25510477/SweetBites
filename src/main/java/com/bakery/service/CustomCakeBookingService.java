package com.bakery.service;

import com.bakery.model.CustomCakeBooking;
import com.bakery.model.User;
import com.bakery.repository.CustomCakeBookingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomCakeBookingService extends AbstractCrudService<CustomCakeBooking, Long> {

    private final CustomCakeBookingRepository repository;

    public CustomCakeBookingService(CustomCakeBookingRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<CustomCakeBooking, Long> getRepository() {
        return repository;
    }

    public List<CustomCakeBooking> findByUser(User user) {
        return repository.findByUser(user);
    }
}
