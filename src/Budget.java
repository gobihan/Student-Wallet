public class Budget {

    private int budgetID;
    private int accountID;
    private double currentSpent;
    private double spendingLimit;
    private TransactionType categoryForBudget;

    /**
     *
     * @param ID
     * @param limit
     */
    public Budget(int ID,int accountID, double currentSpent, double limit,TransactionType category) {
        budgetID=ID;
        this.accountID=accountID;
        this.currentSpent=currentSpent;
        spendingLimit=limit;
        categoryForBudget=category;
    }

    /**
     *
     * @param limit
     */
    public void setSpendingLimit(double limit) {
        this.spendingLimit = limit;
    }

    /**
     *
     * @param category
     */
    public void setBudgetCategory(TransactionType category) {
        // TODO - implement Budget.setBudgetCategory
        throw new UnsupportedOperationException();
    }

    public int getBudgetID() {
        return this.budgetID;
    }

    public double getSpendingLimit() {
        return this.spendingLimit;
    }

    public double getCurrentSpent(){return this.currentSpent;}

    public void setCurrentSpend(double transactionAmount){
        this.currentSpent=this.currentSpent+transactionAmount;
    }

    public TransactionType getCategoryForBudget() {
        return this.categoryForBudget;
    }

}