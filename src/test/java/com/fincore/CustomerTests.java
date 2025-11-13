package com.fincore;

import com.fincore.models.Account;
import com.fincore.models.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CustomerTests {

    @Test
    @DisplayName("Add Customer Account: rejects duplicate additions")
    public void testAddingCustomerAccounts_rejectsduplicateAdditions() {
        Customer customer = new Customer("CUST_TEST_0001", "Test User");
        Account account1 = new Account("Test User", 1000.0);
        customer.addAccount(account1);
        Assertions.assertFalse(customer.addAccount(account1));
    }

    @Test
    @DisplayName("Add Customer Account: rejects null value additions")
    public void testAddingCustomerAccounts_rejectsNullAdditions() {
        Customer customer = new Customer("CUST_TEST_0001", "Test User");
        Assertions.assertFalse(customer.addAccount(null));
    }

    @Test
    @DisplayName("Add Customer Account: adds new accounts successfully")
    public void testAddingCustomerAccounts_addsNewAccountsSuccessfully() {
        Customer customer = new Customer("CUST_TEST_0001", "Test User");
        Account account1 = new Account("Test User", 1000.0);
        Assertions.assertTrue(customer.addAccount(account1));
        Assertions.assertEquals(1, customer.getAccounts().size());
    }

    @Test
    @DisplayName("Remove Customer Account: removes existing accounts successfully")
    public void testRemovingCustomerAccounts_removesExistingAccountsSuccessfully() {
        Customer customer = new Customer("CUST_TEST_0001", "Test User");
        Account account1 = new Account("Test User", 1000.0);
        customer.addAccount(account1);
        Assertions.assertTrue(customer.removeAccount(account1));
        Assertions.assertEquals(0, customer.getAccounts().size());
    }

    @Test
    @DisplayName("Customer Account Operations: total balance is calculated accurately")
    public void testCustomerNetBalance_totalBalanceisAccuratelyCalculated() {
        Customer customer = new Customer("CUST_TEST_0001", "Test User");
        double[] balances = {1000.03, 290.55, 3500.00};
        for(int i = 0; i < balances.length; i++) {
            Account account = new Account(("Test_User_" + i), balances[i]);
            customer.addAccount(account);
        }
        double expectedTotalBalance = Arrays.stream(balances).sum();
        Assertions.assertEquals(expectedTotalBalance, customer.getTotalBalance());
    }

    
}
