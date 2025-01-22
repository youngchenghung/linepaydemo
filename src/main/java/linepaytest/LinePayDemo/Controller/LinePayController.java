package linepaytest.LinePayDemo.Controller;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import linepaytest.LinePayDemo.Model.LinePayConfirm;
import linepaytest.LinePayDemo.Model.LinePayRequest;
import linepaytest.LinePayDemo.Model.LinePayResponse;
import linepaytest.LinePayDemo.Service.LinePayService;


@RestController
@RequestMapping("/linepay")
public class LinePayController {

    @Autowired
    private LinePayService linePayService;

    // LinePayRequest 資料建立快取
    private ConcurrentHashMap<String, LinePayRequest> requestCache = new ConcurrentHashMap<>();

    // 導向靜態資源頁面
    @GetMapping
    public ResponseEntity<Void> redirectToPaymentPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/LinePayPage.html"); // 導向靜態頁面
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    // LinePay API #1付款請求
    @PostMapping("/request")
    public ResponseEntity<LinePayResponse> requestPayment(@RequestBody LinePayRequest linePayRequest) {
        System.out.println("LinePay API #1付款請求");

        LinePayResponse linePayResponse = linePayService.initiatePayment(linePayRequest);
        requestCache.put(linePayRequest.getOrderId(), linePayRequest); // 將訂單資訊存入快取
        return ResponseEntity.ok(linePayResponse);
    } 

    // LinePay API #2付款確認 (callback transactionId 參數)
    // LinePay API #3查詢付款狀態
    @GetMapping("/redirect")
    public ResponseEntity<String> handleRedirect(@RequestParam String transactionId, @RequestParam String orderId) {
        System.out.println("LinePay API #2付款確認");
        System.out.println("Transaction ID: " + transactionId); // 取得 transaction ID

        // 從快取中取得訂單資訊
        LinePayRequest linePayRequest = requestCache.get(orderId);
        if (linePayRequest == null) {
            return ResponseEntity.badRequest().body("Invalid transaction ID");
        }

        // 建立 LinePayConfirm 物件，設定 setAmount, setCurrency 值為 linePayRequest 快取資料
        LinePayConfirm linePayConfirm = new LinePayConfirm();
        linePayConfirm.setAmount(linePayRequest.getAmount());
        linePayConfirm.setCurrency(linePayRequest.getCurrency());
        System.out.println("linePayConfirm: " + linePayConfirm);

        // 呼叫 confirmPayment API 確認付款
        String ConfirmResult = linePayService.confirmPayment(transactionId, linePayConfirm);
        System.out.println("Confirm result: " + ConfirmResult);

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
