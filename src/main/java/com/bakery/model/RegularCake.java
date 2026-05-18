package com.bakery.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("REGULAR_CAKE")
public class RegularCake extends Cake {

    public RegularCake() {}

    public RegularCake(String name, String description, BigDecimal price, String imageUrl, Category category, String flavor) {
        super(name, description, price, imageUrl, category, flavor);
    }

    @Override
    public String getDisplayName() {
        return getName();
    }
}
