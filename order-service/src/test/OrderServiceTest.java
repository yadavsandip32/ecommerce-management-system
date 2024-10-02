package com.ecommerce.orderservice.service;

import com.ecommerce.common.exception.OrderNotFoundException;
import com.ecommerce.common.model.Order;
import com.ecommerce.common.model.OrderRequest;
import com.ecommerce.common.model.Product;
import com.ecommerce.common.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    private Order order;
    private OrderRequest orderRequest;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product(1L, 10);  // 10 units in stock
        orderRequest = new OrderRequest(1L, Arrays.asList(product));

        order = new Order();
        order.setCustomerId(1L);
        order.setQuantity(5); // Ordering 5 units
        order.setTotalPrice(500.0);
    }

    @Test
    public void testPlaceOrder_Success() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order placedOrder = orderService.placeOrder(orderRequest);

        assertNotNull(placedOrder);
        assertEquals(1L, placedOrder.getCustomerId());
        assertEquals(5, placedOrder.getQuantity());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testGetOrder_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrder(1L);

        assertNotNull(foundOrder);
        assertEquals(1L, foundOrder.getCustomerId());
        assertEquals(5, foundOrder.getQuantity());
    }

    @Test
    public void testGetOrder_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrder(1L));
    }

    @Test
    public void testGetOrderHistory_Success() {
        when(orderRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(order));

        List<Order> orderHistory = orderService.getOrderHistory(1L);

        assertNotNull(orderHistory);
        assertFalse(orderHistory.isEmpty());
        assertEquals(1L, orderHistory.get(0).getCustomerId());
    }

    @Test
    public void testGetOrderHistory_NotFound() {
        when(orderRepository.findByCustomerId(1L)).thenReturn(Arrays.asList());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderHistory(1L));
    }

    // Test case to simulate multiple customers placing orders and stock being limited
    @Test
    public void testPlaceOrder_ExceedStock() {
        product.setStock(10); // Only 10 units in stock

        // Simulate two orders trying to place a total of 15 units
        OrderRequest firstOrderRequest = new OrderRequest(1L, Arrays.asList(product));
        OrderRequest secondOrderRequest = new OrderRequest(2L, Arrays.asList(product));

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order ord = invocation.getArgument(0);
            if (ord.getQuantity() > product.getQuantity()) {
                throw new RuntimeException("Product out of stock");
            }
            return ord;
        });

        // First order succeeds
        Order placedOrder1 = orderService.placeOrder(firstOrderRequest);
        assertEquals(1L, placedOrder1.getCustomerId());
        assertEquals(5, placedOrder1.getQuantity());

        // Second order exceeds stock
        assertThrows(RuntimeException.class, () -> orderService.placeOrder(secondOrderRequest));
    }
}
