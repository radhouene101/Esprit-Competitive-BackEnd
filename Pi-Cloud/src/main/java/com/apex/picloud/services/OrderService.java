package com.apex.picloud.services;

import com.apex.picloud.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();
    Optional<Order> getOrderById(Long id);
    Order createOrder(Order order);
    Order updateOrder(Long id, Order orderDetails);
    void deleteOrder(Long id);
}
