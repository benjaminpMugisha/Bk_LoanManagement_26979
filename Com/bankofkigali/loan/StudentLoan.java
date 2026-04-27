package com.bankofkigali.loans;

import com.bankofkigali.models.LoanManager;
import com.bankofkigali.models.Customer;

public class StudentLoan extends LoanManager {

    // ── Specific Attribute ───────────────────────────────────────────────────
    private String universityName; // e.g., AUCA, University of Rwanda

    // ── Default Constructor ──────────────────────────────────────────────────
    public StudentLoan() {
        super();
        setLoanType("Student Loan");
        this.universityName = "Unknown University";
    }

    // ── Parameterised Constructor ────────────────────────────────────────────
    public StudentLoan(String loanId, double amount, double rate, int duration,
                       String officer, String branch, String universityName) {
        super(loanId, "Student Loan", amount, rate, duration, officer, branch);
        this.universityName = universityName;
    }

    // ── Getter & Setter ──────────────────────────────────────────────────────
    public String getUniversityName()              { return universityName; }
    public void   setUniversityName(String name)   { this.universityName = name; }

    // ── Overridden Methods ───────────────────────────────────────────────────

    /** Max 10,000,000 RWF and a valid university name is required. */
    @Override
    public boolean checkEligibility(Customer customer) {
        return super.checkEligibility(customer)
                && getLoanAmount() <= 10_000_000
                && universityName != null
                && !universityName.isBlank();
    }

    /** Students receive a 50% government subsidy on the interest rate. */
    @Override
    public double calculateInterest() {
        double subsidisedRate = getInterestRate() * 0.5;
        double years          = getLoanDuration() / 12.0;
        return getLoanAmount() * (subsidisedRate / 100) * years;
    }

    @Override
    public void approveLoan() {
        System.out.println("  [StudentLoan] Verifying enrolment at " + universityName + "...");
        super.approveLoan();
    }

    @Override
    public String generateLoanReport() {
        return super.generateLoanReport()
                + "\n  University        : " + universityName;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\n  University        : " + universityName;
    }
}
