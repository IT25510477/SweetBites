package com.bakery.controller;

import com.bakery.model.Category;
import com.bakery.model.Product;
import com.bakery.model.User;
import com.bakery.service.CategoryService;
import com.bakery.service.ProductService;
import com.bakery.service.ReviewService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Path UPLOAD_ROOT = Paths.get("uploads", "images").toAbsolutePath();

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;

    public ProductController(ProductService productService, CategoryService categoryService, ReviewService reviewService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.reviewService = reviewService;
    }

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword,
                       @RequestParam(required = false) Long categoryId,
                       HttpSession session,
                       Model model) {
        
        User user = (User) session.getAttribute("user");
        boolean isAdmin = user != null && "admin".equals(user.getRole());

        List<Product> products;
        if (categoryId != null) {
            products = categoryService.findById(categoryId)
                    .map(c -> isAdmin ? productService.findByCategory(c) : productService.findStandardProductsByCategory(c))
                    .orElse(isAdmin ? productService.findAll() : productService.findStandardProducts());
        } else {
            products = isAdmin ? productService.findAll() : productService.findStandardProducts();
        }

        if (keyword != null && !keyword.isBlank()) {
            String lowerKeyword = keyword.toLowerCase();
            products = products.stream()
                    .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(lowerKeyword))
                    .collect(java.util.stream.Collectors.toList());
        }
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCategoryId", categoryId);
        return "product-list";
    }

    @GetMapping("/view/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        return productService.findById(id).map(p -> {
            model.addAttribute("product", p);
            model.addAttribute("reviews", reviewService.findByProduct(p));
            return "product-detail";
        }).orElse("redirect:/product/list?error=notfound");
    }

    @GetMapping("/toggle-availability/{id}")
    public String toggleAvailability(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/admin-login";

        productService.findById(id).ifPresent(p -> {
            p.setAvailable(!p.isAvailable());
            productService.update(p);
        });
        return "redirect:/product/list?success=updated";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/admin-login";
        model.addAttribute("categories", categoryService.findAll());
        return "product-add";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name,
                      @RequestParam String description,
                      @RequestParam BigDecimal price,
                      @RequestParam(required = false) String imageUrl,
                      @RequestParam(required = false) MultipartFile[] imageFiles,
                      @RequestParam Long categoryId,
                      HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/admin-login";

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        categoryService.findById(categoryId).ifPresent(product::setCategory);

        List<String> urls = new ArrayList<>();
        if (imageUrl != null && !imageUrl.isBlank()) urls.add(imageUrl);
        if (imageFiles != null) {
            for (MultipartFile file : imageFiles) {
                String savedPath = saveUploadedFile(file);
                if (savedPath != null) urls.add(savedPath);
            }
        }
        product.setImageUrls(urls);
        productService.save(product);
        return "redirect:/product/list?success=created";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/admin-login";
        return productService.findById(id).map(p -> {
            model.addAttribute("product", p);
            model.addAttribute("categories", categoryService.findAll());
            return "product-edit";
        }).orElse("redirect:/product/list?error=notfound");
    }

    @PostMapping(value = "/edit/{id}", consumes = {"multipart/form-data", "application/x-www-form-urlencoded"})
    public String edit(@PathVariable Long id,
                       @RequestParam String name,
                       @RequestParam String description,
                       @RequestParam BigDecimal price,
                       @RequestParam(required = false) List<String> existingImageUrls,
                       @RequestParam(required = false) String newImageUrl,
                       @RequestParam(required = false) MultipartFile[] imageFiles,
                       @RequestParam Long categoryId,
                       @RequestParam(defaultValue = "false") boolean available,
                       HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/admin-login";

        productService.findById(id).ifPresent(p -> {
            p.setName(name);
            p.setDescription(description);
            p.setPrice(price);
            p.setAvailable(available);
            categoryService.findById(categoryId).ifPresent(p::setCategory);

            List<String> urls = new ArrayList<>();
            if (existingImageUrls != null) urls.addAll(existingImageUrls);
            if (newImageUrl != null && !newImageUrl.isBlank()) urls.add(newImageUrl);

            if (imageFiles != null) {
                for (MultipartFile file : imageFiles) {
                    String savedPath = saveUploadedFile(file);
                    if (savedPath != null) urls.add(savedPath);
                }
            }
            p.setImageUrls(urls);
            productService.update(p);
        });
        return "redirect:/product/list?success=updated";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/admin-login";
        productService.deleteById(id);
        return "redirect:/product/list?success=deleted";
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
            
            // Modern way to transfer file
            file.transferTo(dest);
            System.out.println("DEBUG: Saved file to: " + dest.toString());
            
            return "/uploads/images/" + fileName;
        } catch (IOException e) {
            System.err.println("DEBUG: Failed to save file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
