package linepaytest.LinePayDemo.Controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import linepaytest.LinePayDemo.Model.CartItem;
import linepaytest.LinePayDemo.Model.Product;
import linepaytest.LinePayDemo.Service.CartService;
import linepaytest.LinePayDemo.Service.ProductService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    // 導向靜態資源頁面
    @GetMapping
    public ResponseEntity<Void> redirectToShopPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/ShopPage.html"); // 導向靜態頁面
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }
    
    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart (@RequestBody List<CartItem> items) {
        items.forEach(cartService::addItemToCart);
        if (items.isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }
        return ResponseEntity.ok("Add to cart successfully");
    }
    
    @GetMapping("/cart/items")
    public List<CartItem> getCartItems() {
        return cartService.getCartItems();
    }
    
    @PostMapping("/cart/clear")
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Cart is cleared");
    }
    
}
