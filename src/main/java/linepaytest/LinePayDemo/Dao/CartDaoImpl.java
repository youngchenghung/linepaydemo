package linepaytest.LinePayDemo.Dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import linepaytest.LinePayDemo.Model.CartItem;

@Component
public class CartDaoImpl implements CartDao {

    private final List<CartItem> cart = new ArrayList<>();

    @Override
    public void addItem(CartItem item){
        cart.add(item);
    }

    @Override
    public List<CartItem> getCartItems() {
        return new ArrayList<>(cart);
    }

    @Override
    public void clearCart() {
        cart.clear();
    }
    
}
