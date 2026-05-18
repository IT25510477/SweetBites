package com.bakery.controller;

import com.bakery.model.User;
import com.bakery.repository.CustomCakeBookingRepository;
import com.bakery.repository.OrderRepository;
import com.bakery.repository.ReviewRepository;
import com.bakery.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final CustomCakeBookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ReviewService reviewService;
    private final ContactMessageService messageService;
    private final CategoryService categoryService;
    private final CustomCakeBookingService bookingService;

    public AdminController(ProductService productService,
                           OrderRepository orderRepository,
                           CustomCakeBookingRepository bookingRepository,
                           ReviewRepository reviewRepository,
                           UserService userService,
                           ReviewService reviewService,
                           ContactMessageService messageService,
                           CategoryService categoryService,
                           CustomCakeBookingService bookingService) {
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.reviewService = reviewService;
        this.messageService = messageService;
        this.categoryService = categoryService;
        this.bookingService = bookingService;
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "admin".equals(user.getRole());
    }

    // ── DASHBOARD ──────────────────────────────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("totalProducts", productService.findAll().size());
        model.addAttribute("totalOrders", orderRepository.count());
        model.addAttribute("totalBookings", bookingRepository.count());
        model.addAttribute("totalReviews", reviewRepository.count());
        model.addAttribute("totalUsers", userService.findAll().size());
        model.addAttribute("totalMessages", messageService.findAll().size());

        java.math.BigDecimal totalRevenue = orderRepository.findAll().stream()
                .map(com.bakery.model.Order::getTotalAmount)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        model.addAttribute("totalRevenue", totalRevenue);

        model.addAttribute("recentOrders", orderRepository.findAll().stream()
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .limit(5).collect(java.util.stream.Collectors.toList()));

        model.addAttribute("recentMessages", messageService.findAll().stream()
                .sorted((m1, m2) -> m2.getCreatedAt().compareTo(m1.getCreatedAt()))
                .limit(5).collect(java.util.stream.Collectors.toList()));

        model.addAttribute("pendingBookings", bookingRepository.findAll().stream()
                .filter(b -> "PENDING".equals(b.getBookingStatus())).count());

        // For management panels
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allCategories", categoryService.findAll());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allBookings", bookingRepository.findAll().stream()
                .sorted((b1, b2) -> b2.getCreatedAt().compareTo(b1.getCreatedAt()))
                .collect(java.util.stream.Collectors.toList()));

        return "admin-dashboard";
    }

    // ── USER MANAGEMENT ────────────────────────────────────────────────────────
    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("allUsers", userService.findAll());
        // Also add other dashboard stats
        return "redirect:/admin/dashboard#users-section";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable(name = "id") Long id,
                             @RequestParam(name = "username") String username,
                             @RequestParam(name = "email") String email,
                             HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        userService.findById(id).ifPresent(u -> {
            u.setUsername(username);
            u.setEmail(email);
            userService.update(u);
        });
        return "redirect:/admin/dashboard?success=user_updated#users-section";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        // Don't allow deletion of the admin account
        userService.findById(id).ifPresent(u -> {
            if (!"admin".equals(u.getRole())) {
                userService.deleteById(id);
            }
        });
        return "redirect:/admin/dashboard?success=user_deleted#users-section";
    }

    // ── CUSTOM BOOKING MANAGEMENT (ACCEPT / REJECT) ────────────────────────────
    @GetMapping("/bookings/accept/{id}")
    public String acceptBooking(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        bookingService.findById(id).ifPresent(b -> {
            b.setBookingStatus("CONFIRMED");
            bookingService.update(b);
        });
        return "redirect:/admin/dashboard?success=booking_accepted#bookings-section";
    }

    @GetMapping("/bookings/reject/{id}")
    public String rejectBooking(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        bookingService.findById(id).ifPresent(b -> {
            b.setBookingStatus("CANCELLED");
            bookingService.update(b);
        });
        return "redirect:/admin/dashboard?success=booking_rejected#bookings-section";
    }

    @GetMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        bookingService.deleteById(id);
        return "redirect:/admin/dashboard?success=booking_deleted#bookings-section";
    }

    // ── REVIEWS ────────────────────────────────────────────────────────────────
    @GetMapping("/reviews")
    public String reviews(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("reviews", reviewService.findAll());
        return "admin-reviews";
    }

    // ── MESSAGES ───────────────────────────────────────────────────────────────
    @GetMapping("/messages")
    public String viewMessages(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("messages", messageService.findAllSorted());
        return "admin-messages";
    }

    @GetMapping("/messages/read/{id}")
    public String markMessageRead(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        messageService.findById(id).ifPresent(m -> {
            m.setReadStatus(true);
            messageService.update(m);
        });
        return "redirect:/admin/messages?success=read";
    }

    @GetMapping("/messages/delete/{id}")
    public String deleteMessage(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        messageService.deleteById(id);
        return "redirect:/admin/messages?success=deleted";
    }

    // ── ADMIN REGISTRATION ────────────────────────────────────────────────────
    @GetMapping("/register")
    public String showAdminRegister(HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        return "admin-register";
    }

    @PostMapping("/register")
    public String registerAdmin(@RequestParam(name = "username") String username,
                                @RequestParam(name = "email") String email,
                                @RequestParam(name = "password") String password,
                                HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        
        com.bakery.model.AdminUser newAdmin = new com.bakery.model.AdminUser(username, email, password);
        userService.save(newAdmin);
        
        return "redirect:/admin/dashboard?success=admin_registered#users-section";
    }
}
