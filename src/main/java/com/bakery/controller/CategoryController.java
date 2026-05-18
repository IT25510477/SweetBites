package com.bakery.controller;

import com.bakery.model.Category;
import com.bakery.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import com.bakery.model.User;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private static final Path UPLOAD_ROOT = Paths.get("uploads", "images").toAbsolutePath();

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // UI 1: Category List (READ)
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category-list";
    }

    // UI 2: Add Category Form (CREATE)
    @GetMapping("/add")
    public String addForm(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/admin-login";
        return "category-add";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, 
                      @RequestParam String description,
                      @RequestParam(required = false) String imageUrl,
                      @RequestParam(required = false) MultipartFile imageFile,
                      HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/admin-login";

        Category category = new Category(name, description);
        if (imageFile != null && !imageFile.isEmpty()) {
            String savedPath = saveUploadedFile(imageFile);
            if (savedPath != null) category.setImageUrl(savedPath);
        } else if (imageUrl != null && !imageUrl.isBlank()) {
            category.setImageUrl(imageUrl);
        }

        categoryService.save(category);
        return "redirect:/category/list?success=created";
    }

    // UI 3: Edit Category Form (UPDATE)
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/admin-login";
        
        return categoryService.findById(id).map(c -> {
            model.addAttribute("category", c);
            return "category-edit";
        }).orElse("redirect:/category/list?error=notfound");
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @RequestParam String name,
                       @RequestParam String description,
                       @RequestParam(required = false) String imageUrl,
                       @RequestParam(required = false) MultipartFile imageFile,
                       HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/admin-login";

        categoryService.findById(id).ifPresent(c -> {
            c.setName(name);
            c.setDescription(description);
            if (imageFile != null && !imageFile.isEmpty()) {
                String savedPath = saveUploadedFile(imageFile);
                if (savedPath != null) c.setImageUrl(savedPath);
            } else if (imageUrl != null && !imageUrl.isBlank()) {
                c.setImageUrl(imageUrl);
            }
            categoryService.update(c);
        });
        return "redirect:/category/list?success=updated";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/admin-login";

        categoryService.deleteById(id);
        return "redirect:/category/list?success=deleted";
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
}
