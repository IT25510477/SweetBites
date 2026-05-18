package com.bakery.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CUSTOMER_REVIEW")
public class CustomerReview extends Review {

    public CustomerReview() {}

    public CustomerReview(User user, Product product, int rating, String comment) {
        super(user, product, rating, comment);
    }

    @Override
    public String getFormattedReview() {
        return "Customer " + getUser().getUsername() + " rated " + getRating() + "/5: " + getComment();
    }
}
