package com.bankofkigali.loans;

import com.bankofkigali.models.LoanManager;
import com.bankofkigali.models.Customer;


public class BusinessLoan extends LoanManager {

    // ── Specific Attribute ───────────────────────────────────────────────────
    private String collateralType; // e.g., Land, Equipment, Inventory

    // ── Default Constructor ──────────────────────────────────────────────────
    public BusinessLoan() {
        super();
        setLoanType("Business Loan");
        this.collateralType = "None";
    }

    // ── Parameterised Constructor ────────────────────────────────────────────
    public BusinessLoan(String loanId, double amount, double rate, int duration,
                        String officer, String branch, String collateralType) {
        super(loanId, "Business Loan", amount, rate, duration, officer, branch);
        this.collateralType = collateralType;
    }

    // ── Getter & Setter ──────────────────────────────────────────────────────
    public String getCollateralType()               { return collateralType; }
    public void   setCollateralType(String type)    { this.collateralType = type; }

    // ── Overridden Methods ───────────────────────────────────────────────────

    /** Collateral required and minimum duration of 12 months. */
    @Override
    public boolean checkEligibility(Customer customer) {
        return super.checkEligibility(customer)
                && !collateralType.equalsIgnoreCase("None")
                && getLoanDuration() >= 12;
    }

    /** Business loans use compound monthly interest for realistic SME lending. */
    @Override
    public double calculateInterest() {
        double monthlyRate = (getInterestRate() / 100) / 12;
        int    n           = getLoanDuration();
        return getLoanAmount() * (Math.pow(1 + monthlyRate, n) - 1);
    }

    @Override
    public void approveLoan() {
        System.out.println("  [BusinessLoan] Reviewing business plan and collateral documents...");
        super.approveLoan();
    }

    @Override
    public String generateLoanReport() {
        return super.generateLoanReport()
                + "\n  Collateral Type   : " + collateralType;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\n  Collateral Type   : " + collateralType;
    }
}
