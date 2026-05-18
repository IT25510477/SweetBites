package com.bakery.repository;

import com.bakery.model.Category;
import com.bakery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
    List<Product> findByAvailableTrue();
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByCategoryAndAvailableTrue(Category category);
}
