package com.bakery.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CUSTOM_CAKE")
public class CustomCake extends Cake {

    private String customMessage;
    private String shape;
    private double weightInKg;

    public CustomCake() {}

    public CustomCake(String name, String description, BigDecimal price, String imageUrl, Category category, String flavor, String customMessage, String shape, double weightInKg) {
        super(name, description, price, imageUrl, category, flavor);
        this.customMessage = customMessage;
        this.shape = shape;
        this.weightInKg = weightInKg;
    }

    public String getCustomMessage() { return customMessage; }
    public void setCustomMessage(String customMessage) { this.customMessage = customMessage; }

    public String getShape() { return shape; }
    public void setShape(String shape) { this.shape = shape; }

    public double getWeightInKg() { return weightInKg; }
    public void setWeightInKg(double weightInKg) { this.weightInKg = weightInKg; }

    // Polymorphic pricing logic
    public BigDecimal calculateCustomPrice() {
        // Base price + (weight * 1000)
        return getPrice().add(BigDecimal.valueOf(weightInKg * 1000.0));
    }

    @Override
    public String getDisplayName() {
        return "Custom Cake: " + getName();
    }
}
