package com.bakery.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CUSTOMER")
public class CustomerUser extends User {

    public CustomerUser() {}

    public CustomerUser(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getRole() {
        return "customer";
    }

    @Override
    public boolean authenticate(String inputPassword) {
        return this.getPassword() != null && this.getPassword().equals(inputPassword);
    }
}
