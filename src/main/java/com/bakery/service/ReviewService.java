package com.bakery.service;

import com.bakery.model.Review;
import com.bakery.model.Product;
import com.bakery.repository.ReviewRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService extends AbstractCrudService<Review, Long> {

    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Review, Long> getRepository() {
        return repository;
    }

    public List<Review> findByProduct(Product product) {
        return repository.findByProduct(product);
    }

    public List<Review> findAll() {
        return repository.findAll();
    }

    public long countByProduct(Product product) {
        return repository.findByProduct(product).size();
    }

    public double averageRatingByProduct(Product product) {
        List<Review> reviews = repository.findByProduct(product);
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }
}
