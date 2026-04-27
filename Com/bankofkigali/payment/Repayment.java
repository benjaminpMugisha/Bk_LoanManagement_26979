package com.bankofkigali.models;

import java.time.LocalDate;

/**
 * Class: Repayment
 * Records a single payment transaction made against a loan.
 * Demonstrates: Encapsulation, Association (has-a LoanManager)
 * Package: com.bankofkigali.models
 */
public class Repayment {

    // ── Private Attributes ───────────────────────────────────────────────────
    private String      paymentId;
    private LoanManager loan;
    private double      amountPaid;
    private String      paymentDate;
    private double      remainingBalance;

    // ── Default Constructor ──────────────────────────────────────────────────
    public Repayment() {
        this.paymentId        = "PAY-000";
        this.loan             = null;
        this.amountPaid       = 0.0;
        this.paymentDate      = LocalDate.now().toString();
        this.remainingBalance = 0.0;
    }

    // ── Parameterised Constructor ────────────────────────────────────────────
    public Repayment(String paymentId, LoanManager loan,
                     double amountPaid, String paymentDate) {
        this.paymentId   = paymentId;
        this.loan        = loan;
        this.amountPaid  = amountPaid;
        this.paymentDate = paymentDate;
        if (loan != null) {
            loan.processPayment(amountPaid);
            this.remainingBalance = loan.calculateRemainingBalance();
        }
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String      getPaymentId()        { return paymentId; }
    public LoanManager getLoan()             { return loan; }
    public double      getAmountPaid()       { return amountPaid; }
    public String      getPaymentDate()      { return paymentDate; }
    public double      getRemainingBalance() { return remainingBalance; }

    // ── Setters ──────────────────────────────────────────────────────────────
    public void setPaymentId(String paymentId)    { this.paymentId = paymentId; }
    public void setLoan(LoanManager loan)         { this.loan = loan; }
    public void setAmountPaid(double amountPaid)  { this.amountPaid = amountPaid; }
    public void setPaymentDate(String paymentDate){ this.paymentDate = paymentDate; }

    /**
     * Updates the remaining balance after an additional payment.
     * @param additionalPayment the extra amount being paid
     */
    public void updateRemainingBalance(double additionalPayment) {
        if (loan != null) {
            loan.processPayment(additionalPayment);
            this.amountPaid       += additionalPayment;
            this.remainingBalance  = loan.calculateRemainingBalance();
            System.out.printf("  Balance updated → Remaining: %,.2f RWF%n", remainingBalance);
        }
    }

    // ── toString ─────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        String loanId = (loan != null) ? loan.getLoanId() : "N/A";
        return String.format(
                "╔══ REPAYMENT RECORD ══════════════════════════╗%n" +
                        "  Payment ID        : %s%n" +
                        "  Loan ID           : %s%n" +
                        "  Amount Paid (RWF) : %,.2f%n" +
                        "  Payment Date      : %s%n" +
                        "  Remaining Balance : %,.2f RWF%n" +
                        "╚══════════════════════════════════════════════╝",
                paymentId, loanId, amountPaid, paymentDate, remainingBalance
        );
    }
}
