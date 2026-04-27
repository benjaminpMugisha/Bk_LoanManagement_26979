package com.bankofkigali.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Class: InputValidator
 * Centralises all input validation for the Bank of Kigali LMS.
 * Handles empty inputs, bad formats, negatives, duplicates, and range errors.
 * Package: com.bankofkigali.utils
 */
public class InputValidator {

    // ── Session-scoped duplicate trackers ────────────────────────────────────
    private static final Set<String> usedLoanIds     = new HashSet<>();
    private static final Set<String> usedCustomerIds = new HashSet<>();

    // ── Accepted loan types ───────────────────────────────────────────────────
    private static final Set<String> VALID_LOAN_TYPES = Set.of(
            "personal", "home", "car", "business", "student", "agricultural"
    );

    // ── Empty / Null checks ───────────────────────────────────────────────────

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean validateNonEmpty(String value, String fieldName) {
        if (isEmpty(value)) {
            System.out.println("  ✘ ERROR: '" + fieldName + "' cannot be empty. Please enter a valid value.");
            return false;
        }
        return true;
    }

    // ── Numeric parsing ───────────────────────────────────────────────────────

    public static double parseDouble(String input, String fieldName) {
        if (isEmpty(input)) {
            System.out.println("  ✘ ERROR: '" + fieldName + "' cannot be empty.");
            return Double.NaN;
        }
        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            System.out.println("  ✘ ERROR: '" + fieldName + "' must be a number. You entered: \"" + input + "\"");
            return Double.NaN;
        }
    }

    public static int parseInt(String input, String fieldName) {
        if (isEmpty(input)) {
            System.out.println("  ✘ ERROR: '" + fieldName + "' cannot be empty.");
            return Integer.MIN_VALUE;
        }
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            System.out.println("  ✘ ERROR: '" + fieldName + "' must be a whole number. You entered: \"" + input + "\"");
            return Integer.MIN_VALUE;
        }
    }

    // ── Range & Sign checks ───────────────────────────────────────────────────

    public static boolean validatePositive(double value, String fieldName) {
        if (value <= 0) {
            System.out.printf("  ✘ ERROR: '%s' must be greater than zero. Got: %.2f%n", fieldName, value);
            return false;
        }
        return true;
    }

    public static boolean validateRange(double value, double min, double max, String fieldName) {
        if (value < min || value > max) {
            System.out.printf("  ✘ ERROR: '%s' must be between %.0f and %.0f. Got: %.0f%n",
                    fieldName, min, max, value);
            return false;
        }
        return true;
    }

    // ── Domain-specific validators ────────────────────────────────────────────

    /** Loan amount: must be positive and ≤ 500,000,000 RWF. */
    public static boolean validateLoanAmount(double amount) {
        if (amount <= 0) {
            System.out.println("  ✘ ERROR: Loan amount must be greater than 0 RWF.");
            return false;
        }
        if (amount > 500_000_000) {
            System.out.printf("  ✘ ERROR: Amount %,.0f RWF exceeds the system maximum of 500,000,000 RWF.%n", amount);
            return false;
        }
        return true;
    }

    /** Interest rate: must be between 0.10% and 35.00%. */
    public static boolean validateInterestRate(double rate) {
        if (rate < 0.1 || rate > 35.0) {
            System.out.printf("  ✘ ERROR: Interest rate %.2f%% is invalid. Must be between 0.10%% and 35.00%%.%n", rate);
            return false;
        }
        return true;
    }

    /**
     * Rwanda phone number format: starts with 072–079, exactly 10 digits.
     * Ex: 0788123456
     */
    public static boolean validatePhoneNumber(String phone) {
        if (isEmpty(phone)) {
            System.out.println("  ✘ ERROR: Phone number cannot be empty.");
            return false;
        }
        if (!phone.trim().matches("07[2-9]\\d{7}")) {
            System.out.println("  ✘ ERROR: Invalid phone number \"" + phone
                    + "\". Expected Rwandan format: 07XXXXXXXX (e.g. 0788123456).");
            return false;
        }
        return true;
    }

    /**
     * Rwanda National ID: exactly 16 digits.
     * Ex: 1199880012345678
     */
    public static boolean validateNationalId(String nid) {
        if (isEmpty(nid)) {
            System.out.println("  ✘ ERROR: National ID cannot be empty.");
            return false;
        }
        if (!nid.trim().matches("\\d{16}")) {
            System.out.println("  ✘ ERROR: National ID must be exactly 16 digits. Got: \"" + nid + "\"");
            return false;
        }
        return true;
    }

    /**
     * Loan ID format: LOAN-XXX (e.g. LOAN-001, LOAN-A12).
     * Also checks for duplicates within the session.
     */
    public static boolean validateLoanId(String loanId) {
        if (isEmpty(loanId)) {
            System.out.println("  ✘ ERROR: Loan ID cannot be empty.");
            return false;
        }
        String cleaned = loanId.trim().toUpperCase();
        if (!cleaned.matches("LOAN-[A-Z0-9]{3,10}")) {
            System.out.println("  ✘ ERROR: Invalid Loan ID \"" + loanId
                    + "\". Use format: LOAN-XXX (e.g. LOAN-001).");
            return false;
        }
        if (usedLoanIds.contains(cleaned)) {
            System.out.println("  ✘ ERROR: Loan ID \"" + cleaned + "\" is already in use. Please use a unique ID.");
            return false;
        }
        usedLoanIds.add(cleaned);
        return true;
    }

    /**
     * Customer ID format: CUST-XXX (e.g. CUST-001).
     * Also checks for duplicates within the session.
     */
    public static boolean validateCustomerId(String customerId) {
        if (isEmpty(customerId)) {
            System.out.println("  ✘ ERROR: Customer ID cannot be empty.");
            return false;
        }
        String cleaned = customerId.trim().toUpperCase();
        if (!cleaned.matches("CUST-[A-Z0-9]{3,10}")) {
            System.out.println("  ✘ ERROR: Invalid Customer ID \"" + customerId
                    + "\". Use format: CUST-XXX (e.g. CUST-001).");
            return false;
        }
        if (usedCustomerIds.contains(cleaned)) {
            System.out.println("  ✘ ERROR: Customer ID \"" + cleaned + "\" already exists.");
            return false;
        }
        usedCustomerIds.add(cleaned);
        return true;
    }

    /** Loan type must be one of the 6 supported types. */
    public static boolean validateLoanType(String loanType) {
        if (isEmpty(loanType)) {
            System.out.println("  ✘ ERROR: Loan type cannot be empty.");
            return false;
        }
        if (!VALID_LOAN_TYPES.contains(loanType.trim().toLowerCase())) {
            System.out.println("  ✘ ERROR: Unknown loan type \"" + loanType + "\".");
            System.out.println("           Valid types: personal, home, car, business, student, agricultural.");
            return false;
        }
        return true;
    }

    /** Payment must be positive and must not exceed remaining balance. */
    public static boolean validatePaymentAmount(double payment, double remainingBalance) {
        if (payment <= 0) {
            System.out.println("  ✘ ERROR: Payment amount must be greater than zero.");
            return false;
        }
        if (payment > remainingBalance) {
            System.out.printf("  ✘ ERROR: Payment %.2f RWF exceeds remaining balance %.2f RWF.%n",
                    payment, remainingBalance);
            return false;
        }
        return true;
    }
}
