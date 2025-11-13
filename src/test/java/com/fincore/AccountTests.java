package com.fincore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fincore.models.Account;
import com.fincore.models.InsufficientFundsException;


public class AccountTests {

    @Test
    @DisplayName("Account Deposit: New balacnce is calculated Correctly")
    public void testAccountDeposit_calculatesNewBalanceCorrectly() {
        double initialCBalance = 1000.00;
        Account account = new Account("Test User", initialCBalance);

        double addedFunds = 250.00;
        double expectedNewBalance = initialCBalance + addedFunds;
        account.deposit(addedFunds);
        System.out.println("New balance is calculated Correctly after deposit: "+(expectedNewBalance == account.getBalance())+"\n");
        Assertions.assertEquals(expectedNewBalance,  account.getBalance());
    }

    @Test
    @DisplayName("Account Deposit: Negative deposit is rejected")
    public void testAccountDeposit_rejectsNegativeDeposit() {
        Account account = new Account("Test User", 1000.00);
        boolean result = account.deposit(-250.00);
        System.out.println("Negative deposit is rejected: "+(!result)+"\n");
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Account Withdrawal: New balacnce is calculated Correctly")
    public void testAccountWithdrawal_calculatesNewBalanceCorrectly() {
        double initialCBalance = 1000.00;
        Account account = new Account("Test User", initialCBalance);

        double withdrawnFunds = 250.00;
        double expectedNewBalance = initialCBalance - withdrawnFunds;
        try {
            account.withdraw(withdrawnFunds);
            System.out.println("New balance is calculated Correctly after withdrawal: "+(expectedNewBalance == account.getBalance())+"\n");
            Assertions.assertEquals(expectedNewBalance,  account.getBalance());
        } catch (Exception e) {
            String errMsg = "Withdrawal threw an unexpected exception: ";
            Assertions.fail(errMsg + e.getClass().getName());
            System.out.println(errMsg+e.getClass().getName()+"\n");
        }
    }

    @Test
    @DisplayName("Account Withdrawal: Negative withdrawal is rejected")
    public void testAccountWithdrawal_rejectsNegativeDeposit() {
        Account account = new Account("Test User", 1000.00);
        Assertions.assertThrows(IllegalArgumentException.class, () -> account.withdraw(-250.00));
    }

    @Test
    @DisplayName("Account Withdrawal: withdrawals larger than balance are rejected")
    public void testAccountWithdrawal_rejectsWithdrawalLargerThanBalance() {
        Account account = new Account("Test User", 1000.00);
        Assertions.assertThrows(InsufficientFundsException.class, () -> account.withdraw(2000.00));
    }
}
