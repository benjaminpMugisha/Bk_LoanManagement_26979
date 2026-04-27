package com.bankofkigali.loans;

import com.bankofkigali.models.LoanManager;
import com.bankofkigali.models.Customer;

/**
 * Class: PersonalLoan
 * Subclass of LoanManager for personal/consumer loans.
 * Demonstrates: Inheritance, Method Overriding, Polymorphism
 * Package: com.bankofkigali.loans
 *
 * Specific attribute : purposeOfLoan
 * Eligibility rules  : max 5,000,000 RWF | max 60 months
 */
public class PersonalLoan extends LoanManager {

    // ── Specific Attribute ───────────────────────────────────────────────────
    private String purposeOfLoan; // e.g., Medical, Travel, Education

    // ── Default Constructor ──────────────────────────────────────────────────
    public PersonalLoan() {
        super();
        setLoanType("Personal Loan");
        this.purposeOfLoan = "General";
    }

    // ── Parameterised Constructor ────────────────────────────────────────────
    public PersonalLoan(String loanId, double amount, double rate, int duration,
                        String officer, String branch, String purposeOfLoan) {
        super(loanId, "Personal Loan", amount, rate, duration, officer, branch);
        this.purposeOfLoan = purposeOfLoan;
    }

    // ── Getter & Setter ──────────────────────────────────────────────────────
    public String getPurposeOfLoan()              { return purposeOfLoan; }
    public void   setPurposeOfLoan(String purpose){ this.purposeOfLoan = purpose; }

    // ── Overridden Methods ───────────────────────────────────────────────────

    /** Max 5,000,000 RWF and max 60 months for personal loans. */
    @Override
    public boolean checkEligibility(Customer customer) {
        return super.checkEligibility(customer)
                && getLoanAmount() <= 5_000_000
                && getLoanDuration() <= 60;
    }

    @Override
    public void approveLoan() {
        System.out.println("  [PersonalLoan] Running personal loan approval checks...");
        super.approveLoan();
    }

    @Override
    public String generateLoanReport() {
        return super.generateLoanReport()
                + "\n  Purpose           : " + purposeOfLoan;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\n  Purpose           : " + purposeOfLoan;
    }
}
