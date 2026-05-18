package com.bakery.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("GENERAL")
public class Product extends BaseEntity implements Displayable {

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private java.util.List<String> imageUrls = new java.util.ArrayList<>();

    private boolean available = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {}

    public Product(String name, String description, BigDecimal price, String imageUrl, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        if (imageUrl != null && !imageUrl.isBlank()) {
            this.imageUrls.add(imageUrl);
        }
        this.category = category;
        this.available = true;
    }

    // --- Getters & Setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public java.util.List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(java.util.List<String> imageUrls) { this.imageUrls = imageUrls; }

    public String getImageUrl() {
        return (imageUrls != null && !imageUrls.isEmpty()) ? imageUrls.get(0) : "";
    }

    public void setImageUrl(String imageUrl) {
        if (this.imageUrls == null) this.imageUrls = new java.util.ArrayList<>();
        if (imageUrl != null && !imageUrl.isBlank()) {
            if (this.imageUrls.isEmpty()) {
                this.imageUrls.add(imageUrl);
            } else {
                this.imageUrls.set(0, imageUrl);
            }
        }
    }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    // POLYMORPHISM: overriding Displayable interface
    @Override
    public String getDisplayName() { return name; }

    @Override
    public String getDisplayDescription() { return description; }
}
