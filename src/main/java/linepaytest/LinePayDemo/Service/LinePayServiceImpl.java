package linepaytest.LinePayDemo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import linepaytest.LinePayDemo.Dao.LinePayDao;
import linepaytest.LinePayDemo.Model.LinePayConfirm;
import linepaytest.LinePayDemo.Model.LinePayRequest;
import linepaytest.LinePayDemo.Model.LinePayResponse;


@Component
public class LinePayServiceImpl implements LinePayService {
    
    @Autowired
    private LinePayDao linePayDao;
    
    // LinePay API #1付款請求
    @Override
    public LinePayResponse initiatePayment(LinePayRequest LinePayRequest) {
        return linePayDao.requestPayment(LinePayRequest);
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
