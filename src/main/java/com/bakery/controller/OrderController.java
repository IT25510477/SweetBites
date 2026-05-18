package com.bakery.controller;

import com.bakery.model.*;
import com.bakery.service.OrderService;
import com.bakery.service.ProductService;
import com.bakery.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    public OrderController(OrderService orderService, ProductService productService, UserService userService) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }

    // ==========================================
    // CART ENDPOINTS
    // ==========================================

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        System.out.println("DEBUG: viewCart called, user=" + (user != null ? user.getUsername() : "null"));
        if (user == null) return "redirect:/login";

        Order cart = orderService.getActiveCart(user);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam int quantity,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println("DEBUG: addToCart called for productId=" + productId + ", quantity=" + quantity + ", user=" + (user != null ? user.getUsername() : "null"));
        if (user == null) return "redirect:/login";

        return productService.findById(productId).map(product -> {
            Order cart = orderService.getActiveCart(user);
            
            // Check if product is already in cart
            Optional<OrderItem> existingItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

            if (existingItem.isPresent()) {
                OrderItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + quantity);
            } else {
                OrderItem newItem = new OrderItem(cart, product, quantity);
                cart.getItems().add(newItem);
            }
            
            orderService.recalculateTotal(cart);
            System.out.println("DEBUG: Item added/updated, redirecting to /cart");
            return "redirect:/cart?success=added";
        }).orElseGet(() -> {
            System.out.println("DEBUG: Product not found, redirecting to menu");
            return "redirect:/product/list?error=notfound";
        });
    }

    @PostMapping("/cart/update")
    public String updateCartItem(@RequestParam Long itemId,
                                 @RequestParam int quantity,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Order cart = orderService.getActiveCart(user);
        cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
        
        orderService.recalculateTotal(cart);
        return "redirect:/cart?success=updated";
    }

    @PostMapping("/cart/remove")
    public String removeCartItem(@RequestParam Long itemId,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Order cart = orderService.getActiveCart(user);
        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        orderService.recalculateTotal(cart);
        
        return "redirect:/cart?success=removed";
    }

    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        orderService.clearCart(user);
        return "redirect:/cart?success=cleared";
    }

    @PostMapping("/cart/checkout")
    public String checkout(@RequestParam String deliveryAddress,
                           @RequestParam String contactNumber,
                           @RequestParam String paymentMethod,
                           @RequestParam(required = false) String notes,
                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Order cart = orderService.getActiveCart(user);
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart?error=empty";
        }

        cart.setDeliveryAddress(deliveryAddress);
        cart.setContactNumber(contactNumber);
        cart.setPaymentMethod(paymentMethod);
        cart.setNotes(notes);
        cart.setStatus(OrderStatus.PENDING);
        orderService.save(cart);

        return "redirect:/order/history?success=placed";
    }


    // ==========================================
    // ORDER MANAGEMENT ENDPOINTS
    // ==========================================

    @GetMapping("/order/history")
    public String history(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        System.out.println("DEBUG: history called for user=" + (user != null ? user.getUsername() : "null"));
        if (user == null) return "redirect:/login";
        
        // Admin doesn't place orders, redirect to management
        if ("admin".equals(user.getRole())) {
            return "redirect:/order/admin";
        }
        
        try {
            // Don't show CART status in order history
            java.util.List<Order> history = orderService.findByUser(user).stream()
                    .filter(o -> o.getStatus() != OrderStatus.CART)
                    .collect(java.util.stream.Collectors.toList());
            
            System.out.println("DEBUG: Found " + history.size() + " non-cart orders for history");
            model.addAttribute("orders", history);
            return "order-history";
        } catch (Exception e) {
            System.err.println("ERROR in order history: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/index?error=history_failed";
        }
    }

    @GetMapping("/order/view/{id}")
    public String viewOrder(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        return orderService.findById(id).map(o -> {
            // Security check: only owner or admin can view
            if (!o.getUser().getId().equals(user.getId()) && !"admin".equals(user.getRole())) {
                return "redirect:/order/history";
            }
            model.addAttribute("order", o);
            return "order-detail";
        }).orElse("redirect:/order/history?error=notfound");
    }

    // Edit Order (Customer)
    @GetMapping("/order/edit/{id}")
    public String editOrder(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        System.out.println("DEBUG: editOrder called for ID=" + id + ", user=" + (user != null ? user.getUsername() : "null"));
        if (user == null) return "redirect:/login";

        return orderService.findById(id).map(o -> {
            System.out.println("DEBUG: Found order for edit. Status=" + o.getStatus() + ", Owner ID=" + o.getUser().getId());
            // Security check: only allow owners to edit and only if PENDING
            if (!o.getUser().getId().equals(user.getId()) || o.getStatus() != OrderStatus.PENDING) {
                System.out.println("DEBUG: Unauthorized edit attempt or not PENDING");
                return "redirect:/order/history?error=unauthorized";
            }
            model.addAttribute("order", o);
            return "order-edit";
        }).orElseGet(() -> {
            System.out.println("DEBUG: Order not found: " + id);
            return "redirect:/order/history?error=notfound";
        });
    }

    @PostMapping("/order/edit/{id}")
    public String editOrderSubmit(@PathVariable Long id,
                                  @RequestParam String deliveryAddress,
                                  @RequestParam String contactNumber,
                                  @RequestParam String paymentMethod,
                                  @RequestParam(required = false) String notes,
                                  HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        return orderService.findById(id).map(o -> {
            if (!o.getUser().getId().equals(user.getId()) || o.getStatus() != OrderStatus.PENDING) {
                return "redirect:/order/history?error=cannotedit";
            }
            o.setDeliveryAddress(deliveryAddress);
            o.setContactNumber(contactNumber);
            o.setPaymentMethod(paymentMethod);
            o.setNotes(notes);
            orderService.save(o);
            return "redirect:/order/view/" + id + "?success=updated";
        }).orElse("redirect:/order/history?error=notfound");
    }

    // Update quantities within pending order
    @PostMapping("/order/update-item/{orderId}")
    public String updateOrderItemQuantity(@PathVariable Long orderId,
                                          @RequestParam Long itemId,
                                          @RequestParam int quantity,
                                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        return orderService.findById(orderId).map(o -> {
            if (!o.getUser().getId().equals(user.getId()) || o.getStatus() != OrderStatus.PENDING) {
                return "redirect:/order/history?error=cannotedit";
            }
            
            if (quantity <= 0) {
                o.getItems().removeIf(item -> item.getId().equals(itemId));
            } else {
                o.getItems().stream()
                        .filter(item -> item.getId().equals(itemId))
                        .findFirst()
                        .ifPresent(item -> item.setQuantity(quantity));
            }
            
            orderService.recalculateTotal(o);
            return "redirect:/order/edit/" + orderId + "?success=updated";
        }).orElse("redirect:/order/history?error=notfound");
    }

    // Cancel Order (Customer)
    @GetMapping("/order/cancel/{id}")
    public String cancelOrder(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        return orderService.findById(id).map(o -> {
            if (!o.getUser().getId().equals(user.getId()) || o.getStatus() != OrderStatus.PENDING) {
                return "redirect:/order/history?error=cannotcancel";
            }
            o.setStatus(OrderStatus.CANCELLED);
            orderService.save(o);
            return "redirect:/order/history?success=cancelled";
        }).orElse("redirect:/order/history?error=notfound");
    }

    // ==========================================
    // ADMIN ENDPOINTS
    // ==========================================

    @PostMapping("/order/status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam OrderStatus status, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/index";
        
        orderService.updateStatus(id, status);
        return "redirect:/order/admin?success=status_updated";
    }

    @GetMapping("/order/admin")
    public String adminOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/index";
        
        java.util.List<Order> nonCartOrders = orderService.findAllOrders().stream()
                .filter(o -> o.getStatus() != OrderStatus.CART)
                .collect(java.util.stream.Collectors.toList());
                
        model.addAttribute("orders", nonCartOrders);
        model.addAttribute("statuses", OrderStatus.values()); // might want to filter CART out here too in the UI
        return "order-admin";
    }

    @GetMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) return "redirect:/index";
        
        orderService.deleteById(id);
        return "redirect:/order/admin?success=deleted";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        System.err.println("CRITICAL ERROR in OrderController: " + e.getMessage());
        e.printStackTrace();
        return "redirect:/index?error=order_error";
    }
}
