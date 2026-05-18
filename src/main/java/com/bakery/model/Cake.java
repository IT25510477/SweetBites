package com.bakery.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CAKE")
public class Cake extends Product {

    private String flavor;

    public Cake() {}

    public Cake(String name, String description, BigDecimal price, String imageUrl, Category category, String flavor) {
        super(name, description, price, imageUrl, category);
        this.flavor = flavor;
    }

    public String getFlavor() { return flavor; }
    public void setFlavor(String flavor) { this.flavor = flavor; }

    @Override
    public String getDisplayName() {
        return "Cake: " + getName();
    }
}
