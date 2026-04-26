package com.loanService;

import com.models.Customer;
import com.models.LoanManager;

/**
 * Interface: LoanService
 * Defines the business-logic contract for loan operations.
 * Implemented by LoanServiceImplementation.
 * Package: com.loanService
 */
public interface LoanService {

    /**
     * Checks whether the customer's account balance is sufficient
     * to support the requested loan amount (e.g. covers first instalment).
     *
     * @param balance         customer's current account balance in RWF
     * @param requestedAmount the loan amount being requested
     * @return true if balance is sufficient, false otherwise
     */
    boolean checkBalance(double balance, double requestedAmount);

    /**
     * Checks whether the customer meets the general eligibility criteria
     * for the requested loan amount (income ratio, history, etc.).
     *
     * @param balance         customer's current account balance in RWF
     * @param requestedAmount the loan amount being requested
     * @return true if eligible, false otherwise
     */
    boolean checkEligibility(double balance, double requestedAmount);

    /**
     * Checks the customer's Credit Reference Bureau (CRB) status.
     * A clean CRB record is required for loan approval.
     *
     * @param customerId the unique customer identifier
     * @return true if CRB status is clean, false if blacklisted
     */
    boolean checkCRBStatus(String customerId);

    /**
     * Processes a loan application end-to-end:
     * validates details, checks eligibility, and approves or rejects.
     *
     * @param loan     the loan to process
     * @param customer the applicant
     * @return true if loan was approved, false if rejected
     */
    boolean processLoanApplication(LoanManager loan, Customer customer);

    /**
     * Processes a repayment against an approved loan.
     *
     * @param loan          the loan being paid
     * @param paymentAmount amount being paid in RWF
     * @return true if payment was accepted, false otherwise
     */
    boolean processRepayment(LoanManager loan, double paymentAmount);

    /**
     * Calculates and returns the outstanding balance on a loan.
     *
     * @param loan the loan to query
     * @return remaining balance in RWF
     */
    double getOutstandingBalance(LoanManager loan);
}
