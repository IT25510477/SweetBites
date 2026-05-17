package com.bakery.controller;

import com.bakery.model.CustomerUser;
import com.bakery.model.ContactMessage;
import com.bakery.model.User;
import com.bakery.service.ContactMessageService;
import com.bakery.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private static final Path UPLOAD_ROOT = Paths.get("uploads", "images").toAbsolutePath();

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

    private final UserService userService;
    private final ContactMessageService messageService;

    public UserController(UserService userService, ContactMessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String index() { return "index"; }

    @GetMapping("/loading")
    public String showLoading() { return "loading"; }

    @GetMapping("/login")
    public String showLogin() { return "login"; }

    @GetMapping("/register")
    public String showRegister() { return "register"; }

    @GetMapping("/forgot-password")
    public String showForgotPassword() { return "forgot-password"; }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam(name = "email") String email,
                                       @RequestParam(name = "newPassword") String newPassword,
                                       @RequestParam(name = "confirmPassword") String confirmPassword,
                                       Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "forgot-password";
        }
        
        return userService.findByEmail(email).map(u -> {
            u.setPassword(newPassword);
            userService.update(u);
            model.addAttribute("success", "Password reset successfully! Please login with your new password.");
            return "forgot-password";
        }).orElseGet(() -> {
            model.addAttribute("error", "Email address not found!");
            return "forgot-password";
        });
    }

    @GetMapping("/admin-login")
    public String showAdminLogin() { return "admin-login"; }

    @GetMapping("/index")
    public String showIndex() { return "index"; }

    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public String handleUserRequest(
            @RequestParam(name = "action", defaultValue = "list") String action,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam(name = "currentPassword", required = false) String currentPassword,
            @RequestParam(name = "newPassword", required = false) String newPassword,
            @RequestParam(name = "profilePictureFile", required = false) MultipartFile profilePictureFile,
            HttpSession session, Model model) {

        switch (action) {
            case "list":
                model.addAttribute("users", userService.findAll());
                return "search";

            case "delete":
                if (id != null) userService.deleteById(id);
                return "redirect:/user?action=list";

            case "edit":
                User loggedInUser = (User) session.getAttribute("user");
                if (loggedInUser != null) {
                    return userService.findById(loggedInUser.getId()).map(u -> {
                        model.addAttribute("userToEdit", u);
                        return "edit";
                    }).orElse("redirect:/login");
                }
                return "redirect:/login";

            case "logout":
                session.invalidate();
                return "redirect:/index";

            case "register":
                CustomerUser newUser = new CustomerUser(username, email, password);
                userService.save(newUser);
                return "redirect:/login?success=registered";

            case "login":
                return userService.findByEmail(email).map(u -> {
                    if (u.authenticate(password)) {
                        session.setAttribute("user", u);
                        if ("admin".equals(u.getRole())) {
                            return "redirect:/admin/dashboard";
                        }
                        return "redirect:/index";
                    }
                    model.addAttribute("error", "Invalid email or password!");
                    return "login";
                }).orElseGet(() -> {
                    model.addAttribute("error", "Invalid email or password!");
                    return "login";
                });

            case "update-profile":
                return userService.findById(id).map(u -> {
                    u.setUsername(username);
                    u.setEmail(email);
                    if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
                        String filePath = saveUploadedFile(profilePictureFile);
                        if (filePath != null) {
                            u.setProfilePicture(filePath);
                        }
                    }
                    userService.update(u);
                    session.setAttribute("user", u);
                    return "redirect:/user?action=edit&success=profile_updated";
                }).orElse("redirect:/user?action=edit&error=profile_failed");

            case "update-password":
                return userService.findById(id).map(u -> {
                    if (u.authenticate(currentPassword)) {
                        if (newPassword != null && !newPassword.isEmpty()) {
                            u.setPassword(newPassword);
                            userService.update(u);
                            session.setAttribute("user", u);
                            return "redirect:/user?action=edit&success=password_updated";
                        }
                        return "redirect:/user?action=edit&error=password_empty";
                    }
                    return "redirect:/user?action=edit&error=invalid_password";
                }).orElse("redirect:/user?action=edit&error=password_failed");

            default:
                return "redirect:/index";
        }
    }

    @PostMapping("/contact/send")
    public String sendMessage(@RequestParam(name = "name") String name,
                              @RequestParam(name = "email") String email,
                              @RequestParam(name = "subject") String subject,
                              @RequestParam(name = "message") String message) {
        ContactMessage msg = new ContactMessage(name, email, subject, message);
        messageService.save(msg);
        return "redirect:/?success=sent#contact-us";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        System.err.println("CRITICAL ERROR in UserController: " + e.getMessage());
        e.printStackTrace();
        return "redirect:/?error=send_failed#contact-us";
    }
}
