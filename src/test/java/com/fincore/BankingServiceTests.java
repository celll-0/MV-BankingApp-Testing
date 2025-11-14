package com.fincore;

import com.fincore.models.Account;
import com.fincore.services.BankingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BankingServiceTests {

    @Test
    @DisplayName("Banking Service Between Accounts Transfer: reject transfer between different account holders")
    public void testBankingServiceTransfers_isConfirmed() {
        double transferAmount = 200.0;
        Account account1 = new Account("Charlie", 800.0);
        Account account2 = new Account("Mark", 1000.0);
        Assertions.assertFalse(BankingService.transferBetweenCustomerAccounts(account1, account2, transferAmount));
    }

    @Test
    @DisplayName("Banking Service Between Accounts Transfer: transfer amount is deducted from sender balance")
    public void testBankingServiceTransfer_isDeductedFromSender() {
        double transferAmount = 200.0;
        double senderInitialBalance = 800.0;
        Account sender = new Account("Charlie", senderInitialBalance);
        Account receiver = new Account("Charlie", 1000.0);
        BankingService.transferBetweenCustomerAccounts(sender, receiver, transferAmount);
        Assertions.assertEquals((senderInitialBalance - transferAmount), sender.getBalance());
    }

    @Test
    @DisplayName("Banking Service Between Accounts Transfer: transfer amount is added to sender balance")
    public void testBankingServiceTransfer_isAddedToSenderBalance() {
        double transferAmount = 200.0;
        double receiverInitialBalance = 1000.0;
        Account sender = new Account("Charlie", 800.0);
        Account receiver = new Account("Charlie", receiverInitialBalance);
        BankingService.transferBetweenCustomerAccounts(sender, receiver, transferAmount);
        Assertions.assertEquals((receiverInitialBalance + transferAmount), receiver.getBalance());
    }

    @Test
    @DisplayName("Banking Service Between Accounts Transfer: rejects transfer if sender has insufficient funds")
    public void testBankingServiceTransfer_rejectSenderInsufficientFunds() {
        double transferAmount = 200.0;
        double senderInitialAmount = 100.0;
        Account sender = new Account("Charlie", senderInitialAmount);
        Account receiver = new Account("Charlie", 500.0);
        Assertions.assertFalse(BankingService.transferBetweenCustomerAccounts(sender, receiver, transferAmount));
    }

    @Test
    @DisplayName("Banking Service Between Accounts Transfer: rejects transfer amount of zero")
    public void testBankingServiceTransfer_rejectZeroTransferAmount() {
        double transferAmount = 0;
        Account sender = new Account("Charlie", 100.0);
        Account receiver = new Account("Charlie", 500.0);
        Assertions.assertFalse(BankingService.transferBetweenCustomerAccounts(sender, receiver, transferAmount));
    }

    @Test
    @DisplayName("Banking Service Between Accounts Transfer: rejects transfer to the same account")
    public void testBankingServiceTransfer_rejectsTransferToSender() {
        double transferAmount = 200.0;
        Account sender = new Account("Charlie", 100.0);
        Assertions.assertFalse(BankingService.transferBetweenCustomerAccounts(sender, sender, transferAmount));
    }
}
