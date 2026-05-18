package com.bakery.controller;

import com.bakery.model.*;
import com.bakery.service.CustomCakeBookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequestMapping("/custom-booking")
public class CustomCakeBookingController {

    private final CustomCakeBookingService bookingService;
    private static final Path UPLOAD_ROOT = Paths.get("uploads", "images").toAbsolutePath();

    public CustomCakeBookingController(CustomCakeBookingService bookingService) {
        this.bookingService = bookingService;
    }

    // UI 1: Request List Page (Read)
    @GetMapping("/list")
    public String viewBookings(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if ("admin".equals(user.getRole())) {
            model.addAttribute("bookings", bookingService.findAll());
        } else {
            model.addAttribute("bookings", bookingService.findByUser(user));
        }
        return "custom-booking-list";
    }

    // UI 2: Booking Page (Create)
    @GetMapping("/new")
    public String newBookingForm(HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        return "custom-booking-add";
    }

    @PostMapping("/new")
    public String createBooking(
            @RequestParam String cakeType, // "BIRTHDAY" or "WEDDING"
            @RequestParam String flavor,
            @RequestParam String customMessage,
            @RequestParam String shape,
            @RequestParam Double weightInKg,
            @RequestParam(required = false) Integer numberOfCandles,
            @RequestParam(required = false) Integer tiers,
            @RequestParam(required = false) Boolean floralDecorations,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deliveryDate,
            @RequestParam(required = false) MultipartFile[] inspirationImages,
            @RequestParam(required = false) String specialNotes,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";


        // Sanitize inputs
        int candles = (numberOfCandles != null) ? numberOfCandles : 0;
        int t = (tiers != null) ? tiers : 0;
        boolean floral = (floralDecorations != null) ? floralDecorations : false;
        double weight = (weightInKg != null) ? weightInKg : 1.0;

        CustomCake cake;
        if ("BIRTHDAY".equals(cakeType)) {
            cake = new BirthdayCake("Custom Birthday", "Bespoke Birthday Cake", BigDecimal.valueOf(1000.0), null, null, flavor, customMessage, shape, weight, candles);
        } else if ("WEDDING".equals(cakeType)) {
            cake = new WeddingCake("Custom Wedding", "Bespoke Wedding Cake", BigDecimal.valueOf(3000.0), null, null, flavor, customMessage, shape, weight, t, floral);
        } else {
            cake = new CustomCake("Custom " + cakeType, "Bespoke " + cakeType + " Cake", BigDecimal.valueOf(2000.0), null, null, flavor, customMessage, shape, weight);
        }

        CustomCakeBooking booking = new CustomCakeBooking(user, cake, deliveryDate);
        booking.setSpecialNotes(specialNotes);
        
        if (inspirationImages != null) {
            for (MultipartFile img : inspirationImages) {
                if (!img.isEmpty()) {
                    String savedPath = saveUploadedFile(img);
                    if (savedPath != null) {
                        booking.getInspirationImageUrls().add(savedPath);
                    }
                }
            }
        }
        
        bookingService.save(booking);

        return "redirect:/custom-booking/list?success=created";
    }

    // UI 3: Edit Request Page (Update)
    @GetMapping("/edit/{id}")
    public String editBookingForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        return bookingService.findById(id).map(booking -> {
            model.addAttribute("booking", booking);
            return "custom-booking-edit";
        }).orElse("redirect:/custom-booking/list");
    }

    @PostMapping("/edit/{id}")
    public String updateBooking(
            @PathVariable Long id,
            @RequestParam String flavor,
            @RequestParam String customMessage,
            @RequestParam String shape,
            @RequestParam double weightInKg,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deliveryDate,
            @RequestParam(required = false) String status, // Admin only
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        return bookingService.findById(id).map(booking -> {
            CustomCake cake = booking.getCustomCake();
            cake.setFlavor(flavor);
            cake.setCustomMessage(customMessage);
            cake.setShape(shape);
            cake.setWeightInKg(weightInKg);
            booking.setDeliveryDate(deliveryDate);

            if ("admin".equals(user.getRole()) && status != null) {
                booking.setBookingStatus(status);
            }

            bookingService.update(booking);
            return "redirect:/custom-booking/list?success=updated";
        }).orElse("redirect:/custom-booking/list");
    }

    // DELETE Booking
    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        bookingService.deleteById(id);
        return "redirect:/custom-booking/list?success=deleted";
    }

    private String saveUploadedFile(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        try {
            if (!Files.exists(UPLOAD_ROOT)) {
                Files.createDirectories(UPLOAD_ROOT);
            }
            String originalName = file.getOriginalFilename();
            String ext = (originalName != null && originalName.contains("."))
                    ? originalName.substring(originalName.lastIndexOf('.'))
                    : ".jpg";
            String fileName = UUID.randomUUID() + ext;
            Path dest = UPLOAD_ROOT.resolve(fileName);
            file.transferTo(dest);
            return "/uploads/images/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        System.err.println("CRITICAL ERROR in CustomCakeBookingController: " + e.getMessage());
        e.printStackTrace();
        return "redirect:/custom-booking/new?error=server_error&msg=" + e.getMessage();
    }
}
