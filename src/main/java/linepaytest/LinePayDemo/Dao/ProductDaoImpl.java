package linepaytest.LinePayDemo.Dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import linepaytest.LinePayDemo.Model.Product;

@Component
public class ProductDaoImpl implements ProductDao {

    @Override
    public List<Product> getProducts() {
        return Arrays.asList(new Product("1", "杯子", 100),new Product("2", "盤子", 200));
    }
    
}
