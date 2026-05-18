package com.bakery;

import com.bakery.model.*;
import com.bakery.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoryRepository         categoryRepository;
    private final ProductRepository          productRepository;
    private final UserRepository             userRepository;
    private final OrderRepository            orderRepository;
    private final ReviewRepository           reviewRepository;
    private final CustomCakeBookingRepository bookingRepository;

    public DatabaseSeeder(CategoryRepository categoryRepository,
                          ProductRepository productRepository,
                          UserRepository userRepository,
                          OrderRepository orderRepository,
                          ReviewRepository reviewRepository,
                          CustomCakeBookingRepository bookingRepository) {
        this.categoryRepository  = categoryRepository;
        this.productRepository   = productRepository;
        this.userRepository      = userRepository;
        this.orderRepository     = orderRepository;
        this.reviewRepository    = reviewRepository;
        this.bookingRepository   = bookingRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) {
            // Data already exists — preserve all admin changes, do nothing
            return;
        }
        seedAll();
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // FULL SEED (first run only)
    // ─────────────────────────────────────────────────────────────────────────────
    private void seedAll() {

        // ── CATEGORIES ──────────────────────────────────────────────────────────
        Category cakes    = categoryRepository.save(new Category("Classic Cakes",   "Traditional cakes for all occasions",                "/uploads/images/chocolate_fudge.png"));
        Category pastries = categoryRepository.save(new Category("French Pastries", "Delicate and flaky pastries",                        "https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=800"));
        Category breads   = categoryRepository.save(new Category("Artisan Breads",  "Freshly baked daily breads",                         "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=800"));
        Category cupcakes = categoryRepository.save(new Category("Cupcakes",        "Bite-sized sweet treats",                            "https://images.unsplash.com/photo-1550617931-e17a7b70dce2?w=800"));
        Category cookies  = categoryRepository.save(new Category("Cookies",         "Warm and gooey cookies",                             "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=800"));
        Category custom   = categoryRepository.save(new Category("Custom Cakes",    "Bespoke personalised cakes for special events",       "/uploads/images/IMG-20260514-WA0029.jpg"));

        // ── CLASSIC CAKES ────────────────────────────────────────────────────────
        //  Generated images (local — stored in uploads/images/)
        Product chocCake    = productRepository.save(new RegularCake("Chocolate Fudge Cake",        "Rich double chocolate fudge cake with silky ganache",              BigDecimal.valueOf(1500), "/uploads/images/chocolate_fudge.png",   cakes, "Chocolate"));
        Product vanCake     = productRepository.save(new RegularCake("Vanilla Bean Cake",           "Soft vanilla sponge with light buttercream frosting",              BigDecimal.valueOf(1200), "/uploads/images/vanilla_cake.png",      cakes, "Vanilla"));
        Product redVelvet   = productRepository.save(new RegularCake("Red Velvet Cake",             "Classic red velvet with cream cheese frosting",                    BigDecimal.valueOf(1800), "/uploads/images/red_velvet.png",        cakes, "Red Velvet"));
        Product caramelCake = productRepository.save(new RegularCake("Golden Caramel Drizzle Cake", "Moist sponge cake with golden caramel drizzle",                    BigDecimal.valueOf(2000), "/uploads/images/caramel_cake.png",      cakes, "Caramel"));
        Product lemonCake   = productRepository.save(new RegularCake("Lemon Zest Cake",             "Bright and zesty lemon cake with lemon curd filling",              BigDecimal.valueOf(1600), "/uploads/images/lemon_cake.png",        cakes, "Lemon"));
        Product strawCake   = productRepository.save(new RegularCake("Strawberry Cream Cake",       "Fluffy sponge with fresh strawberries and whipped cream",          BigDecimal.valueOf(1900), "/uploads/images/strawberry_cake.png",   cakes, "Strawberry"));
        //  Black Forest — use a reliable Unsplash URL (quota exhausted for generation)
        Product blackForest = productRepository.save(new RegularCake("Black Forest Cake",           "Cherry and chocolate masterpiece with cream layers",               BigDecimal.valueOf(1700), "https://images.unsplash.com/photo-1588195538326-c5b1e9f80a1b?w=600", cakes, "Chocolate Cherry"));

        // ── CUSTOM / SPECIAL CAKES ───────────────────────────────────────────────
        //  These use the uploaded local IMG files that already exist in uploads/images/
        Product birthdayChoc = productRepository.save(new BirthdayCake(
                "Birthday Chocolate Cake",
                "Celebratory chocolate cake with personalised message & candles",
                BigDecimal.valueOf(2500), "/uploads/images/IMG-20260514-WA0029.jpg", custom,
                "Chocolate", "Happy Birthday!", "Round", 2.0, 12));

        Product weddingRose = productRepository.save(new WeddingCake(
                "Wedding Rose Cake",
                "Elegant 3-tier wedding cake with floral decorations and vanilla cream",
                BigDecimal.valueOf(15000), "/uploads/images/IMG-20260514-WA0030.jpg", custom,
                "Vanilla", "Forever & Always", "Round", 8.0, 3, true));

        // ── FRENCH PASTRIES ──────────────────────────────────────────────────────
        Product croissant = productRepository.save(new RegularCake("Butter Croissant",  "Flaky and buttery French classic, baked fresh daily",       BigDecimal.valueOf(350),  "https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=600",  pastries, "Butter"));
        Product painChoc  = productRepository.save(new RegularCake("Pain au Chocolat",  "Croissant dough filled with rich dark chocolate",           BigDecimal.valueOf(450),  "https://images.unsplash.com/photo-1549903072-7e6e0bedb7fb?w=600",  pastries, "Chocolate"));
        Product almondCro = productRepository.save(new RegularCake("Almond Croissant",  "Twice-baked croissant with almond frangipane cream",        BigDecimal.valueOf(500),  "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=600", pastries, "Almond"));
        Product eclair    = productRepository.save(new RegularCake("Éclair",             "Choux pastry filled with vanilla custard, chocolate glaze", BigDecimal.valueOf(600),  "https://images.unsplash.com/photo-1587314168485-3236d6710814?w=600", pastries, "Vanilla Cream"));

        // ── ARTISAN BREADS ───────────────────────────────────────────────────────
        Product sourdough  = productRepository.save(new RegularCake("Sourdough Loaf",   "Naturally leavened, crusty sourdough bread",              BigDecimal.valueOf(800),  "https://images.unsplash.com/photo-1585478259715-876acc5be8eb?w=600", breads, "Sourdough"));
        Product baguette   = productRepository.save(new RegularCake("French Baguette",  "Classic long and crusty loaf, made fresh daily",          BigDecimal.valueOf(400),  "https://images.unsplash.com/photo-1627308595229-7830a5c91f9f?w=600", breads, "Plain"));
        Product ciabatta   = productRepository.save(new RegularCake("Ciabatta",         "Italian rustic bread with a light, chewy texture",        BigDecimal.valueOf(500),  "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=600", breads, "Olive Oil"));
        Product multigrain = productRepository.save(new RegularCake("Multigrain Bread", "Hearty loaf packed with seeds, oats, and wholegrains",    BigDecimal.valueOf(650),  "https://images.unsplash.com/photo-1586444248902-2f64eddc8df3?w=600", breads, "Multigrain"));

        // ── CUPCAKES ─────────────────────────────────────────────────────────────
        Product assortedCups = productRepository.save(new RegularCake("Assorted Cupcake Box",       "A beautiful box of our signature assorted cupcakes",          BigDecimal.valueOf(1200), "https://images.unsplash.com/photo-1563729784474-d77dbb933a9e?w=600", cupcakes, "Assorted"));
        Product chocCupcake  = productRepository.save(new RegularCake("Chocolate Mud Cupcake",      "Dense chocolate cake with rich fudge frosting",               BigDecimal.valueOf(300),  "https://images.unsplash.com/photo-1550617931-e17a7b70dce2?w=600",  cupcakes, "Chocolate"));
        Product strawCupcake = productRepository.save(new RegularCake("Strawberry Lemonade Cupcake","Lemon sponge topped with sweet strawberry buttercream",       BigDecimal.valueOf(350),  "https://images.unsplash.com/photo-1576618148400-f54bed99fcfd?w=600", cupcakes, "Lemon Strawberry"));
        Product caramelCup   = productRepository.save(new RegularCake("Caramel Apple Cupcake",      "Spiced apple cake with golden caramel drizzle topping",       BigDecimal.valueOf(350),  "https://images.unsplash.com/photo-1614707267537-b85aaf00c4b7?w=600", cupcakes, "Caramel Apple"));

        // ── COOKIES & MUFFINS ────────────────────────────────────────────────────
        Product chocChip   = productRepository.save(new RegularCake("Chocolate Chip Cookies", "Classic chewy cookies loaded with dark chocolate chips",  BigDecimal.valueOf(200), "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=600", cookies, "Chocolate Chip"));
        Product blueberryM = productRepository.save(new RegularCake("Blueberry Muffin",       "Jumbo, fluffy muffin bursting with fresh blueberries",    BigDecimal.valueOf(280), "https://images.unsplash.com/photo-1607958996333-41aef7caefaa?w=600", cookies, "Blueberry"));
        Product doubleChoc = productRepository.save(new RegularCake("Double Chocolate Cookie","Rich chocolate dough with white chocolate chunks",        BigDecimal.valueOf(250), "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=600",  cookies, "Double Chocolate"));
        Product oatmeal    = productRepository.save(new RegularCake("Oatmeal Raisin Cookie",  "Soft oatmeal cookie with plump golden raisins",           BigDecimal.valueOf(200), "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=600",  cookies, "Oatmeal Raisin"));
        Product macadamia  = productRepository.save(new RegularCake("Macadamia Nut Cookie",   "Buttery cookie packed with white chocolate and macadamia", BigDecimal.valueOf(350), "https://images.unsplash.com/photo-1618923850107-d1a234d7a73a?w=600", cookies, "Macadamia"));

        // ── USERS ────────────────────────────────────────────────────────────────
        AdminUser    admin  = (AdminUser)    userRepository.save(new AdminUser("admin",  "admin@sweetbites.lk", "admin123", true));
        CustomerUser dinura = (CustomerUser) userRepository.save(new CustomerUser("dinura", "dinura@gmail.com", "pass123"));
        CustomerUser amaya  = (CustomerUser) userRepository.save(new CustomerUser("amaya",  "amaya@gmail.com",  "pass123"));
        CustomerUser kasun  = (CustomerUser) userRepository.save(new CustomerUser("kasun",  "kasun@gmail.com",  "pass123"));
        CustomerUser nethmi = (CustomerUser) userRepository.save(new CustomerUser("nethmi", "nethmi@gmail.com", "pass123"));
        CustomerUser saman  = (CustomerUser) userRepository.save(new CustomerUser("saman",  "saman@gmail.com",  "pass123"));

        // ── ORDERS ───────────────────────────────────────────────────────────────
        Order o1 = new Order(); o1.setUser(dinura); o1.setStatus(OrderStatus.DELIVERED);
        o1.setDeliveryAddress("45 Galle Road, Colombo 03"); o1.setNotes("Please ring doorbell");
        Order saved1 = orderRepository.save(o1);
        saved1.getItems().addAll(List.of(new OrderItem(saved1, chocCake, 1), new OrderItem(saved1, chocChip, 3)));
        saved1.setTotalAmount(chocCake.getPrice().add(chocChip.getPrice().multiply(BigDecimal.valueOf(3))));
        orderRepository.save(saved1);

        Order o2 = new Order(); o2.setUser(amaya); o2.setStatus(OrderStatus.PROCESSING);
        o2.setDeliveryAddress("12 Ward Place, Colombo 07"); o2.setNotes("No nuts please");
        Order saved2 = orderRepository.save(o2);
        saved2.getItems().addAll(List.of(new OrderItem(saved2, assortedCups, 2), new OrderItem(saved2, vanCake, 1)));
        saved2.setTotalAmount(assortedCups.getPrice().multiply(BigDecimal.valueOf(2)).add(vanCake.getPrice()));
        orderRepository.save(saved2);

        Order o3 = new Order(); o3.setUser(kasun); o3.setStatus(OrderStatus.PENDING);
        o3.setDeliveryAddress("88 High Level Road, Nugegoda");
        Order saved3 = orderRepository.save(o3);
        saved3.getItems().addAll(List.of(new OrderItem(saved3, sourdough, 2), new OrderItem(saved3, croissant, 4), new OrderItem(saved3, eclair, 2)));
        saved3.setTotalAmount(sourdough.getPrice().multiply(BigDecimal.valueOf(2)).add(croissant.getPrice().multiply(BigDecimal.valueOf(4))).add(eclair.getPrice().multiply(BigDecimal.valueOf(2))));
        orderRepository.save(saved3);

        Order o4 = new Order(); o4.setUser(nethmi); o4.setStatus(OrderStatus.DELIVERED);
        o4.setDeliveryAddress("3 Flower Road, Colombo 07"); o4.setNotes("Gift wrap please");
        Order saved4 = orderRepository.save(o4);
        saved4.getItems().addAll(List.of(new OrderItem(saved4, redVelvet, 1), new OrderItem(saved4, blueberryM, 3)));
        saved4.setTotalAmount(redVelvet.getPrice().add(blueberryM.getPrice().multiply(BigDecimal.valueOf(3))));
        orderRepository.save(saved4);

        // ── CUSTOM CAKE BOOKINGS ─────────────────────────────────────────────────
        CustomCake customB1 = new BirthdayCake("Custom Birthday – Amaya", "3kg birthday cake, round, chocolate", BigDecimal.valueOf(3000), "/uploads/images/IMG-20260514-WA0026.jpg", custom, "Chocolate", "Happy Birthday Amaya!", "Round", 3.0, 24);
        productRepository.save(customB1);
        CustomCake customW1 = new WeddingCake("Custom Wedding – Kasun & Nethmi", "5-tier white vanilla wedding cake", BigDecimal.valueOf(25000), "/uploads/images/IMG-20260514-WA0027.jpg", custom, "Vanilla", "Love Forever", "Hexagonal", 12.0, 5, true);
        productRepository.save(customW1);
        CustomCake customB2 = new BirthdayCake("Custom Birthday – Saman", "2kg red velvet birthday cake, square", BigDecimal.valueOf(2800), "/uploads/images/IMG-20260514-WA0028.jpg", custom, "Red Velvet", "Happy Birthday Saman!", "Square", 2.0, 18);
        productRepository.save(customB2);

        bookingRepository.save(new CustomCakeBooking(amaya, customB1, LocalDate.now().plusDays(5)));
        CustomCakeBooking wb1 = new CustomCakeBooking(kasun, customW1, LocalDate.now().plusDays(30));
        wb1.setBookingStatus("CONFIRMED");
        bookingRepository.save(wb1);
        bookingRepository.save(new CustomCakeBooking(saman, customB2, LocalDate.now().plusDays(3)));

        // ── REVIEWS ──────────────────────────────────────────────────────────────
        reviewRepository.save(new CustomerReview(dinura,  chocCake,    5, "Absolutely divine! The ganache is so rich and smooth."));
        reviewRepository.save(new CustomerReview(amaya,   vanCake,     4, "Light and fluffy, just the right amount of sweetness!"));
        reviewRepository.save(new CustomerReview(kasun,   redVelvet,   5, "Best red velvet in Colombo, no doubt. Cream cheese was perfect."));
        reviewRepository.save(new CustomerReview(nethmi,  caramelCake, 4, "The caramel drizzle is incredible. Will definitely order again."));
        reviewRepository.save(new CustomerReview(saman,   lemonCake,   5, "So refreshing and tangy! A perfect summer cake."));
        reviewRepository.save(new CustomerReview(dinura,  croissant,   5, "Perfectly flaky and buttery. As good as Paris!"));
        reviewRepository.save(new CustomerReview(amaya,   almondCro,   4, "Love the almond cream filling — very authentic."));
        reviewRepository.save(new CustomerReview(kasun,   sourdough,   5, "Amazing crust and chewy inside. Best sourdough I've had locally!"));
        reviewRepository.save(new CustomerReview(nethmi,  assortedCups,5, "Ordered these for my daughter's party — everyone loved them!"));
        reviewRepository.save(new CustomerReview(saman,   chocChip,    4, "Thick, chewy and packed with chocolate. A solid cookie."));
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // FIX IMAGE PATHS (runs when data already exists — just updates images)
    // ─────────────────────────────────────────────────────────────────────────────
    private void fixProductImages() {
        // Map of product name fragment → correct image URL
        java.util.Map<String, String> imageMap = new java.util.LinkedHashMap<>();
        imageMap.put("Chocolate Fudge",             "/uploads/images/chocolate_fudge.png");
        imageMap.put("Vanilla Bean",                "/uploads/images/vanilla_cake.png");
        imageMap.put("Red Velvet",                  "/uploads/images/red_velvet.png");
        imageMap.put("Golden Caramel",              "/uploads/images/caramel_cake.png");
        imageMap.put("Lemon Zest",                  "/uploads/images/lemon_cake.png");
        imageMap.put("Strawberry Cream",            "/uploads/images/strawberry_cake.png");
        imageMap.put("Black Forest",                "https://images.unsplash.com/photo-1588195538326-c5b1e9f80a1b?w=600");
        imageMap.put("Birthday Chocolate",          "/uploads/images/IMG-20260514-WA0029.jpg");
        imageMap.put("Wedding Rose",                "/uploads/images/IMG-20260514-WA0030.jpg");
        imageMap.put("Butter Croissant",            "https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=600");
        imageMap.put("Pain au Chocolat",            "https://images.unsplash.com/photo-1549903072-7e6e0bedb7fb?w=600");
        imageMap.put("Almond Croissant",            "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=600");
        imageMap.put("Éclair",                      "https://images.unsplash.com/photo-1587314168485-3236d6710814?w=600");
        imageMap.put("Sourdough Loaf",              "https://images.unsplash.com/photo-1585478259715-876acc5be8eb?w=600");
        imageMap.put("French Baguette",             "https://images.unsplash.com/photo-1627308595229-7830a5c91f9f?w=600");
        imageMap.put("Ciabatta",                    "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=600");
        imageMap.put("Multigrain Bread",            "https://images.unsplash.com/photo-1586444248902-2f64eddc8df3?w=600");
        imageMap.put("Assorted Cupcake",            "https://images.unsplash.com/photo-1563729784474-d77dbb933a9e?w=600");
        imageMap.put("Chocolate Mud Cupcake",       "https://images.unsplash.com/photo-1550617931-e17a7b70dce2?w=600");
        imageMap.put("Strawberry Lemonade Cupcake", "https://images.unsplash.com/photo-1576618148400-f54bed99fcfd?w=600");
        imageMap.put("Caramel Apple Cupcake",       "https://images.unsplash.com/photo-1614707267537-b85aaf00c4b7?w=600");
        imageMap.put("Chocolate Chip Cookies",      "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=600");
        imageMap.put("Blueberry Muffin",            "https://images.unsplash.com/photo-1607958996333-41aef7caefaa?w=600");
        imageMap.put("Double Chocolate Cookie",     "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=600");
        imageMap.put("Oatmeal Raisin Cookie",       "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=600");
        imageMap.put("Macadamia Nut Cookie",        "https://images.unsplash.com/photo-1618923850107-d1a234d7a73a?w=600");
        imageMap.put("Custom Birthday",             "/uploads/images/IMG-20260514-WA0026.jpg");
        imageMap.put("Custom Wedding",              "/uploads/images/IMG-20260514-WA0027.jpg");

        List<Product> products = productRepository.findAll();
        for (Product p : products) {
            for (java.util.Map.Entry<String, String> entry : imageMap.entrySet()) {
                if (p.getName() != null && p.getName().contains(entry.getKey())) {
                    // Always overwrite image with the correct one
                    if (p.getImageUrls() == null) p.setImageUrls(new java.util.ArrayList<>());
                    p.getImageUrls().clear();
                    p.getImageUrls().add(entry.getValue());
                    productRepository.save(p);
                    break;
                }
            }
        }

        // Admin user guard
        if (userRepository.count() == 0) {
            userRepository.save(new AdminUser("admin", "admin@sweetbites.lk", "admin123", true));
        }
    }
}
