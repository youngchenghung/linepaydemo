package linepaytest.LinePayDemo.Service;

import java.util.List;

import linepaytest.LinePayDemo.Model.CartItem;

public interface CartService {
    
    void addItemToCart(CartItem items);

    List<CartItem> getCartItems();

    void clearCart();
}
