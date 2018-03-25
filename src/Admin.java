import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Admin {
private ResultSet rs;
private SQLiteConnection db;

    private Admin() {
        this.db = SQLiteConnection.getInstance();
        this.rs = null;
    }

    private static final Admin INSTANCE = new Admin();
    public static Admin getInstance() { return INSTANCE;}

    public Account searchAccount(String username, String password) {
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date1 = new Date();
       // System.out.println(dateFormat.format(date1));
    Account account=null;
        String sql = "SELECT  ID, Firstname, Lastname, Username, Password, Amount, Income, Date FROM Account";
        try {
            rs = db.query(sql);
            while (rs.next()) {
                if (rs.getString("Username").equals(username) && rs.getString("Password").equals(password)) {
                    java.util.Date date=new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("Date"));
//                    if(dateFormat.format(date).equals(dateFormat.format(date1))) {
//                         account = new Account(rs.getInt("ID"), rs.getString("Firstname"), rs.getString("Lastname"), rs.getString("Username"), rs.getString("Password"), rs.getInt("Amount")+rs.getInt("Income"), rs.getInt("Income"), date);
//                    }
                     account = new Account(rs.getInt("ID"), rs.getString("Firstname"), rs.getString("Lastname"), rs.getString("Username"), rs.getString("Password"), rs.getInt("Amount"), rs.getInt("Income"), date);

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }

    public boolean addAccount(String firstname, String secondname, String username, String password, String amount, String income) {
        boolean flag=false;
        String sql0 = "SELECT Username FROM Account";
        try {
            rs = db.query(sql0);
            while (rs.next()) {
                if (username.equals(rs.getString("Username"))) {
                    System.out.println("Fuck off");
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!flag) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            String sql = "INSERT INTO Account (Firstname,Lastname,Username,Password,Amount,Income, Date) " + "VALUES('" + firstname + "','" + secondname + "','" + username + "','" + password + "'," + amount + "," + income + ",'"+dateFormat.format(date)+"');";
            System.out.println(sql);
            try {
                db.update(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     *
     * @param account
     */
    public void deleteAccount(Account account) {
        String sql="DELETE FROM Account WHERE ID='"+account.getAccountID()+"';";
        try{ db.update(sql);}
        catch(Exception e){e.printStackTrace();}
    }

    /**
     *
     * @param account
     */
    public void updateAccount(Account account, String newPassword, String newAmount, String newIncome) {
        if(!newPassword.equals("")){
            String sql0="UPDATE Account SET Password='"+newPassword+"' WHERE ID='"+account.getAccountID()+"'";
            try{
                db.update(sql0);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!newAmount.equals("")){
            String sql0="UPDATE Account SET Amount='"+newAmount+"' WHERE ID='"+account.getAccountID()+"'";
            try{
                db.update(sql0);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!newIncome.equals("")){
            String sql0="UPDATE Account SET Income='"+newIncome+"' WHERE ID='"+account.getAccountID()+"'";
            try{
                db.update(sql0);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param
     */
    public void addTransaction(Account account, ArrayList<Transaction> transactions) {
        String sql = "INSERT INTO Transactions ( AccountID, Amount , Name, Category, Date) " + "VALUES('" + account.getAccountID() + "','" + transactions.get(transactions.size() - 1).getTransactionAmount() + "','" + transactions.get(transactions.size() - 1).getTransactionName() + "','" + transactions.get(transactions.size() - 1).getCategoryOfTransaction().toString() + "','"+transactions.get(transactions.size()-1).getTransactionDate().toString()+"');";
        System.out.println(sql);
        try {
            db.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTransaction(Transaction transaction){
        String sql="DELETE FROM Transactions WHERE ID='"+transaction.getTransactionID()+"';";
        try{
            db.update(sql);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void getTransactions(Account account, ArrayList<Transaction> transactions){
        String sql = "SELECT ID, AccountID, Amount, Name, Category, Date FROM Transactions";

        try {
            rs = db.query(sql);
            while (rs.next()) {
                if(rs.getInt("AccountID") == account.getAccountID()) {
                    java.util.Date date=new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("Date"));
                    if (rs.getString("Category").equals("Transport")) {
                        transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), date, TransactionType.Transport));
                    } else if (rs.getString("Category").equals("Food")) {
                        transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), date, TransactionType.Food));
                    } else if (rs.getString("Category").equals("Accomodation")) {
                        transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), date, TransactionType.Accomodation));
                    } else if (rs.getString("Category").equals("Leisure")) {
                        transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), date, TransactionType.Leisure));
                    } else if (rs.getString("Category").equals("Debt")) {
                        transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), date, TransactionType.Debt));
                    } else if (rs.getString("Category").equals("Savings")) {
                        transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), date, TransactionType.Savings));
                    } else if (rs.getString("Category").equals("Other")) {
                        transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), date, TransactionType.Other));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addBudget(Account account, ArrayList<Budget> budgets) {
        String sql = "INSERT INTO Budgets ( AccountID, CurrentSpent, SpendingLimit , Name, Category) " + "VALUES('" + account.getAccountID() + "','" + budgets.get(budgets.size() - 1).getCurrentSpent()+ "','" + budgets.get(budgets.size() - 1).getSpendingLimit()+ "','lool','" + budgets.get(budgets.size() - 1).getCategoryForBudget().toString() + "');";
        System.out.println(sql);
        try {
            db.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param budget
     */
    public void deleteBudget(Budget budget) {
        String sql="DELETE FROM Budgets WHERE ID='"+budget.getBudgetID()+"';";
        try{
            db.update(sql);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkBudget(Account account, Budget budget){
        String sql="SELECT AccountID, Category FROM Budgets";
        try{
            rs=db.query(sql);
            while(rs.next())
                if(account.getAccountID()==rs.getInt("AccountID") && budget.getCategoryForBudget().toString().equals(rs.getString("Category"))) return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public void updateBudget(Account account, Budget budget){
        String sql = " UPDATE Budgets SET CurrentSpent='" + budget.getCurrentSpent() + "' WHERE ID='" + budget.getBudgetID() + "' AND AccountID='" + account.getAccountID() + "';";
        db.update(sql);
    }

    public void getBudgets(Account account, ArrayList<Budget> budgets){
        String sql = "SELECT ID, AccountID, CurrentSpent, SpendingLimit, Category FROM Budgets";

        try {
            rs = db.query(sql);
            while (rs.next()) {
                if(rs.getInt("AccountID") == account.getAccountID()) {
                    if (rs.getString("Category").equals("Transport")) {
                        budgets.add(new Budget(rs.getInt("ID"), rs.getInt("AccountID"),  rs.getInt("CurrentSpent"), rs.getInt("SpendingLimit"), TransactionType.Transport));
                    } else if (rs.getString("Category").equals("Food")) {
                        budgets.add(new Budget(rs.getInt("ID"), rs.getInt("AccountID"),  rs.getInt("CurrentSpent"), rs.getInt("SpendingLimit"), TransactionType.Food));
                    } else if (rs.getString("Category").equals("Accomodation")) {
                        budgets.add(new Budget(rs.getInt("ID"), rs.getInt("AccountID"),  rs.getInt("CurrentSpent"), rs.getInt("SpendingLimit"), TransactionType.Accomodation));
                    } else if (rs.getString("Category").equals("Leisure")) {
                        budgets.add(new Budget(rs.getInt("ID"), rs.getInt("AccountID"), rs.getInt("CurrentSpent"), rs.getInt("SpendingLimit"), TransactionType.Leisure));
                    } else if (rs.getString("Category").equals("Debt")) {
                        budgets.add(new Budget(rs.getInt("ID"), rs.getInt("AccountID"),  rs.getInt("CurrentSpent"), rs.getInt("SpendingLimit"), TransactionType.Debt));
                    } else if (rs.getString("Category").equals("Savings")) {
                        budgets.add(new Budget(rs.getInt("ID"), rs.getInt("AccountID"),  rs.getInt("CurrentSpent"), rs.getInt("SpendingLimit"), TransactionType.Savings));
                    } else if (rs.getString("Category").equals("Other")) {
                        budgets.add(new Budget(rs.getInt("ID"), rs.getInt("AccountID"),  rs.getInt("CurrentSpent"), rs.getInt("SpendingLimit"), TransactionType.Other));
                    }
                    else if (rs.getString("Category").equals("General")) {
                        budgets.add(new Budget(rs.getInt("ID"), rs.getInt("AccountID"),  rs.getInt("CurrentSpent"), rs.getInt("SpendingLimit"), TransactionType.General));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}