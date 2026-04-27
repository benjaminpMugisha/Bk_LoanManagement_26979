package com.bankofkigali.models;

/**
 * Class: Customer
 * Represents a Bank of Kigali customer.
 * Demonstrates: Encapsulation
 * Package: com.bankofkigali.models
 */
public class Customer {

    // ── Private Attributes ───────────────────────────────────────────────────
    private String customerId;
    private String customerName;
    private String nationalId;
    private String phoneNumber;

    // ── Default Constructor ──────────────────────────────────────────────────
    public Customer() {
        this.customerId   = "CUST-000";
        this.customerName = "Unknown";
        this.nationalId   = "0000000000000000";
        this.phoneNumber  = "0700000000";
    }

    // ── Parameterised Constructor ────────────────────────────────────────────
    public Customer(String customerId, String customerName,
                    String nationalId, String phoneNumber) {
        this.customerId   = customerId;
        this.customerName = customerName;
        this.nationalId   = nationalId;
        this.phoneNumber  = phoneNumber;
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getCustomerId()   { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getNationalId()   { return nationalId; }
    public String getPhoneNumber()  { return phoneNumber; }

    // ── Setters ──────────────────────────────────────────────────────────────
    public void setCustomerId(String customerId)     { this.customerId = customerId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setNationalId(String nationalId)     { this.nationalId = nationalId; }
    public void setPhoneNumber(String phoneNumber)   { this.phoneNumber = phoneNumber; }

    // ── toString ─────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
                "╔══ CUSTOMER DETAILS ══════════════════════╗%n" +
                        "  Customer ID   : %s%n" +
                        "  Name          : %s%n" +
                        "  National ID   : %s%n" +
                        "  Phone         : %s%n" +
                        "╚══════════════════════════════════════════╝",
                customerId, customerName, nationalId, phoneNumber
        );
    }
}
