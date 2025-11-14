package com.fincore.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.UUID;

/**
 * Bank class representing the main banking system that manages all customers.
 * This class uses a HashMap to store and retrieve customers by their unique ID.
 * 
 * @author FinCore Development Team
 * @version 5.0.0
 */
public class Bank {
    
    private final Map<String, Customer> customers;
    private int nextCustomerId;
    
    /**
     * Constructor to initialize the Bank with an empty customer map.
     */
    public Bank() {
        this.customers = new HashMap<>();
        this.nextCustomerId = 1;
    }
    
    /**
     * Adds a new customer to the bank.
     * 
     * @param name the customer's name
     * @return the created customer, or null if customer already exists
     */
    public Customer addCustomer(String name) {
        String customerId = generateCustomerId();
        Customer customer = new Customer(customerId, name);
        
        if (customers.putIfAbsent(customerId, customer) == null) {
            return customer;
        }
        return null;
    }
    
    /**
     * Adds a new customer with a specific ID to the bank.
     * 
     * @param customerId the customer's unique ID
     * @param name the customer's name
     * @return the created customer, or null if customer ID already exists
     */
    public Customer addCustomer(String customerId, String name) {
        Customer customer = new Customer(customerId, name);
        
        if (customers.putIfAbsent(customerId, customer) == null) {
            return customer;
        }
        return null;
    }
    
    /**
     * Retrieves a customer by their ID.
     * 
     * @param customerId the customer's unique ID
     * @return the customer if found, null otherwise
     */
    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }
    
    /**
     * Checks if a customer exists in the bank.
     * 
     * @param customerId the customer's unique ID
     * @return true if the customer exists, false otherwise
     */
    public boolean hasCustomer(String customerId) {
        return customers.containsKey(customerId);
    }
    
    /**
     * Removes a customer from the bank.
     * 
     * @param customerId the customer's unique ID
     * @return the removed customer, or null if customer was not found
     */
    public Customer removeCustomer(String customerId) {
        return customers.remove(customerId);
    }
    
    /**
     * Gets all customers in the bank.
     * 
     * @return a collection of all customers
     */
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
    
    /**
     * Gets the total number of customers in the bank.
     * 
     * @return the number of customers
     */
    public int getCustomerCount() {
        return customers.size();
    }
    
    /**
     * Gets the total number of accounts across all customers.
     * 
     * @return the total number of accounts
     */
    public int getTotalAccountCount() {
        return customers.values().stream()
                       .mapToInt(Customer::getAccountCount)
                       .sum();
    }
    
    /**
     * Gets the total balance across all accounts in the bank.
     * 
     * @return the total balance
     */
    public double getTotalBankBalance() {
        return customers.values().stream()
                       .mapToDouble(Customer::getTotalBalance)
                       .sum();
    }
    
    /**
     * Displays a summary of all customers and their accounts.
     */
    public void displayBankSummary() {
        System.out.println("=== Bank Summary ===");
        System.out.println("Total Customers: " + getCustomerCount());
        System.out.println("Total Accounts: " + getTotalAccountCount());
        System.out.println("Total Bank Balance: $" + String.format("%.2f", getTotalBankBalance()));
        System.out.println();
        
        if (customers.isEmpty()) {
            System.out.println("No customers found in the bank.");
        } else {
            System.out.println("Customer List:");
            for (Customer customer : customers.values()) {
                System.out.println("  " + customer);
            }
        }
    }
    
    /**
     * Generates a unique customer ID.
     * 
     * @return a unique customer ID string
     */
    private String generateCustomerId() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Returns a string representation of the bank.
     * 
     * @return string representation of the bank
     */
    @Override
    public String toString() {
        return "Bank[customers=" + getCustomerCount() + ", totalAccounts=" + getTotalAccountCount() + "]";
    }
}
