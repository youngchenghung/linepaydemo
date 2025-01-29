package linepaytest.LinePayDemo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import linepaytest.LinePayDemo.Dao.CartDao;
import linepaytest.LinePayDemo.Model.CartItem;

@Component
public class CartServiceImpl implements CartService {

    @Autowired 
    private CartDao cartDao;

    @Override
    public void addItemToCart(CartItem item) {
        cartDao.addItem(item);
    }
    
}
