package linepaytest.LinePayDemo.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import linepaytest.LinePayDemo.Dao.CartDao;
import linepaytest.LinePayDemo.Dao.LinePayDao;
import linepaytest.LinePayDemo.Model.CartItem;
import linepaytest.LinePayDemo.Model.LinePayConfirm;
import linepaytest.LinePayDemo.Model.LinePayRequest;
import linepaytest.LinePayDemo.Model.LinePayResponse;


@Component
public class LinePayServiceImpl implements LinePayService {
    
    @Autowired

    private CartDao cartDao;

    private LinePayDao linePayDao;

    
    // LinePay API #1付款請求
    @Override
    public LinePayResponse initiatePayment() {
        // 取得購物車商品
        List<CartItem> cartItems = cartDao.getCartItems(); // 取得購物車商品
        int totalAmount = cartItems.stream().mapToInt(item -> item.getPrice() * item.getQuantity()).sum(); // 計算總金額

        // 組裝 LinePayRequest 中 Package 資訊
        LinePayRequest.Package packageInfo = new LinePayRequest.Package();
        packageInfo.setId("package_id");
        packageInfo.setName("Shop Cart");
        packageInfo.setAmount(totalAmount);
        packageInfo.setProducts(cartItems);

        // 組裝 LinePayRequest 中 RedirectUrls 資訊
        LinePayRequest.RedirectUrls redirectUrls = new LinePayRequest.RedirectUrls();
        redirectUrls.setConfirmUrl("https://http:www.google.com/confirm");
        redirectUrls.setCancelUrl("https://www.example.com/cancel");

        // 組裝 LinePayRequest
        LinePayRequest linePayRequest = new LinePayRequest();
        String orderId = UUID.randomUUID().toString(); // 產生 orderId
        linePayRequest.setAmount(totalAmount);
        linePayRequest.setCurrency("TWD");
        linePayRequest.setOrderId(orderId);
        linePayRequest.setPackages(List.of(packageInfo)); // 將 packageInfo 加入 packages
        linePayRequest.setRedirectUrls(redirectUrls); // 將 redirectUrls 加入 redirectUrls

        return linePayDao.initiatePayment(linePayRequest); // 呼叫 LinePayDao 的 initiatePayment 方法
    }

    // LinePay API #2付款確認
    @Override
    public String confirmPayment(String transactionId, LinePayConfirm linePayConfirm) {
        return linePayDao.confirmTransaction(transactionId, linePayConfirm);
    }
    
    // LinePay API #3查詢付款狀態
    @Override
    public String getPaymentStatus(String transactionId) {
        return linePayDao.getPaymentStatus(transactionId);
    }
    
}
