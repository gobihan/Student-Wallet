public class Budget {

    private int budgetID;
    private int accountID;
    private String budgetName;
    private double currentSpent;
    private double spendingLimit;
    private TransactionType categoryForBudget;

    /**
     *
     * @param ID
     * @param limit
     */
    public Budget(int ID,int accountID,String name, double currentSpent, double limit,TransactionType category) {
        budgetID=ID;
        this.accountID=accountID;
        this.budgetName=name;
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

    public int getAcccountID(){return this.accountID;}

    public String getBudgetName(){return this.budgetName;}

    public double getSpendingLimit() {
        return this.spendingLimit;
    }

    public double getCurrentSpent(){return this.currentSpent;}

    public TransactionType getCategoryForBudget() {
        return this.categoryForBudget;
    }

}