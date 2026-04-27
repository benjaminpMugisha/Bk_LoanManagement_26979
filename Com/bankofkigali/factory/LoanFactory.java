package com.bankofkigali.utils;

import com.bankofkigali.models.LoanManager;
import com.bankofkigali.loans.PersonalLoan;
import com.bankofkigali.loans.HomeLoan;
import com.bankofkigali.loans.CarLoan;
import com.bankofkigali.loans.BusinessLoan;
import com.bankofkigali.loans.StudentLoan;
import com.bankofkigali.loans.AgriculturalLoan;


public class LoanFactory {

    public static LoanManager createLoan(String loanType,
                                         String loanId,
                                         double amount,
                                         double rate,
                                         int    duration,
                                         String officer,
                                         String branch,
                                         String extraParam) {

        if (loanType == null || loanType.isBlank()) {
            System.out.println("  ✘ LoanFactory ERROR: Loan type must not be empty.");
            return null;
        }

        switch (loanType.trim().toLowerCase()) {

            case "personal":
                return new PersonalLoan(loanId, amount, rate, duration,
                        officer, branch, extraParam);

            case "home":
                double propertyValue = parseExtra(extraParam, "Property Value");
                return new HomeLoan(loanId, amount, rate, duration,
                        officer, branch, propertyValue);

            case "car":
                return new CarLoan(loanId, amount, rate, duration,
                        officer, branch, extraParam);

            case "business":
                return new BusinessLoan(loanId, amount, rate, duration,
                        officer, branch, extraParam);

            case "student":
                return new StudentLoan(loanId, amount, rate, duration,
                        officer, branch, extraParam);

            case "agricultural":
                double farmSize = parseExtra(extraParam, "Farm Size");
                return new AgriculturalLoan(loanId, amount, rate, duration,
                        officer, branch, farmSize);

            default:
                System.out.println("  ✘ LoanFactory ERROR: Unknown loan type \"" + loanType + "\".");
                System.out.println("    Supported: personal, home, car, business, student, agricultural.");
                return null;
        }
    }
  private static double parseExtra(String value, String fieldName) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.out.println("  ⚠ LoanFactory WARNING: " + fieldName
                    + " value invalid (\"" + value + "\"); defaulting to 0.");
            return 0;
        }
    }

   public static void printAvailableLoanTypes() {
        System.out.println();
        System.out.println("  Available Loan Types:");
        System.out.println("   1. personal      – General-purpose personal loan  (max 5,000,000 RWF | max 60 months)");
        System.out.println("   2. home          – Mortgage / property purchase    (LTV ≤ 80% of property value)");
        System.out.println("   3. car           – Vehicle financing               (max 30,000,000 RWF | max 48 months)");
        System.out.println("   4. business      – SME & corporate loans           (collateral required | min 12 months)");
        System.out.println("   5. student       – Education financing             (50% subsidised rate | max 10,000,000 RWF)");
        System.out.println("   6. agricultural  – Farm & agri-business            (min 0.5 ha | max 50,000,000 RWF)");
    }
}
