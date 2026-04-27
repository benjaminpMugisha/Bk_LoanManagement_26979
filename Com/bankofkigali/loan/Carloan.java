package com.bankofkigali.loans;

import com.bankofkigali.models.LoanManager;
import com.bankofkigali.models.Customer;

public class CarLoan extends LoanManager {

    // ── Specific Attribute ───────────────────────────────────────────────────
    private String vehicleModel; // e.g., Toyota RAV4 2022

    // ── Default Constructor ──────────────────────────────────────────────────
    public CarLoan() {
        super();
        setLoanType("Car Loan");
        this.vehicleModel = "Unknown";
    }

    // ── Parameterised Constructor ────────────────────────────────────────────
    public CarLoan(String loanId, double amount, double rate, int duration,
                   String officer, String branch, String vehicleModel) {
        super(loanId, "Car Loan", amount, rate, duration, officer, branch);
        this.vehicleModel = vehicleModel;
    }

    // ── Getter & Setter ──────────────────────────────────────────────────────
    public String getVehicleModel()             { return vehicleModel; }
    public void   setVehicleModel(String model) { this.vehicleModel = model; }

    // ── Overridden Methods ───────────────────────────────────────────────────

    /** Max 30,000,000 RWF and max 48-month term for car loans. */
    @Override
    public boolean checkEligibility(Customer customer) {
        return super.checkEligibility(customer)
                && getLoanAmount() <= 30_000_000
                && getLoanDuration() <= 48;
    }

    @Override
    public void rejectLoan() {
        System.out.println("  [CarLoan] Vehicle inspection failed or criteria not met.");
        super.rejectLoan();
    }

    @Override
    public String generateLoanReport() {
        return super.generateLoanReport()
                + "\n  Vehicle Model     : " + vehicleModel;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\n  Vehicle Model     : " + vehicleModel;
    }
}
