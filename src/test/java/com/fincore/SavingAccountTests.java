package com.fincore;

import com.fincore.models.SavingsAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SavingAccountTests {

    @Test
    @DisplayName("Savings Apply Interest: is correctly applied to balance")
    public void testSavingsApplyInterest_isCorrectlyApplied() {
        double interestRate = 0.07;
        double balance = 1000.0;
        SavingsAccount account = new SavingsAccount("Alice", balance, interestRate);

        double expectedInterest = balance * interestRate;
        double expectedNewBalance = balance + expectedInterest;
        account.applyInterest();
        Assertions.assertEquals(expectedNewBalance, account.getBalance());
    }

    @Test
    @DisplayName("Savings Apply Interest: is correctly applied with new interest rate")
    public void testSavingsApplyInterest_isCorrectlyAppliedWithNewRate() {
        double balance = 1000.0;
        SavingsAccount account = new SavingsAccount("Alice", balance, 0.04);
        double newinterestRate = 0.07;
        account.setInterestRate(newinterestRate);

        double expectedInterest = balance * newinterestRate;
        double expectedNewBalance = balance + expectedInterest;
        account.applyInterest();
        Assertions.assertEquals(expectedNewBalance, account.getBalance());
    }

    @Test
    @DisplayName("Savings Account Interest Rate: rejects setting negative rate")
    public void testSavingsAccountInterest_rejectsNegativeRate() {
        double badInterestRate = -0.02;
        // should throw error on negative interest rate during construction
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SavingsAccount("Bob", 500.0, badInterestRate));
        // should reject setting new negative interest rate
        SavingsAccount account = new SavingsAccount("Alice", 500.0, 0.04);
        Assertions.assertThrows(IllegalArgumentException.class, () -> account.setInterestRate(badInterestRate));
    }
}
