package com.Main;

import com.loanService.LoanService;
import com.loanServiceImplementation.LoanServiceImplementation;
import com.models.Customer;
import com.models.LoanManager;
import com.models.Repayment;
import com.utils.InputValidator;
import com.utils.LoanFactory;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Class: Main
 * Entry point for the Bank of Kigali Loan Management System.
 * Uses LoanService for all business logic operations.
 *
 * Compile (from src/ folder):
 *   javac -d ../out $(find . -name "*.java")
 *
 * Run:
 *   java -cp ../out com.Main.Main
 *
 * Package: com.Main
 */
public class Main {

    private static final Scanner     sc          = new Scanner(System.in);
    private static final LoanService loanService = new LoanServiceImplementation();

    // ── Entry Point ───────────────────────────────────────────────────────────
    public static void main(String[] args) {
        printBanner();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": applyForLoan();                        break;
                case "2": makePayment();                         break;
                case "3": LoanFactory.printAvailableLoanTypes(); break;
                case "0":
                    System.out.println("\n  Thank you for using Bank of Kigali LMS. Goodbye!\n");
                    running = false;
                    break;
                default:
                    System.out.println("  ✘ Invalid option. Please enter 1, 2, 3, or 0.");
            }
        }
        sc.close();
    }

    // ── Banner & Menu ─────────────────────────────────────────────────────────

    static void printBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════════════╗");
        System.out.println("  ║        BANK OF KIGALI – LOAN MANAGEMENT SYSTEM          ║");
        System.out.println("  ║               Secure  |  Scalable  |  Reliable          ║");
        System.out.println("  ╚══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    static void printMenu() {
        System.out.println("  ┌─── MAIN MENU ─────────────────────────────────────┐");
        System.out.println("  │  1. Apply for a New Loan                          │");
        System.out.println("  │  2. Make a Loan Payment                           │");
        System.out.println("  │  3. View Available Loan Types                     │");
        System.out.println("  │  0. Exit                                          │");
        System.out.println("  └───────────────────────────────────────────────────┘");
        System.out.print("  Choice: ");
    }

    // ── Option 1: Apply for a Loan ────────────────────────────────────────────

    static void applyForLoan() {
        System.out.println("\n  ═══ NEW LOAN APPLICATION ════════════════════════════\n");

        // ── Step 1: Customer Information ─────────────────────────────────────
        System.out.println("  ─── Step 1: Customer Information ───");

        String custId   = promptString("  Customer ID   (e.g. CUST-001) : ",
                                        InputValidator::validateCustomerId);
        String custName = promptNonEmpty("  Customer Full Name            : ");
        String nid      = promptString("  National ID   (16 digits)     : ",
                                        InputValidator::validateNationalId);
        String phone    = promptString("  Phone Number  (07XXXXXXXX)    : ",
                                        InputValidator::validatePhoneNumber);

        Customer customer = new Customer(custId, custName, nid, phone);
        System.out.println("\n" + customer);

        // ── Step 2: Account Balance (used by LoanService checks) ─────────────
        double balance = promptDouble("  Account Balance (RWF)         : ",
                                      v -> InputValidator.validatePositive(v, "Account Balance"));

        // ── Step 3: Loan Details ─────────────────────────────────────────────
        System.out.println("\n  ─── Step 2: Loan Details ───");
        LoanFactory.printAvailableLoanTypes();

        String loanType = promptString("\n  Loan Type                     : ",
                                        InputValidator::validateLoanType);
        String loanId   = promptString("  Loan ID       (e.g. LOAN-001) : ",
                                        InputValidator::validateLoanId);
        double amount   = promptDouble("  Loan Amount   (RWF)           : ",
                                        InputValidator::validateLoanAmount);
        double rate     = promptDouble("  Interest Rate (%)             : ",
                                        InputValidator::validateInterestRate);
        int    duration = promptInt   ("  Duration      (months)        : ",
                                        v -> InputValidator.validateRange(v, 1, 480, "Duration"));
        String officer  = promptNonEmpty("  Loan Officer Name             : ");
        String branch   = promptNonEmpty("  Branch Location               : ");

        // ── Step 4: Type-specific field ───────────────────────────────────────
        String extra = collectExtra(loanType);

        // ── Step 5: LoanService – Balance & Eligibility checks ───────────────
        System.out.println("\n  ─── Running Service Checks ───");
        if (!loanService.checkBalance(balance, amount)) {
            System.out.println("  ✘ Application stopped: Insufficient account balance.");
            return;
        }
        if (!loanService.checkEligibility(balance, amount)) {
            System.out.println("  ✘ Application stopped: Eligibility criteria not met.");
            return;
        }

        // ── Step 6: Create loan via LoanFactory (Polymorphism) ───────────────
        LoanManager loan = LoanFactory.createLoan(
                loanType, loanId, amount, rate, duration, officer, branch, extra);

        if (loan == null) {
            System.out.println("  ✘ Loan creation failed. Returning to menu.");
            return;
        }

        // ── Step 7: Full application pipeline via LoanService ─────────────────
        boolean approved = loanService.processLoanApplication(loan, customer);

        // ── Step 8: Display result ────────────────────────────────────────────
        if (approved) {
            System.out.println(loan.generateLoanReport());
            System.out.printf("  Outstanding Balance: %,.2f RWF%n",
                loanService.getOutstandingBalance(loan));
        } else {
            System.out.println("  ✘ Loan application was not approved.");
        }
    }

    // ── Option 2: Make a Payment ──────────────────────────────────────────────

    static void makePayment() {
        System.out.println("\n  ═══ LOAN PAYMENT ════════════════════════════════════\n");

        String loanId   = promptNonEmpty("  Loan ID (reference)           : ");
        double amount   = promptDouble("  Loan Principal Amount (RWF)   : ",
                                        InputValidator::validateLoanAmount);
        double rate     = promptDouble("  Interest Rate (%)             : ",
                                        InputValidator::validateInterestRate);
        int    duration = promptInt("  Duration (months)             : ",
                                    v -> InputValidator.validateRange(v, 1, 480, "Duration"));

        // Reconstruct approved loan for payment demo
        LoanManager loan = new LoanManager(
                loanId, "General", amount, rate, duration, "Loan Officer", "Kigali HQ");
        loan.setLoanStatus("APPROVED");

        System.out.printf("%n  Total Repayment  : %,.2f RWF%n", loan.calculateTotalRepayment());
        System.out.printf("  Remaining Balance: %,.2f RWF%n%n",
                loanService.getOutstandingBalance(loan));

        double payment = promptDouble("  Payment Amount (RWF)          : ",
                p -> InputValidator.validatePaymentAmount(p, loanService.getOutstandingBalance(loan)));

        // Process via LoanService
        boolean success = loanService.processRepayment(loan, payment);

        if (success) {
            String payId = "PAY-" + (System.currentTimeMillis() % 100000);
            Repayment repayment = new Repayment(payId, loan, payment, LocalDate.now().toString());
            System.out.println("\n" + repayment);
            System.out.println(loan.generatePaymentReceipt());

            // Follow-up payment option
            if (loanService.getOutstandingBalance(loan) > 0) {
                System.out.print("\n  Make another payment on this loan? (yes/no): ");
                if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                    double extra = promptDouble("  Additional Payment (RWF): ",
                            p -> InputValidator.validatePaymentAmount(
                                    p, loanService.getOutstandingBalance(loan)));
                    loanService.processRepayment(loan, extra);
                    repayment.updateRemainingBalance(extra);
                    System.out.println(loan.generatePaymentReceipt());
                }
            } else {
                System.out.println("\n  ✔ Loan fully repaid. Congratulations!");
            }
        }
    }

    // ── Type-specific extra parameter ─────────────────────────────────────────

    static String collectExtra(String loanType) {
        switch (loanType.trim().toLowerCase()) {
            case "personal":
                return promptNonEmpty("  Purpose of Loan (e.g. Medical, Travel) : ");
            case "home":
                return String.valueOf(promptDouble(
                        "  Property Value (RWF)                    : ",
                        v -> InputValidator.validatePositive(v, "Property Value")));
            case "car":
                return promptNonEmpty("  Vehicle Model (e.g. Toyota RAV4 2022)  : ");
            case "business":
                return promptNonEmpty("  Collateral Type (e.g. Land, Equipment) : ");
            case "student":
                return promptNonEmpty("  University / Institution Name           : ");
            case "agricultural":
                return String.valueOf(promptDouble(
                        "  Farm Size (hectares)                    : ",
                        v -> InputValidator.validatePositive(v, "Farm Size")));
            default:
                return "";
        }
    }

    // ── Prompt Helpers ────────────────────────────────────────────────────────

    @FunctionalInterface interface SV { boolean v(String s); }
    @FunctionalInterface interface DV { boolean v(double d); }
    @FunctionalInterface interface IV { boolean v(int i); }

    static String promptString(String prompt, SV validator) {
        while (true) {
            System.out.print(prompt);
            String in = sc.nextLine().trim();
            if (validator.v(in)) return in;
        }
    }

    static String promptNonEmpty(String prompt) {
        return promptString(prompt,
                s -> InputValidator.validateNonEmpty(s, prompt.replace(":", "").trim()));
    }

    static double promptDouble(String prompt, DV validator) {
        while (true) {
            System.out.print(prompt);
            double v = InputValidator.parseDouble(
                    sc.nextLine().trim(), prompt.replace(":", "").trim());
            if (!Double.isNaN(v) && validator.v(v)) return v;
        }
    }

    static int promptInt(String prompt, IV validator) {
        while (true) {
            System.out.print(prompt);
            int v = InputValidator.parseInt(
                    sc.nextLine().trim(), prompt.replace(":", "").trim());
            if (v != Integer.MIN_VALUE && validator.v(v)) return v;
        }
    }
}
