package com.bakery.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("WEDDING_CAKE")
public class WeddingCake extends CustomCake {

    private int tiers;
    private boolean floralDecorations;

    public WeddingCake() {}

    public WeddingCake(String name, String description, BigDecimal price, String imageUrl, Category category, String flavor, String customMessage, String shape, double weightInKg, int tiers, boolean floralDecorations) {
        super(name, description, price, imageUrl, category, flavor, customMessage, shape, weightInKg);
        this.tiers = tiers;
        this.floralDecorations = floralDecorations;
    }

    public int getTiers() { return tiers; }
    public void setTiers(int tiers) { this.tiers = tiers; }

    public boolean isFloralDecorations() { return floralDecorations; }
    public void setFloralDecorations(boolean floralDecorations) { this.floralDecorations = floralDecorations; }

    @Override
    public BigDecimal calculateCustomPrice() {
        // Base + Rs 2000 per tier + Rs 5000 if floral
        BigDecimal price = super.calculateCustomPrice().add(BigDecimal.valueOf(tiers * 2000.0));
        if (floralDecorations) {
            price = price.add(BigDecimal.valueOf(5000.0));
        }
        return price;
    }

    @Override
    public String getDisplayName() {
        return "Wedding Cake: " + getName();
    }
}
