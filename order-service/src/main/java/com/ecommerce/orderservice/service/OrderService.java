package com.ecommerce.orderservice.service;

import com.ecommerce.common.exception.OrderNotFoundException;
import com.ecommerce.common.model.OrderRequest;
import com.ecommerce.common.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.common.model.Order;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }


    public List<Order> getOrdersByCustomerId(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for customer ID: " + customerId);
        }
        return orders;
    }

    public Order placeOrder(OrderRequest orderRequest) {
//        return orderRepository.save(order);
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setCustomerId(orderRequest.getProductId());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());

        // Save the order in the repository
        return orderRepository.save(order);
    }

    public List<Order> getOrderHistory(Long customerId) {
        // Fetch order history for a specific customer using the customerId
        List<Order> orderHistory = orderRepository.findByCustomerId(customerId);

        // If no orders are found, you can handle it as needed (return empty list, throw exception, etc.)
        if (orderHistory.isEmpty()) {
            // You can throw an exception if required
            throw new OrderNotFoundException("No orders found for customer with ID: " + customerId);
        }

        return orderHistory;
    }
}
