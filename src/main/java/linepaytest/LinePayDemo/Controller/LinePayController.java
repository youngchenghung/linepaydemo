package linepaytest.LinePayDemo.Controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import linepaytest.LinePayDemo.Model.CartItem;
import linepaytest.LinePayDemo.Model.LinePayConfirm;
import linepaytest.LinePayDemo.Model.LinePayRequest;
import linepaytest.LinePayDemo.Model.LinePayResponse;
import linepaytest.LinePayDemo.Service.CartService;
import linepaytest.LinePayDemo.Service.LinePayService;


@RestController
@RequestMapping("/linepay")
public class LinePayController {

    @Autowired
    private LinePayService linePayService;

    @Autowired
    private CartService cartService;

    // 導向靜態資源頁面
    @GetMapping
    public ResponseEntity<Void> redirectToPaymentPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/LinePayPage.html"); // 導向靜態頁面
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    // LinePay API #1付款請求
    @PostMapping("/request")
    public ResponseEntity<LinePayResponse> requestPayment() {
        System.out.println("LinePay API #1付款請求");

        List<CartItem> cartItems = cartService.getCartItems(); // 取得購物車商品
        LinePayResponse linePayResponse = linePayService.initiatePayment(cartItems); // 呼叫 initiatePayment API
        return ResponseEntity.ok(linePayResponse);
    } 

    // LinePay API #2付款確認 (callback transactionId 參數)
    // LinePay API #3查詢付款狀態
    @GetMapping("/redirect")
    public ResponseEntity<String> handleRedirect(@RequestParam String transactionId) {
        System.out.println("LinePay API #2付款確認");
        System.out.println("Transaction ID: " + transactionId); // 取得 transaction ID

        if (transactionId == null) {
            return ResponseEntity.badRequest().body("Invalid transaction ID");
        }

        // 呼叫 confirmPayment API 確認付款
        List<CartItem> cartItems = cartService.getCartItems(); // 取得購物車商品
        String ConfirmResult = linePayService.confirmPayment(transactionId, cartItems);
        System.out.println("Confirm result: " + ConfirmResult);

        // 判斷 LinePay server 回傳碼是否為 0000
        if (ConfirmResult.contains("\"returnCode\":\"0000\"")) {
            System.out.println("LinePay API #3查詢付款狀態");
            String StatusResult = linePayService.getPaymentStatus(transactionId);
            System.out.println("Status result: " + StatusResult);
            
            return ResponseEntity.ok("Payment status. Result: " + StatusResult);
        }

        else {
            return ResponseEntity.badRequest().body("Payment status failed.\nResult: " + ConfirmResult);
        }
    }
}
