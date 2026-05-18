package com.bakery.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN_REVIEW")
public class AdminReview extends Review {

    private String adminTitle;

    public AdminReview() {}

    public AdminReview(User user, Product product, int rating, String comment, String adminTitle) {
        super(user, product, rating, comment);
        this.adminTitle = adminTitle;
    }

    public String getAdminTitle() { return adminTitle; }
    public void setAdminTitle(String adminTitle) { this.adminTitle = adminTitle; }

    @Override
    public String getFormattedReview() {
        return "[Official] " + adminTitle + " " + getUser().getUsername() + " says: " + getComment();
    }
}
