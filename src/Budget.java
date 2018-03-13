public class Budget {

    private int budgetID;
    private double spendingLimit;
    private TransactionType categoryForBudget;

    /**
     *
     * @param ID
     * @param limit
     */
    public Budget(int ID, int limit, TransactionType category) {
        budgetID=ID;
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

    public TransactionType getCategoryForBudget() {
        return this.categoryForBudget;
    }

}