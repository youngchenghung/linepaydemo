package linepaytest.LinePayDemo.Service;

import linepaytest.LinePayDemo.Model.LinePayConfirm;
import linepaytest.LinePayDemo.Model.LinePayRequest;
import linepaytest.LinePayDemo.Model.LinePayResponse;

public interface LinePayService {

    // LinePay API #1付款請求
    LinePayResponse initiatePayment();

    // LinePay API #2付款確認
    String confirmPayment(String transactionId, LinePayConfirm linePayConfirm);

    // LinePay API #3查詢付款狀態
    String getPaymentStatus(String transactionId);
    
}
