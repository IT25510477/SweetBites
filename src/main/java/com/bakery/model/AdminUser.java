package com.bakery.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class AdminUser extends User {

    private boolean superAdmin = false;

    public AdminUser() {}

    public AdminUser(String username, String email, String password) {
        super(username, email, password);
        this.superAdmin = false;
    }

    public AdminUser(String username, String email, String password, boolean superAdmin) {
        super(username, email, password);
        this.superAdmin = superAdmin;
    }

    public boolean isSuperAdmin() { return superAdmin; }
    public void setSuperAdmin(boolean superAdmin) { this.superAdmin = superAdmin; }

    @Override
    public String getRole() {
        return "admin";
    }

    // Admins might have more complex auth rules in the future
    @Override
    public boolean authenticate(String inputPassword) {
        return this.getPassword() != null && this.getPassword().equals(inputPassword);
    }
}
