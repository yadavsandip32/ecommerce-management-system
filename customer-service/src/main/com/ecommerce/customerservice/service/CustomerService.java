package service;

import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.common.model.Order;
import com.ecommerce.common.model.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import com.ecommerce.common.model.*;
import com.ecommerce.common.repository.*;

@Service
public class CustomerService {

    @Autowired
    private RestTemplate restTemplate;  // Communication with other services
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    public List<Product> getProducts(String category) {
        String url = "http://admin-service/admin/inventory?category=" + category;
        return Arrays.asList(restTemplate.getForObject(url, Product[].class));
    }

    public Product getProduct(Long productId) {
        String url = "http://admin-service/admin/inventory/" + productId;
        return restTemplate.getForObject(url, Product.class);
    }

    public ResponseEntity<String> addToCart(Long productId, Cart cart) {
        // Logic for adding product to cart
        com.ecommerce.common.model.Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Check if the cart already exists for the customer
        Cart existingCart = cartRepository.findByCustomerId(cart.getCustomerId());

        // Add the product to the cart
        existingCart.addProduct(product);

        // Save the updated cart
        cartRepository.save(existingCart);

        return ResponseEntity.ok("Product added to cart successfully.");

    }

    public Cart getCart(Long customerId) {
        // Logic for fetching cart
        return cartRepository.findByCustomerId(customerId);

    }

    public ResponseEntity<String> placeOrder(OrderRequest orderRequest) {
        String url = "http://order-service/order/place";
        return restTemplate.postForEntity(url, orderRequest, String.class);
    }

    public List<Order> getOrderHistory(Long customerId) {
        String url = "http://order-service/order/history/" + customerId;
        return Arrays.asList(restTemplate.getForObject(url, Order[].class));
    }
}

