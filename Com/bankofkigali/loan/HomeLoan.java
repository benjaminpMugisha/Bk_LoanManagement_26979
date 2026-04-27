package com.bankofkigali.loans;

import com.bankofkigali.models.LoanManager;
import com.bankofkigali.models.Customer;

/**
 * Class: HomeLoan
 * Subclass of LoanManager for mortgage / property purchase loans.
 * Demonstrates: Inheritance, Method Overriding, Polymorphism
 * Package: com.bankofkigali.loans
 *
 * Specific attribute : propertyValue
 * Eligibility rules  : LTV must not exceed 80% of property value
 * Interest rule      : 0.5% rate discount applied
 */
public class HomeLoan extends LoanManager {

    // ── Specific Attribute ───────────────────────────────────────────────────
    private double propertyValue; // Market value of the property in RWF

    // ── Default Constructor ──────────────────────────────────────────────────
    public HomeLoan() {
        super();
        setLoanType("Home Loan");
        this.propertyValue = 0.0;
    }

    // ── Parameterised Constructor ────────────────────────────────────────────
    public HomeLoan(String loanId, double amount, double rate, int duration,
                    String officer, String branch, double propertyValue) {
        super(loanId, "Home Loan", amount, rate, duration, officer, branch);
        this.propertyValue = propertyValue;
    }

    // ── Getter & Setter ──────────────────────────────────────────────────────
    public double getPropertyValue()             { return propertyValue; }
    public void   setPropertyValue(double value) { this.propertyValue = value; }

    // ── Overridden Methods ───────────────────────────────────────────────────

    /** Loan-to-Value (LTV) must be ≤ 80% of property value. */
    @Override
    public boolean checkEligibility(Customer customer) {
        return super.checkEligibility(customer)
                && propertyValue > 0
                && getLoanAmount() <= propertyValue * 0.80;
    }

    /** Home loans receive a preferential 0.5% rate discount. */
    @Override
    public double calculateInterest() {
        double discountedRate = Math.max(0, getInterestRate() - 0.5);
        double years          = getLoanDuration() / 12.0;
        return getLoanAmount() * (discountedRate / 100) * years;
    }

    @Override
    public void approveLoan() {
        System.out.println("  [HomeLoan] Verifying property title documents...");
        super.approveLoan();
    }

    @Override
    public String generateLoanReport() {
        return super.generateLoanReport()
                + String.format("%n  Property Value    : %,.2f RWF", propertyValue);
    }

    @Override
    public String toString() {
        return super.toString()
                + String.format("%n  Property Value    : %,.2f RWF", propertyValue);
    }
}
