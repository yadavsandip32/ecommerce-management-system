package controller;

import com.ecommerce.common.model.Order;
import com.ecommerce.common.model.OrderRequest;
import com.ecommerce.common.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;
import com.ecommerce.common.model.Cart;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/products")
    public List<Product> viewProducts(@RequestParam(required = false) String category) {
        return customerService.getProducts(category);
    }

    @GetMapping("/products/{productId}")
    public Product viewProduct(@PathVariable Long productId) {
        return customerService.getProduct(productId);
    }

    @PostMapping("/cart/add/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable Long productId, @RequestBody Cart cart) {
        return customerService.addToCart(productId, cart);
    }

    @GetMapping("/cart")
    public Cart viewCart(@RequestParam Long customerId) {
        return customerService.getCart(customerId);
    }

    @PostMapping("/order/place")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        return customerService.placeOrder(orderRequest);
    }

    @GetMapping("/orders")
    public List<Order> viewOrderHistory(@RequestParam Long customerId) {
        return customerService.getOrderHistory(customerId);
    }
}
