package com.fincore.models;

/**
 * SavingsAccount class representing a savings account with interest functionality.
 * This class extends Account and adds interest rate capabilities.
 * 
 * @author FinCore Development Team
 * @version 4.0.0
 */
public class SavingsAccount extends Account {
    
    // Private field for interest rate
    private double interestRate;
    
    /**
     * Constructor to initialize a SavingsAccount with account holder name, 
     * initial balance, and interest rate.
     * 
     * @param accountHolder the name of the account holder
     * @param initialBalance the initial balance for the account
     * @param interestRate the annual interest rate (as a decimal, e.g., 0.05 for 5%)
     */
    public SavingsAccount(String accountHolder, double initialBalance, double interestRate) {
        super(accountHolder, initialBalance);
        this.setInterestRate(interestRate);
    }
    
    /**
     * Gets the current interest rate.
     * 
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }
    
    /**
     * Sets the interest rate for the savings account.
     * 
     * @param interestRate the new interest rate (as a decimal)
     */
    public void setInterestRate(double interestRate) {
        if (interestRate >= 0) {
            this.interestRate = interestRate;
        } else {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
    }
    
    /**
     * Applies interest to the current balance based on the interest rate.
     * This method calculates and adds the interest amount to the balance.
     */
    public void applyInterest() {
        double interestAmount = getBalance() * interestRate;
        deposit(interestAmount);
        System.out.println("Interest applied: $" + String.format("%.2f", interestAmount));
        System.out.println("New balance after interest: $" + String.format("%.2f", getBalance()));
    }
    
    /**
     * Overrides the checkBalance method to include interest rate information.
     */
    @Override
    public void checkBalance() {
        System.out.println("=== Savings Account Balance ===");
        System.out.println("Account Holder: " + getAccountHolder());
        System.out.println("Current Balance: $" + String.format("%.2f", getBalance()));
        System.out.println("Interest Rate: " + String.format("%.2f%%", interestRate * 100));
    }
    
    /**
     * Returns a string representation of the savings account.
     * 
     * @return string representation of the savings account
     */
    @Override
    public String toString() {
        return "SavingsAccount[holder=" + getAccountHolder() + 
               ", balance=$" + String.format("%.2f", getBalance()) + 
               ", interestRate=" + String.format("%.2f%%", interestRate * 100) + "]";
    }
}
