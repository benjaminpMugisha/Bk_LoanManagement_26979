package com.bankofkigali.loans;

import com.bankofkigali.models.LoanManager;
import com.bankofkigali.models.Customer;

/**
 * Class: AgriculturalLoan
 * Subclass of LoanManager for farm and agri-business loans.
 * Demonstrates: Inheritance, Method Overriding, Polymorphism
 * Package: com.bankofkigali.loans
 *
 * Specific attribute : farmSize (hectares)
 * Eligibility rules  : min 0.5 ha farm size | max 50,000,000 RWF
 * Interest rule      : seasonal – effective 75% of duration
 */
public class AgriculturalLoan extends LoanManager {

    // ── Specific Attribute ───────────────────────────────────────────────────
    private double farmSize; // in hectares

    // ── Default Constructor ──────────────────────────────────────────────────
    public AgriculturalLoan() {
        super();
        setLoanType("Agricultural Loan");
        this.farmSize = 0.0;
    }

    // ── Parameterised Constructor ────────────────────────────────────────────
    public AgriculturalLoan(String loanId, double amount, double rate, int duration,
                            String officer, String branch, double farmSize) {
        super(loanId, "Agricultural Loan", amount, rate, duration, officer, branch);
        this.farmSize = farmSize;
    }

    // ── Getter & Setter ──────────────────────────────────────────────────────
    public double getFarmSize()            { return farmSize; }
    public void   setFarmSize(double size) { this.farmSize = size; }

    // ── Overridden Methods ───────────────────────────────────────────────────

    /** Minimum 0.5 ha farm and max 50,000,000 RWF loan amount. */
    @Override
    public boolean checkEligibility(Customer customer) {
        return super.checkEligibility(customer)
                && farmSize >= 0.5
                && getLoanAmount() <= 50_000_000;
    }

    /** Seasonal interest: charged only for 75% of loan duration (9 of 12 months/year). */
    @Override
    public double calculateInterest() {
        double effectiveMonths = getLoanDuration() * 0.75;
        double years           = effectiveMonths / 12.0;
        return getLoanAmount() * (getInterestRate() / 100) * years;
    }

    @Override
    public void approveLoan() {
        System.out.println("  [AgriculturalLoan] Verifying farm land title and size...");
        super.approveLoan();
    }

    @Override
    public String generateLoanReport() {
        return super.generateLoanReport()
                + String.format("%n  Farm Size         : %.2f hectares", farmSize);
    }

    @Override
    public String toString() {
        return super.toString()
                + String.format("%n  Farm Size         : %.2f hectares", farmSize);
    }
}
