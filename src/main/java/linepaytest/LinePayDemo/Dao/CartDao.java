package linepaytest.LinePayDemo.Dao;

import java.util.List;

import linepaytest.LinePayDemo.Model.CartItem;

public interface CartDao {
    
    void addItem(CartItem items);

    List<CartItem> getCartItems();

    void clearCart();
}
