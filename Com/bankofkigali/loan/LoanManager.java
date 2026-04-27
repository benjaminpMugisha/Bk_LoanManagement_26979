package com.bankofkigali.models;

import com.bankofkigali.core.Loan;
import com.bankofkigali.core.Payable;
import java.time.LocalDate;


public class LoanManager extends Loan implements Payable {

    // ── Additional Attributes ────────────────────────────────────────────────
    private String officerName;
    private String branchLocation;
    private double totalPaid;

    // ── Default Constructor ──────────────────────────────────────────────────
    public LoanManager() {
        super();
        this.officerName    = "N/A";
        this.branchLocation = "Kigali HQ";
        this.totalPaid      = 0.0;
    }

    // ── Parameterised Constructor ────────────────────────────────────────────
    public LoanManager(String loanId, String loanType, double loanAmount,
                       double interestRate, int loanDuration,
                       String officerName, String branchLocation) {
        super(loanId, loanType, loanAmount, interestRate, loanDuration);
        this.officerName    = officerName;
        this.branchLocation = branchLocation;
        this.totalPaid      = 0.0;
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getOfficerName()    { return officerName; }
    public String getBranchLocation() { return branchLocation; }
    public double getTotalPaid()      { return totalPaid; }

    // ── Setters ──────────────────────────────────────────────────────────────
    public void setOfficerName(String officerName)       { this.officerName = officerName; }
    public void setBranchLocation(String branchLocation) { this.branchLocation = branchLocation; }
    public void setTotalPaid(double totalPaid)           { this.totalPaid = totalPaid; }

    // ══ Abstract Method Implementations ══════════════════════════════════════

    /**
     * Simple interest: I = P × R × T (years)
     */
    @Override
    public double calculateInterest() {
        double years = getLoanDuration() / 12.0;
        return getLoanAmount() * (getInterestRate() / 100) * years;
    }

    /**
     * Amortisation formula: M = P[r(1+r)^n] / [(1+r)^n – 1]
     */
    @Override
    public double calculateMonthlyInstallment() {
        double r = (getInterestRate() / 100) / 12;
        int    n = getLoanDuration();
        if (r == 0) return getLoanAmount() / n;
        double factor = Math.pow(1 + r, n);
        return getLoanAmount() * (r * factor) / (factor - 1);
    }

    @Override
    public boolean checkEligibility(Customer customer) {
        return getLoanAmount() > 0
                && getLoanDuration() >= 6
                && customer != null
                && customer.getCustomerName() != null
                && !customer.getCustomerName().isBlank();
    }

    @Override
    public void approveLoan() {
        setLoanStatus("APPROVED");
        System.out.println("  ✔ Loan [" + getLoanId() + "] APPROVED by " + officerName);
    }

    @Override
    public void rejectLoan() {
        setLoanStatus("REJECTED");
        System.out.println("  ✘ Loan [" + getLoanId() + "] REJECTED by " + officerName);
    }

    @Override
    public double calculateTotalRepayment() {
        return getLoanAmount() + calculateInterest();
    }

    @Override
    public String generateLoanReport() {
        return String.format(
                "%n╔══ LOAN REPORT ════════════════════════════════════╗%n" +
                        "  Loan ID            : %s%n" +
                        "  Type               : %s%n" +
                        "  Principal (RWF)    : %,.2f%n" +
                        "  Interest Rate      : %.2f%%%n" +
                        "  Duration           : %d months%n" +
                        "  Total Interest     : %,.2f RWF%n" +
                        "  Monthly Instalment : %,.2f RWF%n" +
                        "  Total Repayment    : %,.2f RWF%n" +
                        "  Status             : %s%n" +
                        "  Officer            : %s%n" +
                        "  Branch             : %s%n" +
                        "╚═══════════════════════════════════════════════════╝",
                getLoanId(), getLoanType(), getLoanAmount(),
                getInterestRate(), getLoanDuration(),
                calculateInterest(), calculateMonthlyInstallment(),
                calculateTotalRepayment(), getLoanStatus(),
                officerName, branchLocation
        );
    }

    @Override
    public boolean validateLoanDetails() {
        if (getLoanId() == null || getLoanId().isBlank()) {
            System.out.println("  ERROR: Loan ID cannot be empty.");
            return false;
        }
        if (getLoanAmount() <= 0) {
            System.out.println("  ERROR: Loan amount must be greater than zero.");
            return false;
        }
        if (getInterestRate() < 0 || getInterestRate() > 100) {
            System.out.println("  ERROR: Interest rate must be between 0 and 100.");
            return false;
        }
        if (getLoanDuration() < 1) {
            System.out.println("  ERROR: Loan duration must be at least 1 month.");
            return false;
        }
        return true;
    }

    // ══ Payable Interface Implementations ════════════════════════════════════

    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("  ERROR: Payment amount must be positive.");
            return false;
        }
        if (!"APPROVED".equals(getLoanStatus())) {
            System.out.println("  ERROR: Loan must be APPROVED before payments can be made.");
            return false;
        }
        double remaining = calculateRemainingBalance();
        totalPaid += Math.min(amount, remaining);
        System.out.printf("  ✔ Payment of %,.2f RWF accepted.%n", Math.min(amount, remaining));
        return true;
    }

    @Override
    public double calculateRemainingBalance() {
        return Math.max(0, calculateTotalRepayment() - totalPaid);
    }

    @Override
    public String generatePaymentReceipt() {
        return String.format(
                "%n╔══ PAYMENT RECEIPT ═══════════════════════════════════╗%n" +
                        "  Bank of Kigali – Official Receipt%n" +
                        "  Date               : %s%n" +
                        "  Loan ID            : %s%n" +
                        "  Total Repayment    : %,.2f RWF%n" +
                        "  Amount Paid So Far : %,.2f RWF%n" +
                        "  Remaining Balance  : %,.2f RWF%n" +
                        "  Branch             : %s%n" +
                        "╚══════════════════════════════════════════════════════╝",
                LocalDate.now(), getLoanId(),
                calculateTotalRepayment(), totalPaid,
                calculateRemainingBalance(), branchLocation
        );
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                "%n  Officer       : %s%n  Branch        : %s",
                officerName, branchLocation
        );
    }
}
