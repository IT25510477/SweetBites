package com.bakery.controller;

import com.bakery.model.*;
import com.bakery.service.ProductService;
import com.bakery.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;

    public ReviewController(ReviewService reviewService, ProductService productService) {
        this.reviewService = reviewService;
        this.productService = productService;
    }

    // UI 1: View Reviews for a product
    @GetMapping("/product/{productId}")
    public String viewReviews(@PathVariable Long productId, Model model) {
        return productService.findById(productId).map(product -> {
            model.addAttribute("product", product);
            model.addAttribute("reviews", reviewService.findByProduct(product));
            return "review-list";
        }).orElse("redirect:/product/list");
    }

    // UI 2: Submit Review
    @GetMapping("/add/{productId}")
    public String submitReviewForm(@PathVariable Long productId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        return productService.findById(productId).map(product -> {
            model.addAttribute("product", product);
            return "review-add";
        }).orElse("redirect:/product/list");
    }

    @PostMapping("/add/{productId}")
    public String submitReview(@PathVariable Long productId, @RequestParam int rating, @RequestParam String comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        return productService.findById(productId).map(product -> {
            Review review;
            if ("admin".equals(user.getRole())) {
                review = new AdminReview(user, product, rating, comment, "Moderator");
            } else {
                review = new CustomerReview(user, product, rating, comment);
            }
            reviewService.save(review);
            return "redirect:/review/product/" + productId + "?success=true";
        }).orElse("redirect:/product/list");
    }

    // UI 3: Edit Review
    @GetMapping("/edit/{id}")
    public String editReviewForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        return reviewService.findById(id).map(review -> {
            if (!review.getUser().getId().equals(user.getId()) && !"admin".equals(user.getRole())) {
                return "redirect:/product/list";
            }
            model.addAttribute("review", review);
            return "review-edit";
        }).orElse("redirect:/product/list");
    }

    @PostMapping("/edit/{id}")
    public String editReview(@PathVariable Long id, @RequestParam int rating, @RequestParam String comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        return reviewService.findById(id).map(review -> {
            if (!review.getUser().getId().equals(user.getId()) && !"admin".equals(user.getRole())) {
                return "redirect:/product/list";
            }
            review.setRating(rating);
            review.setComment(comment);
            reviewService.update(review);
            return "redirect:/review/product/" + review.getProduct().getId() + "?success=updated";
        }).orElse("redirect:/product/list");
    }

    // DELETE Review
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        return reviewService.findById(id).map(review -> {
            Long productId = review.getProduct().getId();
            if (review.getUser().getId().equals(user.getId()) || "admin".equals(user.getRole())) {
                reviewService.deleteById(id);
            }
            return "redirect:/review/product/" + productId + "?success=deleted";
        }).orElse("redirect:/product/list");
    }
}
