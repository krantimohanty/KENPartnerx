package com.kencloud.partner.user.app_model;

/**
 * Created by suchismita.p on 11/23/2016.
 */

public class NewOrderModel {
    String orderNo, customerName, status, amount, paymentMode;


   public NewOrderModel(){

    }
    public NewOrderModel(String orderNo, String customerName, String status, String amount, String paymentMode){

        this.orderNo=orderNo;
        this.customerName=customerName;
        this.status=status;
        this.amount=amount;
        this.paymentMode=paymentMode;


    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}
