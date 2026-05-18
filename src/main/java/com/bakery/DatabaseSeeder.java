package com.bakery;

import com.bakery.model.*;
import com.bakery.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoryRepository         categoryRepository;
    private final UserRepository             userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public DatabaseSeeder(CategoryRepository categoryRepository,
                          UserRepository userRepository) {
        this.categoryRepository  = categoryRepository;
        this.userRepository      = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        deleteDefaultProducts();

        if (categoryRepository.count() > 0) {
            // Seed admin user just in case
            if (userRepository.count() == 0) {
                userRepository.save(new AdminUser("admin", "admin@sweetbites.lk", "admin123", true));
            }
            return;
        }
        seedAll();
    }

    private void deleteDefaultProducts() {
        try {
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

            List<String> defaultNames = List.of(
                "Chocolate Fudge Cake",
                "Vanilla Bean Cake",
                "Red Velvet Cake",
                "Golden Caramel Drizzle Cake",
                "Lemon Zest Cake",
                "Strawberry Cream Cake",
                "Black Forest Cake",
                "Birthday Chocolate Cake",
                "Wedding Rose Cake",
                "Custom Birthday – Amaya",
                "Custom Wedding – Kasun & Nethmi",
                "Custom Birthday – Saman",
                "Butter Croissant",
                "Pain au Chocolat",
                "Almond Croissant",
                "Éclair",
                "Sourdough Loaf",
                "French Baguette",
                "Ciabatta",
                "Multigrain Bread",
                "Assorted Cupcake Box",
                "Chocolate Mud Cupcake",
                "Strawberry Lemonade Cupcake",
                "Caramel Apple Cupcake",
                "Chocolate Chip Cookies",
                "Blueberry Muffin",
                "Double Chocolate Cookie",
                "Oatmeal Raisin Cookie",
                "Macadamia Nut Cookie"
            );

            // Delete from dependent tables
            entityManager.createNativeQuery("DELETE FROM order_items WHERE product_id IN (SELECT id FROM products WHERE name IN :names)")
                    .setParameter("names", defaultNames)
                    .executeUpdate();

            entityManager.createNativeQuery("DELETE FROM reviews WHERE product_id IN (SELECT id FROM products WHERE name IN :names)")
                    .setParameter("names", defaultNames)
                    .executeUpdate();

            entityManager.createNativeQuery("DELETE FROM booking_inspiration_images WHERE booking_id IN (SELECT id FROM custom_bookings WHERE custom_cake_id IN (SELECT id FROM products WHERE name IN :names))")
                    .setParameter("names", defaultNames)
                    .executeUpdate();

            entityManager.createNativeQuery("DELETE FROM custom_bookings WHERE custom_cake_id IN (SELECT id FROM products WHERE name IN :names)")
                    .setParameter("names", defaultNames)
                    .executeUpdate();

            entityManager.createNativeQuery("DELETE FROM product_images WHERE product_id IN (SELECT id FROM products WHERE name IN :names)")
                    .setParameter("names", defaultNames)
                    .executeUpdate();

            entityManager.createNativeQuery("DELETE FROM products WHERE name IN :names")
                    .setParameter("names", defaultNames)
                    .executeUpdate();

            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
            System.out.println("DEBUG: Successfully cleaned up all default products and their details from database.");
        } catch (Exception e) {
            System.err.println("DEBUG: Failed to clean up default products: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void seedAll() {
        // ── CATEGORIES ──────────────────────────────────────────────────────────
        categoryRepository.save(new Category("Classic Cakes",   "Traditional cakes for all occasions",                "/uploads/images/chocolate_fudge.png"));
        categoryRepository.save(new Category("French Pastries", "Delicate and flaky pastries",                        "https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=800"));
        categoryRepository.save(new Category("Artisan Breads",  "Freshly baked daily breads",                         "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=800"));
        categoryRepository.save(new Category("Cupcakes",        "Bite-sized sweet treats",                            "https://images.unsplash.com/photo-1550617931-e17a7b70dce2?w=800"));
        categoryRepository.save(new Category("Cookies",         "Warm and gooey cookies",                             "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=800"));
        categoryRepository.save(new Category("Custom Cakes",    "Bespoke personalised cakes for special events",       "/uploads/images/IMG-20260514-WA0029.jpg"));

        // ── USERS ────────────────────────────────────────────────────────────────
        userRepository.save(new AdminUser("admin",  "admin@sweetbites.lk", "admin123", true));
        userRepository.save(new CustomerUser("dinura", "dinura@gmail.com", "pass123"));
        userRepository.save(new CustomerUser("amaya",  "amaya@gmail.com",  "pass123"));
        userRepository.save(new CustomerUser("kasun",  "kasun@gmail.com",  "pass123"));
        userRepository.save(new CustomerUser("nethmi", "nethmi@gmail.com", "pass123"));
        userRepository.save(new CustomerUser("saman",  "saman@gmail.com",  "pass123"));
    }
}
