package com.bakery.service;

import com.bakery.model.User;
import com.bakery.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserService extends AbstractCrudService — INHERITANCE + POLYMORPHISM.
 * ENCAPSULATION: all state through private fields.
 */
@Service
public class UserService extends AbstractCrudService<User, Long> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean authenticate(String email, String password) {
        return userRepository.findByEmail(email)
                .map(u -> password != null && password.equals(u.getPassword()))
                .orElse(false);
    }
}
