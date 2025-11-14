package com.fincore.ui;

import com.fincore.models.Account;
import com.fincore.models.SavingsAccount;
import com.fincore.models.Customer;
import com.fincore.models.Bank;
import com.fincore.models.InsufficientFundsException;

import java.util.Scanner;

/**
 * Main class for the FinCore CLI Banking application.
 * This is the entry point for the command-line banking system.
 * Handles user interaction and coordinates between different components.
 * 
 * @author FinCore Development Team
 * @version 5.0.0
 */
public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank();
    private static Customer currentCustomer = null;
    
    /**
     * Main method - entry point of the application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to FinCore CLI Banking!");
        System.out.println("Your trusted partner in financial management.");
        System.out.println();

        // Add some sample customers for demonstration
        initializeSampleData();

        // Main application loop
        boolean running = true;
        while (running) {
            if (currentCustomer == null) {
                running = handleMainMenu();
            } else {
                running = handleCustomerMenu();
            }
        }
        
        System.out.println("Thank you for using FinCore CLI Banking. Goodbye!");
        scanner.close();
    }
    
    /**
     * Initializes the bank with some sample data for demonstration.
     */
    private static void initializeSampleData() {
        // Create sample customers
        Customer customer1 = bank.addCustomer("CUST0001", "Alex Doe");
        Customer customer2 = bank.addCustomer("CUST0002", "Jane Smith");
        
        // Add sample accounts
        customer1.addAccount(new Account("Alex Doe", 1000.00));
        customer1.addAccount(new SavingsAccount("Alex Doe", 500.00, 0.05));
        
        customer2.addAccount(new Account("Jane Smith", 2500.00));
        customer2.addAccount(new SavingsAccount("Jane Smith", 1000.00, 0.03));
        
        System.out.println("Sample data initialized with 2 customers and 4 accounts.");
        System.out.println();
    }
    
    /**
     * Handles the main menu when no customer is logged in.
     * 
     * @return true to continue running, false to exit
     */
    private static boolean handleMainMenu() {
        displayMainMenu();
        int choice = getUserChoice();
        
        switch (choice) {
            case 1:
                handleCustomerLogin();
                break;
            case 2:
                handleCreateCustomer();
                break;
            case 3:
                bank.displayBankSummary();
                break;
            case 4:
                return false; // Exit
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        
        System.out.println();
        return true;
    }
    
    /**
     * Handles the customer menu when a customer is logged in.
     * 
     * @return true to continue running, false to exit
     */
    private static boolean handleCustomerMenu() {
        displayCustomerMenu();
        int choice = getUserChoice();
        
        switch (choice) {
            case 1:
                handleAccountOperations();
                break;
            case 2:
                currentCustomer.displayAccountSummary();
                break;
            case 3:
                handleAddAccount();
                break;
            case 4:
                currentCustomer = null; // Logout
                System.out.println("Logged out successfully.");
                break;
            case 5:
                return false; // Exit
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        
        System.out.println();
        return true;
    }
    
    /**
     * Displays the main menu options.
     */
    private static void displayMainMenu() {
        System.out.println("=== FinCore CLI Banking - Main Menu ===");
        System.out.println("1. Customer Login");
        System.out.println("2. Create New Customer");
        System.out.println("3. Bank Summary");
        System.out.println("4. Exit");
        System.out.print("Please select an option (1-4): ");
    }
    
    /**
     * Displays the customer menu options.
     */
    private static void displayCustomerMenu() {
        System.out.println("=== FinCore CLI Banking - Customer Menu ===");
        System.out.println("Logged in as: " + currentCustomer.getName() + " (ID: " + currentCustomer.getCustomerId() + ")");
        System.out.println();
        System.out.println("1. Account Operations");
        System.out.println("2. View Account Summary");
        System.out.println("3. Add New Account");
        System.out.println("4. Logout");
        System.out.println("5. Exit");
        System.out.print("Please select an option (1-5): ");
    }
    
    /**
     * Handles customer login process.
     */
    private static void handleCustomerLogin() {
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine().trim();
        
        Customer customer = bank.getCustomer(customerId);
        if (customer != null) {
            currentCustomer = customer;
            System.out.println("Welcome back, " + customer.getName() + "!");
        } else {
            System.out.println("Customer not found. Please check your Customer ID or create a new account.");
        }
    }
    
    /**
     * Handles creating a new customer.
     */
    private static void handleCreateCustomer() {
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty. Please try again.");
            return;
        }
        
        Customer customer = bank.addCustomer(name);
        if (customer != null) {
            System.out.println("Customer created successfully!");
            System.out.println("Your Customer ID is: " + customer.getCustomerId());
            System.out.println("Please save this ID for future logins.");
            
            // Ask if they want to log in immediately
            System.out.print("Would you like to log in now? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("y") || response.equals("yes")) {
                currentCustomer = customer;
                System.out.println("Logged in successfully!");
            }
        } else {
            System.out.println("Error creating customer. Please try again.");
        }
    }
    
    /**
     * Handles adding a new account for the current customer.
     */
    private static void handleAddAccount() {
        System.out.println("=== Add New Account ===");
        System.out.println("1. Checking Account");
        System.out.println("2. Savings Account");
        System.out.print("Select account type (1-2): ");
        
        int accountType = getUserChoice();
        System.out.print("Enter initial balance: $");
        
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid amount: $");
            scanner.next();
        }
        double initialBalance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        Account newAccount;
        if (accountType == 1) {
            newAccount = new Account(currentCustomer.getName(), initialBalance);
        } else if (accountType == 2) {
            System.out.print("Enter interest rate (as percentage, e.g., 5.0 for 5%): ");
            while (!scanner.hasNextDouble()) {
                System.out.print("Please enter a valid interest rate: ");
                scanner.next();
            }
            double interestRate = scanner.nextDouble() / 100.0; // Convert percentage to decimal
            scanner.nextLine(); // Consume newline
            try {
                newAccount = new SavingsAccount(currentCustomer.getName(), initialBalance, interestRate);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                newAccount = null;
            }
        } else {
            System.out.println("Invalid account type selected.");
            return;
        }
        
        if (currentCustomer.addAccount(newAccount)) {
            System.out.println("Account added successfully!");
            System.out.println("Account Type: " + newAccount.getClass().getSimpleName());
            System.out.println("Initial Balance: $" + String.format("%.2f", initialBalance));
        } else {
            System.out.println("Error adding account. Please try again.");
        }
    }
    
    /**
     * Handles account operations (deposit, withdraw, check balance).
     */
    private static void handleAccountOperations() {
        if (currentCustomer.getAccountCount() == 0) {
            System.out.println("No accounts found. Please add an account first.");
            return;
        }
        
        // Display available accounts
        System.out.println("=== Available Accounts ===");
        for (int i = 0; i < currentCustomer.getAccountCount(); i++) {
            Account account = currentCustomer.getAccount(i);
            System.out.println((i + 1) + ". " + account.getClass().getSimpleName() + 
                             " - Balance: $" + String.format("%.2f", account.getBalance()));
        }
        
        System.out.print("Select account (1-" + currentCustomer.getAccountCount() + "): ");
        int accountIndex = getUserChoice() - 1;
        
        if (accountIndex < 0 || accountIndex >= currentCustomer.getAccountCount()) {
            System.out.println("Invalid account selection.");
            return;
        }
        
        Account selectedAccount = currentCustomer.getAccount(accountIndex);
        
        // Account operations menu
        System.out.println("=== Account Operations ===");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        if (selectedAccount instanceof SavingsAccount) {
            System.out.println("4. Apply Interest");
        }
        System.out.print("Select operation: ");
        
        int operation = getUserChoice();
        
        switch (operation) {
            case 1:
                handleDeposit(selectedAccount);
                break;
            case 2:
                handleWithdrawal(selectedAccount);
                break;
            case 3:
                selectedAccount.checkBalance();
                break;
            case 4:
                if (selectedAccount instanceof SavingsAccount) {
                    ((SavingsAccount) selectedAccount).applyInterest();
                } else {
                    System.out.println("Interest can only be applied to savings accounts.");
                }
                break;
            default:
                System.out.println("Invalid operation selected.");
        }
    }
    
    /**
     * Gets the user's menu choice and validates input.
     * 
     * @return the user's choice as an integer
     */
    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next(); // Clear invalid input
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return choice;
    }
    
    /**
     * Handles the deposit operation for an account.
     * 
     * @param account the account to deposit to
     */
    private static void handleDeposit(Account account) {
        System.out.print("Enter amount to deposit: $");
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid amount: $");
            scanner.next();
        }
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        if (account.deposit(amount)) {
            System.out.println("Deposit successful!");
            System.out.println("Amount deposited: $" + String.format("%.2f", amount));
            System.out.println("New balance: $" + String.format("%.2f", account.getBalance()));
        } else {
            System.out.println("Error: Deposit amount must be positive.");
        }
    }
    
    /**
     * Handles the withdrawal operation for an account.
     * Uses try-catch to handle InsufficientFundsException.
     * 
     * @param account the account to withdraw from
     */
    private static void handleWithdrawal(Account account) {
        System.out.print("Enter amount to withdraw: $");
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid amount: $");
            scanner.next();
        }
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        try {
            account.withdraw(amount);
            System.out.println("Withdrawal successful!");
            System.out.println("Amount withdrawn: $" + String.format("%.2f", amount));
            System.out.println("New balance: $" + String.format("%.2f", account.getBalance()));
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }
}
