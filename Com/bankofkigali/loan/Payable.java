package com.bankofkigali.core;

public interface Payable {
    boolean processPayment(double amount);
    double  calculateRemainingBalance();
    String  generatePaymentReceipt();
}
