import java.util.*;

public class Transaction {

    private int transactionID;
    private double transactionAmount;
    private Date transactionDate;
    private TransactionType categoryOfTransaction;

    /**
     *
     * @param ID
     * @param amount
     * @param category
     */
    public Transaction(int ID, int amount, TransactionType category) {
        transactionID=ID;
        transactionAmount=amount;
        categoryOfTransaction=category;
    }
    public boolean confirmTransaction() {
        // TODO - implement Transaction.confirmTransaction
        throw new UnsupportedOperationException();
    }

    public boolean confirmDelete() {
        // TODO - implement Transaction.confirmDelete
        throw new UnsupportedOperationException();
    }

    public int getTransactionID() {
        return this.transactionID;
    }

    public double getTransactionAmount() {
        return this.transactionAmount;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

    public TransactionType getCategoryOfTransaction() {
        return this.categoryOfTransaction;
    }

}