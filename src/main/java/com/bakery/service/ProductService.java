package com.bakery.service;

import com.bakery.model.Category;
import com.bakery.model.Product;
import com.bakery.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends AbstractCrudService<Product, Long> {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    protected JpaRepository<Product, Long> getRepository() {
        return productRepository;
    }

    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> findAvailable() {
        return productRepository.findByAvailableTrue();
    }

    public List<Product> search(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Product> findStandardProducts() {
        return findAll().stream()
                .filter(p -> !(p instanceof com.bakery.model.CustomCake))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Product> findStandardProductsByCategory(Category category) {
        return findByCategory(category).stream()
                .filter(p -> !(p instanceof com.bakery.model.CustomCake))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Product> searchStandard(String keyword) {
        return search(keyword).stream()
                .filter(p -> !(p instanceof com.bakery.model.CustomCake))
                .collect(java.util.stream.Collectors.toList());
    }
}
