package com.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    Holder jDoe;
    Account account;
    Account account2;

    /**
     * Set up the test environment with initialize an Holder and two Accounts
     * Account 1 with only the Holder to test the default values
     * Account 2 with the Holder, a balance of 500, an overdrawnMax of 1000 and a maxTransferRate of 2000 to test the constructor with all parameters
     * Account 3 with the Holder, an overdrawnMax of 1000 and a maxTransferRate of 2000 to test the constructor without the balance
     */
    @BeforeEach
    void setUp() {
        this.jDoe = new Holder("John", "Doe", "1 rue de la Paix");
        this.account = new Account(jDoe);
        this.account2 = new Account(jDoe, 1000, 500, 2000);
        Account account3 = new Account(jDoe, 1000, 2000);
    }

    /**
     * Test the setOverdrawnMax method
     * Set the overdrawnMax to 1000 and check if the value is correct
     */
    @DisplayName("Test of the method setOverdrawnMax")
    @Test
    void setOverdrawnMax() {
        account.setOverdrawnMax(1000);
        assertEquals(1000, account.getOverdrawnMax());
    }

    /**
     * Test the getAccountNumber method
     * Check if the first account number is 1 and the second is 2
     */
    @DisplayName("Test of the method getAccountNumber")
    @Test
    void getAccountNumber() {
        assertEquals(1, account.getAccountNumber());
        assertEquals(2, account2.getAccountNumber());
    }

    /**
     * Test the getHolder method to check if the getHolder method return the correct description of the Holder
     */
    @DisplayName("Test of the method getHolder")
    @Test
    void getHolder() {
        assertEquals("Name : John, Surname : Doe, Address : 1 rue de la Paix", account.getHolder());
    }

    /**
     * Test the getOverdrawnMax method to check if the overdrawnMax is correct
     */
    @DisplayName("Test of the method getOverdrawnMax")
    @Test
    void getOverdrawnMax() {
        assertEquals(1000, account2.getOverdrawnMax());
    }

    /**
     * Test the getMaxTransferRate method to check if the maxTransferRate is correct
     */
    @DisplayName("Test of the method getMaxTransferRate")
    @Test
    void getMaxTransferRate() {
        assertEquals(2000, account2.getMaxTransferRate());
    }

    /**
     * Test the isOverdrawn method to check if the account is overdrawn
     */
    @DisplayName("Test of the method isOverdrawn")
    @Test
    void isOverdrawn() {
        account.debit(600);
        assertTrue(account.isOverdrawn());
        account2.debit(400);
        assertFalse(account2.isOverdrawn());
    }

    /**
     * Test the getBalance method to check if the balance is correct
     */
    @DisplayName("Test of the method getBalance")
    @Test
    void getBalance() {
        assertEquals(0, account.getBalance());
        assertEquals(500, account2.getBalance());
    }

    /**
     * Test the deposit method to check if the deposit is correct
     */
    @DisplayName("Test of the method deposit")
    @Test
    void deposit() {
        account.deposit(100);
        assertEquals(100, account.getBalance());
        account2.deposit(100);
        assertEquals(600, account2.getBalance());
    }

    /**
     * Test the debit method with an amount more than the maxTransferRate
     */
    @DisplayName("Test of the method debit with an amount more than the maxTransferRate")
    @Test
    void debitMoreThanMax() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> account.debit(1200));
        assertEquals("Cannot transfer more than the maximum transfer rate", exception.getMessage());
        account.debit(100);
        assertEquals(-100, account.getBalance());
    }

    /**
     * Test the debit method with a negative amount
     */
    @DisplayName("Test of the method debit with a negative amount")
    @Test
    void debitNegativeAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> account.debit(-100));
        assertEquals("Cannot transfer a negative amount", exception.getMessage());
    }

    /**
     * Test the debit method with an amount more than the overdrawn limit
     */
    @DisplayName("Test of the method debit with an amount more than the overdrawn limit")
    @Test
    void debitMoreThanOverdrawnLimit() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> account.debit(900));
        assertEquals("Cannot transfer more than the overdrawn limit", exception.getMessage());
    }

    /**
     * Test the debit method with an amount authorized
     */
    @DisplayName("Test of the method debit with an amount authorized")
    @Test
    void debit() {
        account.debit(100);
        assertEquals(-100, account.getBalance());
    }

    /**
     * Test the transfer method with an amount authorized
     */
    @DisplayName("Test of the method transfer with an amount authorized")
    @Test
    void transfer() {
        account.transfer(100, account2);
        assertEquals(600, account2.getBalance());
    }

    @Test
    void updateOverdrawn() {
        account.debit(100);
        assertTrue(account.isOverdrawn());
        account.deposit(200);
        assertFalse(account.isOverdrawn());
    }
}