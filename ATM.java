package atm;

import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    private final String type;
    private final double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": $" + amount;
    }
}

class Account {
    private double balance;
    private final ArrayList<Transaction> transactions;

    public Account(double balance) {
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        transactions.add(new Transaction("Withdrawal", amount));
        return true;
    }

    public void transfer(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactions.add(new Transaction("Transfer to " + recipient, amount));
        }
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}

public class ATM {
    private final Account account;
    private final Scanner scanner;

    public ATM(double initialBalance) {
        account = new Account(initialBalance);
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\nWelcome to the Console ATM");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Balance: $" + account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Deposited $" + depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawAmount = scanner.nextDouble();
                    if (account.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawn $" + withdrawAmount);
                    } else {
                        System.out.println("Insufficient funds");
                    }
                    break;
                case 4:
                    System.out.print("Enter transfer amount: $");
                    double transferAmount = scanner.nextDouble();
                    System.out.print("Enter recipient's initial balance: $");
                    double recipientBalance = scanner.nextDouble();
                    Account recipient = new Account(recipientBalance);
                    account.transfer(recipient, transferAmount);
                    System.out.println("Transferred $" + transferAmount + " to recipient");
                    break;
                case 5:
                    System.out.println("Transaction History:");
                    ArrayList<Transaction> transactions = account.getTransactions();
                    transactions.forEach(System.out::println);
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM(1000); 
        atm.displayMenu();
    }
}
