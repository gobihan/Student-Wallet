import java.util.*;

public class Account {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private int accountID;
    private int accountAmount;
    private double income;
    private Date date;


    /**
     *
     * @param ID
     * @param amount
     * @param income
     */
    public Account(int ID,String fname, String sname, String uname, String pword,int amount, double income, Date date) {
        accountID=ID;
        accountAmount=amount;
        firstName=fname;
        lastName=sname;
        userName=uname;
        password=pword;
        this.income=income;
        this.date=date;
    }

    /**
     *
     * @param userName
     * @param password
     */
    public boolean login(String userName, String password) {
        // TODO - implement Account.login
        throw new UnsupportedOperationException();
    }

    public void updateAccountDetails() {
        // TODO - implement Account.updateAccountDetails
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param account
     */
    public void deleteAccount(Account account) {
        // TODO - implement Account.deleteAccount
        throw new UnsupportedOperationException();
    }

    public boolean confirmDeleteAccount() {
        // TODO - implement Account.confirmDeleteAccount
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param name
     * @param amount
     * @param category
     */
    public Transaction createTransaction(String name, int amount, TransactionType category) {
        // TODO - implement Account.createTransaction
        throw new UnsupportedOperationException();
    }

    public boolean confirmTransaction() {
        // TODO - implement Account.confirmTransaction
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param transaction
     */
    public void removeTransaction(Transaction transaction) {
        // TODO - implement Account.removeTransaction
        throw new UnsupportedOperationException();
    }

    public boolean confirmDeleteTransaction() {
        // TODO - implement Account.confirmDeleteTransaction
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param transactionName
     */
    public Transaction searchForTransaction(String transactionName) {
        // TODO - implement Account.searchForTransaction
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param transactionDate
     */
    public Transaction searchForTransaction(Date transactionDate) {
        // TODO - implement Account.searchForTransaction
        throw new UnsupportedOperationException();
    }

    public void sortTransactionByAmount() {
        // TODO - implement Account.sortTransactionByAmount
        throw new UnsupportedOperationException();
    }

    public void sortTransactionByDate() {
        // TODO - implement Account.sortTransactionByDate
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param username
     */
    public void searchAccount(String username) {
        // TODO - implement Account.searchAccount
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param name
     * @param limit
     * @param category
     */
    public Budget createBudget(String name, int limit, TransactionType category) {
        // TODO - implement Account.createBudget
        throw new UnsupportedOperationException();
    }

    public boolean confirmBudget() {
        // TODO - implement Account.confirmBudget
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param budget
     */
    public void removeBudget(Budget budget) {
        // TODO - implement Account.removeBudget
        throw new UnsupportedOperationException();
    }

    public boolean confirmDeleteBudget() {
        // TODO - implement Account.confirmDeleteBudget
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param name
     */
    public Budget searchForBudget(String name) {
        // TODO - implement Account.searchForBudget
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param transaction
     */
    public void deleteTransaction(Transaction transaction) {
        // TODO - implement Account.deleteTransaction
        throw new UnsupportedOperationException();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getAccountID() {
        // TODO - implement Account.getAccountID
       return this.accountID;
    }

    public int getAccountAmount() {
        return this.accountAmount;
    }

    public double getIncome() {
        return this.income;
    }
    public Date getDate() {
        return this.date;
    }

    public void showExpenditureGraphs() {
        // TODO - implement Account.showExpenditureGraphs
        throw new UnsupportedOperationException();
    }

}