package com.fincore;

import com.fincore.models.Account;
import com.fincore.models.Bank;
import com.fincore.models.Customer;
import com.fincore.models.SavingsAccount;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class BankTests {

    @Test
    @DisplayName("Bank Add Customer: allows usage of both '.addCustomer' methods")
    public void testBankAddCustomer_allowsUsageOfBothAddCustomerMethods() {
        Bank bank = new Bank();
        Customer customer1 = bank.addCustomer("CUST0001", "Test User 2");
        Assertions.assertNotNull(customer1);
        Customer customer2 = bank.addCustomer("Test User 1");
        Assertions.assertNotNull(customer2);
    }

    @Test
    @DisplayName("Bank Add Customer: rejects duplicate customer")
    public void testBankAddCustomer_rejectsDuplicateCustomer() {
        Bank bank = new Bank();
        Customer customer1 = bank.addCustomer("CUST_TEST_0001", "Test User 1");
        Assertions.assertNotNull(customer1);
        Customer duplicateCustomer = bank.addCustomer("CUST_TEST_0001", "Test User 1 Duplicate");
        Assertions.assertNull(duplicateCustomer);
    }

    @Test
    @DisplayName("Bank Add Customer: Adds new customer successfully")
    public void testBankAddCustomer_addsNewCustomerSuccessfully() {
        Bank bank = new Bank();
        String testCustomerID = "CUST_TEST_0003";
        Assertions.assertEquals(0, bank.getCustomerCount());
        Customer customer = bank.addCustomer(testCustomerID, "Test User 3");
        Assertions.assertEquals(1, bank.getCustomerCount());
        Assertions.assertEquals(customer, bank.getCustomer(testCustomerID));
    }

    @Test
    @DisplayName("Total Account Count: counts accounts across all customers accurately")
    public void testBankTotalAccountCount_iscorrect() {
        Bank bank = new Bank();
        Customer customer1 = bank.addCustomer("CUST_TEST_0004", "Test User 4");
        Customer customer2 = bank.addCustomer("CUST_TEST_0005", "Test User 5");

        customer1.addAccount(new Account(customer1.getName(), 1000.0));
        customer1.addAccount(new SavingsAccount(customer1.getName(),500.0, 0.03));
        customer2.addAccount(new Account(customer2.getName(), 2000.0));
        Assertions.assertEquals(3, bank.getTotalAccountCount());
    }

    @Test
    @DisplayName("Total Bank Balance: calculates total balance across all accounts accurately")
    public void testBankTotalAccountBalance_iscorrect() {
        Bank bank = new Bank();
        Customer customer1 = bank.addCustomer("CUST_TEST_0004", "Test User 4");
        Customer customer2 = bank.addCustomer("CUST_TEST_0005", "Test User 5");

        double[] balances = {1000.0, 500.0, 2000.0};
        customer1.addAccount(new Account(customer1.getName(), balances[0]));
        customer1.addAccount(new Account(customer1.getName(), balances[1]));
        customer2.addAccount(new SavingsAccount(customer2.getName(), balances[2], 0.04));
        Assertions.assertEquals(Arrays.stream(balances).sum(), bank.getTotalBankBalance());
    }

}
