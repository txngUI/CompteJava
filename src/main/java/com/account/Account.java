package com.account;

public class Account {
    // attributes
    private static int accountCount = 0;
    private int accountNumber;
    private Holder holder;
    private double balance = 0;
    private double overdrawn;
    private double overdrawnMax = 800;
    private double maxTransferRate = 1000;

    /**
     * Constructor for Account with the holder and generates an unique account number
     *
     * @param holder : Human object already created
     */
    public Account(Holder holder) {
        accountCount++;
        this.accountNumber = accountCount;
        this.holder = holder;
    }

    /**
     * Constructor for Account with the holder, overdrawnMax and maxTransferRate
     * it use the previous constructor and set the overdrawnMax and maxTransferRate
     *
     * @param holder          : Human object already created
     * @param overdrawnMax    : maximum amount that can be overdrawn (float)
     * @param maxTransferRate : maximum amount that can be transferred (float)
     */
    public Account(Holder holder, double overdrawnMax, double maxTransferRate) {
        this(holder);
        this.overdrawnMax = overdrawnMax;
        this.maxTransferRate = maxTransferRate;
    }

    /**
     * Constructor for Account with the holder, overdrawnMax, balance and maxTransferRate
     * it use the previous constructor and set the balance
     *
     * @param holder          : Human object already created
     * @param overdrawnMax    : maximum amount that can be overdrawn (float)
     * @param balance         : initial balance (float)
     * @param maxTransferRate : maximum amount that can be transferred (float)
     */
    public Account(Holder holder, double overdrawnMax, double balance, double maxTransferRate) {
        this(holder, overdrawnMax, maxTransferRate);
        this.balance += balance;

        updateOverdrawn();
    }

    /**
     * Method to set the overdrawnMax of the account
     *
     * @param overdrawnMax : maximum amount that can be overdrawn (float)
     */
    public void setOverdrawnMax(double overdrawnMax) {
        this.overdrawnMax = overdrawnMax;
    }

    /**
     * Method to get the number of the account
     *
     * @return accountNumber : number of the account (int)
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Method to get the holder of the account
     *
     * @return name + surname + address of the holder (String)
     */
    public String getHolder() {
        return holder.toString();
    }

    /**
     * Method to get the overdrawnMax of the account
     *
     * @return overdrawnMax : maximum amount that can be overdrawn (float)
     */
    public double getOverdrawnMax() {
        return overdrawnMax;
    }

    /**
     * Method to get the maxTransferRate of the account
     *
     * @return maxTransferRate : maximum amount that can be transferred (float)
     */
    public double getMaxTransferRate() {
        return maxTransferRate;
    }

    /**
     * Method to know if the account is overdrawn
     *
     * @return boolean : true if the account is overdrawn, false otherwise
     */
    public boolean isOverdrawn() {
        return this.overdrawn < 0;
    }

    /**
     * Method to get the balance of the account
     *
     * @return balance : balance of the account (float)
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Method to deposit money in the account
     * it also check if the account is not overdrawn anymore
     *
     * @param amount : amount to deposit (float)
     */
    public void deposit(double amount) {
        this.balance += amount;
        updateOverdrawn();
    }

    /**
     * Method to debit money from the account
     * it also check if the account can to be debited or is overdrawn
     *
     * @param amount : amount to debit (float)
     */
    public void debit(double amount) {
        this.debitAllowed(amount);
        this.balance -= amount;
        updateOverdrawn();

    }

    /**
     * Method to transfer money from the account to another account
     * it used the debit and deposit methods (deposit of another account)
     * and check if the transfer is allowed
     *
     * @param amount  : amount to transfer (float)
     * @param account : account to transfer the money (Account)
     */
    public void transfer(double amount, Account account) {
        this.debit(amount);
        this.debitAllowed(amount);
        account.deposit(amount);
    }

    /**
     * Method to check if the action is allowed
     * it check if the amount is not negative, not over the overdrawn limit and not over the maximum transfer rate
     *
     * @param amount : amount to check (float)
     */
    private void debitAllowed(double amount) {
        if (amount > this.maxTransferRate) {
            throw new IllegalArgumentException("Cannot transfer more than the maximum transfer rate");
        } else if (amount < 0) {
            throw new IllegalArgumentException("Cannot transfer a negative amount");
        } else if ((this.balance - amount) * -1 > this.overdrawnMax) {
            throw new IllegalArgumentException("Cannot transfer more than the overdrawn limit");
        }
    }

    private void updateOverdrawn() {
        if (this.balance < 0) {
            this.overdrawn = this.balance;
        } else {
            this.overdrawn = 0;
        }
    }
}
