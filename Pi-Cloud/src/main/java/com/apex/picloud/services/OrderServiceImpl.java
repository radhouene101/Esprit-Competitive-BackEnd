package com.apex.picloud.services;

import com.apex.picloud.models.CartItem;
import com.apex.picloud.models.Customer;
import com.apex.picloud.models.Order;
import com.apex.picloud.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService; // Inject CustomerService

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    @Override
    public Order createOrder(Order order) {
        // Check if customer exists
        Long customerId = order.getCustomer().getId();
        Optional<Customer> existingCustomerOptional = customerService.getCustomerById(customerId);
        if (existingCustomerOptional.isPresent()) {
            // Customer exists, update order with existing customer
            Customer existingCustomer = existingCustomerOptional.get();
            order.setCustomer(existingCustomer);
        } else {
            // Customer doesn't exist, create a new one
            Customer newCustomer = customerService.createCustomer(order.getCustomer());
            order.setCustomer(newCustomer); // Update order with the newly created customer
        }

        // Set the order reference for each OrderCustomer
        for (CartItem cartItem : order.getOrderCustomer()) {
            cartItem.setOrder(order);
        }

        // Now, all OrderCustomer instances have the order reference set
        // You can proceed with saving the Order entity
        return orderRepository.save(order);
    }



    @Override
    public Order updateOrder(Long id, Order orderDetails) {
        // Retrieve the existing order by id
        Optional<Order> orderOptional = orderRepository.findById(id);

        // Ensure the order exists before updating
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            // Update the order's attributes with the provided details
            order.setPaymentMethod(orderDetails.getPaymentMethod());
            order.setOrderStatus(orderDetails.getOrderStatus());
            // ... update any other relevant fields

            // Save the updated order using the repository
            return orderRepository.save(order);
        } else {
            // Handle the case where the order is not found
            throw new ResourceNotFoundException("Order with id " + id + " not found");
        }
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
