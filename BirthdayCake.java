package com.bakery.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("BIRTHDAY_CAKE")
public class BirthdayCake extends CustomCake {

    private int numberOfCandles;

    public BirthdayCake() {}

    public BirthdayCake(String name, String description, BigDecimal price, String imageUrl, Category category, String flavor, String customMessage, String shape, double weightInKg, int numberOfCandles) {
        super(name, description, price, imageUrl, category, flavor, customMessage, shape, weightInKg);
        this.numberOfCandles = numberOfCandles;
    }

    public int getNumberOfCandles() { return numberOfCandles; }
    public void setNumberOfCandles(int numberOfCandles) { this.numberOfCandles = numberOfCandles; }

    @Override
    public BigDecimal calculateCustomPrice() {
        // Additional Rs 50 per candle
        return super.calculateCustomPrice().add(BigDecimal.valueOf(numberOfCandles * 50.0));
    }

    @Override
    public String getDisplayName() {
        return "Birthday Cake: " + getName();
    }
}
