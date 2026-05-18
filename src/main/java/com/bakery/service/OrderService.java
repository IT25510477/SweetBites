package com.bakery.service;

import com.bakery.model.*;
import com.bakery.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderService extends AbstractCrudService<Order, Long> {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    public List<Order> findByUser(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    public Order updateStatus(Long orderId, OrderStatus status) {
        return orderRepository.findById(orderId).map(order -> {
            order.setStatus(status);
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
    }

    public BigDecimal calculateTotal(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order getActiveCart(User user) {
        System.out.println("DEBUG: getActiveCart for user=" + user.getUsername());
        List<Order> carts = orderRepository.findByUserAndStatus(user, OrderStatus.CART);
        if (!carts.isEmpty()) {
            System.out.println("DEBUG: Found existing cart with ID=" + carts.get(0).getId());
            return carts.get(0);
        }
        System.out.println("DEBUG: No cart found, creating new one");
        Order newCart = new Order();
        newCart.setUser(user);
        newCart.setStatus(OrderStatus.CART);
        Order saved = orderRepository.save(newCart);
        System.out.println("DEBUG: New cart saved with ID=" + saved.getId());
        return saved;
    }

    public void recalculateTotal(Order order) {
        order.setTotalAmount(calculateTotal(order.getItems()));
        orderRepository.save(order);
    }

    public void clearCart(User user) {
        Order cart = getActiveCart(user);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        orderRepository.save(cart);
    }
}
