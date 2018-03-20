import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;
public class Controller implements Initializable {
@FXML
Button login;
@FXML
TextField username;
@FXML
TextField password;
@FXML
Button register;
@FXML
TextField password2;
@FXML
TextField username2;
@FXML
TextField firstname;
@FXML
TextField secondname;
@FXML
TextField amount;
@FXML
TextField income;
@FXML
Label loginFail;
@FXML
Hyperlink openRegister;
@FXML
Label name;
@FXML
Label moneyinfo;
@FXML
Button addTrans;
@FXML
Button confirmTrans;
@FXML
TextField transName;
@FXML
TextField transAmount;
@FXML
MenuButton transType;
@FXML
DatePicker transDate;
@FXML
MenuItem Transport;
@FXML
MenuItem Food;
@FXML
MenuItem Accomodation;
@FXML
MenuItem Leisure;
@FXML
MenuItem Debt;
@FXML
MenuItem Savings;
@FXML
MenuItem Other;
@FXML
TextArea TransactionList;
@FXML
Button Confirm;
@FXML
Button close;
@FXML
Button refresh;
@FXML
Button deleter;
@FXML
Button searchCancel;
@FXML
TextField deleteName;
@FXML
TextArea displayDelete;
@FXML
Button delete;
@FXML
Button confirmDelete;
@FXML
Button budgetAdd;
@FXML
MenuButton budgetType;
@FXML
MenuItem General;
@FXML
MenuItem Transport2;
@FXML
MenuItem Food2;
@FXML
MenuItem Accomodation2;
@FXML
MenuItem Leisure2;
@FXML
MenuItem Debt2;
@FXML
MenuItem Savings2;
@FXML
MenuItem Other2;
@FXML
TextField budgetName;
@FXML
TextField budgetLimit;
@FXML
Button addBudget;
@FXML
Button Confirm2;
@FXML
Button close2;
@FXML
TextArea BudgetList;
@FXML
TextArea displaybudgetDelete;
@FXML
TextField deletebudgetName;
@FXML
Button searchCancel2;
@FXML
Button deleter2;
@FXML
Button confirmBudgetDelete;
@FXML
Label budgetExists;

SQLiteConnection db = SQLiteConnection.getInstance();
ResultSet rs = null;
private static Account account;
private static ArrayList<Transaction> transactions= new ArrayList<Transaction>();
private static Transaction transaction;
private static Budget budget;
private static Transaction deletedTransaction;
private static Budget deletedBudget;
private static ArrayList<Budget> budgets= new ArrayList<Budget>();

    public void openRegister(ActionEvent event)throws Exception{
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        primaryStage.setTitle("Register");
        primaryStage.setScene(new Scene(root));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(login.getScene().getWindow());
        primaryStage.showAndWait();
    }

    public void register(ActionEvent event) throws Exception{
        Account account= new Account(1,"Gobihan","Manogarasingam","gobz97","monkeyboy10",420, 42000);
        String sql= "INSERT INTO Account (Firstname,Lastname,Username,Password,Amount,Income) "+"VALUES('"+firstname.getText()+"','"+secondname.getText()+"','"+username2.getText()+"','"+password2.getText()+"',"+amount.getText()+","+income.getText()+");";
        System.out.println(sql);
        try {
            rs=db.query(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void login(ActionEvent event) throws Exception{
            String sql = "SELECT  ID, Firstname, Lastname, Username, Password, Amount, Income  FROM Account";
            try {
                rs = db.query(sql);
                String user = username.getText();
                String pword = password.getText();
                boolean flag=false;
                while (rs.next()) {
                    if (rs.getString("Username").equals(user) && rs.getString("Password").equals(pword)){
                        flag=true;
                        loginFail.setText("");
                        account=new Account(rs.getInt("ID"),rs.getString("Firstname"),rs.getString("Lastname"),rs.getString("Username"),rs.getString("Password"),rs.getInt("Amount"),rs.getInt("Income"));
                        Stage primaryStage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountDisplay.fxml"));
                        Parent root=loader.load();
                        Controller controller= loader.getController();
                        controller.displayTransaction();
                        controller.displayBudgets();
                        controller.name.setText("Welcome, "+account.getFirstName()+ " "+account.getLastName());
                        controller.moneyinfo.setText("You have £"+account.getAccountAmount()+" in your account\nYour income is £"+account.getIncome()+ " per month.\nYou haven't gone over any budgets.");
                        controller.refresh();
                        primaryStage.setTitle("Stock GUI");
                        primaryStage.setScene(new Scene(root));
                        primaryStage.initModality(Modality.NONE);
                        primaryStage.initOwner(login.getScene().getWindow());
                        primaryStage.show();
                    }
                }
                if(!flag){
                    loginFail.setText("Incorrect credentials");
                    loginFail.setTextFill(Color.valueOf("red"));
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void addTransaction(ActionEvent event)throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addTransaction.fxml"));
        Parent root=loader.load();
        primaryStage.setTitle("Stock GUI");
        primaryStage.setScene(new Scene(root));
        primaryStage.initModality(Modality.NONE);
        primaryStage.initOwner(addTrans.getScene().getWindow());
        primaryStage.show();

    }

    public void transactionType(ActionEvent event){
            if(event.getSource()==Transport) {
                transType.setText("Transport");
            }
            else if(event.getSource()==Food){
                transType.setText("Food");
            }
            else if(event.getSource()==Accomodation){
                transType.setText("Accomodation");
            }
            else if(event.getSource()==Leisure){
                transType.setText("Leisure");
            }
            else if(event.getSource()==Debt){
                transType.setText("Debt");
            }
            else if(event.getSource()==Savings){
                transType.setText("Savings");
            }
            else if(event.getSource()==Other){
                transType.setText("Other");
            }
    }
    public void inputTransaction(ActionEvent event) throws IOException{
        String date=transDate.getConverter().toString(transDate.getValue());
        System.out.println(transDate.getValue());
        String[] dateStuff=date.split("/");
        System.out.println(dateStuff[0]+ dateStuff[1] + dateStuff[2]);
        java.sql.Date date1= new java.sql.Date(Integer.parseInt(dateStuff[2]),Integer.parseInt(dateStuff[1]), Integer.parseInt(dateStuff[0]));

        Date date2 = Date.valueOf(transDate.getValue());
        System.out.println(date2);
        if (transType.getText().equals("Transport")) {
            transaction=new Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Transport);
        } else if (transType.getText().equals("Food")) {
            transaction= new Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Food);
        } else if (transType.getText().equals("Accomodation")) {
            transaction=new Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Accomodation);
        } else if (transType.getText().equals("Leisure")) {
            transaction=new  Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Leisure);
        } else if (transType.getText().equals("Debt")) {
            transaction=new  Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Debt);
        } else if (transType.getText().equals("Savings")) {
            transaction=new Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Savings);
        } else if (transType.getText().equals("Other") ) {
            transaction=new Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Other);
        }
        System.out.println(transaction.getTransactionName());

        Stage stage = (Stage) confirmTrans.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("confirm.fxml"));
        Parent root=loader.load();
        primaryStage.setTitle("Stock GUI");
        primaryStage.setScene(new Scene(root));
        primaryStage.initModality(Modality.NONE);
        primaryStage.initOwner(confirmTrans.getScene().getWindow());
        primaryStage.show();

    }
    public void confirmTransaction(ActionEvent event)throws SQLException {
        transactions.add(transaction);
        String sql = "INSERT INTO Transactions ( AccountID, Amount , Name, Category, Date) " + "VALUES('" + account.getAccountID() + "','" + transactions.get(transactions.size() - 1).getTransactionAmount() + "','" + transactions.get(transactions.size() - 1).getTransactionName() + "','" + transactions.get(transactions.size() - 1).getCategoryOfTransaction().toString() + "','"+transactions.get(transactions.size()-1).getTransactionDate().toString()+"');";
        System.out.println(sql);
        try {
            rs = db.query(sql);
        } catch (NullPointerException npe) {
            System.out.println("Well done");
        }
        searchBudgetOfCategory(transaction);
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
//        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("AccountDisplay.fxml"));
//        Controller c = loader1.getController();
//        c.refresh();
    }

    public void cancelTransaction(ActionEvent event){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    public void openDelete()throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteTransaction.fxml"));
        Parent root=loader.load();
        primaryStage.setTitle("Stock GUI");
        primaryStage.setScene(new Scene(root));
        primaryStage.initModality(Modality.NONE);
        primaryStage.initOwner(delete.getScene().getWindow());
        primaryStage.show();

    }
    public void searchTransaction(ActionEvent event){
        Transaction transaction=null;
        for(int i=0; i<transactions.size(); i++){
            if(transactions.get(i).getTransactionName().equals(deleteName.getText())){
                transaction=transactions.get(i);
                break;
            }
        }
        if(transaction==null){
            System.out.println("No such transaction");
            return;
        }

        displayDelete.appendText(transaction.getTransactionName()+"\n"+transaction.getTransactionAmount()+"\n"+transaction.getCategoryOfTransaction().toString()+"\n"+transaction.getTransactionDate().toString());
        deletedTransaction=transaction;
    }

    public void confirmDelete(ActionEvent event)throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("confirmDelete.fxml"));
        Parent root=loader.load();
        primaryStage.setTitle("Stock GUI");
        primaryStage.setScene(new Scene(root));
        primaryStage.initModality(Modality.NONE);
        primaryStage.initOwner(deleter.getScene().getWindow());
        primaryStage.show();
    }
    public void deleteTransaction(){
        String sql="DELETE FROM Transactions WHERE ID='"+deletedTransaction.getTransactionID()+"';";
        try{
            rs=db.query(sql);
            transactions.remove(deletedTransaction);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Stage stage = (Stage) confirmDelete.getScene().getWindow();
        stage.close();

    }

    public void displayTransaction(){
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

        public void addBudget(ActionEvent event)throws IOException{
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addBudget.fxml"));
            Parent root=loader.load();
            primaryStage.setTitle("Stock GUI");
            primaryStage.setScene(new Scene(root));
            primaryStage.initModality(Modality.NONE);
            primaryStage.initOwner(budgetAdd.getScene().getWindow());
            primaryStage.show();
        }
        public void budgetType(ActionEvent event){
            if(event.getSource()==Transport2) {
                budgetType.setText("Transport");
            }
            else if(event.getSource()==General){
                budgetType.setText("General");
            }
            else if(event.getSource()==Food2){
                budgetType.setText("Food");
            }
            else if(event.getSource()==Accomodation2){
                budgetType.setText("Accomodation");
            }
            else if(event.getSource()==Leisure2){
                budgetType.setText("Leisure");
            }
            else if(event.getSource()==Debt2){
                budgetType.setText("Debt");
            }
            else if(event.getSource()==Savings2){
                budgetType.setText("Savings");
            }
            else if(event.getSource()==Other2){
                budgetType.setText("Other");
            }
        }
        public void inputBudget(ActionEvent event)throws IOException{


            if (budgetType.getText().equals("Transport")) {
                budget=new Budget(budgets.size(),account.getAccountID(),0,Double.parseDouble(budgetLimit.getText()), TransactionType.Transport);
            } else if (budgetType.getText().equals("Food")) {
                budget=new Budget(budgets.size(),account.getAccountID(),0,Double.parseDouble(budgetLimit.getText()), TransactionType.Food);
            } else if (budgetType.getText().equals("Accomodation")) {
                budget=new Budget(budgets.size(),account.getAccountID(),0,Double.parseDouble(budgetLimit.getText()), TransactionType.Accomodation);
            } else if (budgetType.getText().equals("Leisure")) {
                budget=new Budget(budgets.size(),account.getAccountID(),0,Double.parseDouble(budgetLimit.getText()), TransactionType.Leisure);
            } else if (budgetType.getText().equals("Debt")) {
                budget=new Budget(budgets.size(),account.getAccountID(),0,Double.parseDouble(budgetLimit.getText()), TransactionType.Debt);
            } else if (budgetType.getText().equals("Savings")) {
                budget=new Budget(budgets.size(),account.getAccountID(),0,Double.parseDouble(budgetLimit.getText()), TransactionType.Savings);
            } else if (budgetType.getText().equals("Other") ) {
                budget=new Budget(budgets.size(),account.getAccountID(),0,Double.parseDouble(budgetLimit.getText()), TransactionType.Other);
            }
            System.out.println(budget.getBudgetID());
            boolean flag=false;
            String sql="SELECT AccountID, Category FROM Budgets";
            try{
                rs=db.query(sql);
                while(rs.next()){
                    if(account.getAccountID()==rs.getInt("AccountID") && budget.getCategoryForBudget().toString().equals(rs.getString("Category"))){
                        System.out.println("Fuck off");
                        flag=true;
                        budgetExists.setText("You already have a budget for this category");
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            if(!flag) {
                Stage stage = (Stage) addBudget.getScene().getWindow();
                stage.close();

                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("confirmBudget.fxml"));
                Parent root = loader.load();
                primaryStage.setTitle("Stock GUI");
                primaryStage.setScene(new Scene(root));
                primaryStage.initModality(Modality.NONE);
                primaryStage.initOwner(addBudget.getScene().getWindow());
                primaryStage.show();
            }
        }
        public void confirmBudget(ActionEvent event){
            budgets.add(budget);
            String sql = "INSERT INTO Budgets ( AccountID, CurrentSpent, SpendingLimit , Name, Category) " + "VALUES('" + account.getAccountID() + "','" + budgets.get(budgets.size() - 1).getCurrentSpent()+ "','" + budgets.get(budgets.size() - 1).getSpendingLimit()+ "','lool','" + budgets.get(budgets.size() - 1).getCategoryForBudget().toString() + "');";
            System.out.println(sql);
            try {
                rs = db.query(sql);
            } catch (NullPointerException npe) {
                System.out.println("Well done");
            }
            Stage stage = (Stage) close2.getScene().getWindow();
            stage.close();
        }
        public void cancelBudget(ActionEvent event){
            Stage stage = (Stage) close2.getScene().getWindow();
            stage.close();
        }
    public void displayBudgets(){
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
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void openBudgetDelete()throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteBudget.fxml"));
        Parent root=loader.load();
        primaryStage.setTitle("Stock GUI");
        primaryStage.setScene(new Scene(root));
        primaryStage.initModality(Modality.NONE);
        primaryStage.initOwner(delete.getScene().getWindow());
        primaryStage.show();

    }
    public void searchBudget(ActionEvent event){
        Budget budget=null;
        for(int i=0; i<budgets.size(); i++){
            if(budgets.get(i).getCategoryForBudget().toString().equals(deletebudgetName.getText())){
                budget=budgets.get(i);
                break;
            }
        }
        if(budget==null){
            System.out.println("No such transaction");
            return;
        }

        displaybudgetDelete.appendText(budget.getSpendingLimit()+"\n"+budget.getCategoryForBudget().toString()+"\n"+budget.getCurrentSpent());
        deletedBudget=budget;
    }
    public void confirmBudgetDelete(ActionEvent event)throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("confirmBudgetDelete.fxml"));
        Parent root=loader.load();
        primaryStage.setTitle("Stock GUI");
        primaryStage.setScene(new Scene(root));
        primaryStage.initModality(Modality.NONE);
        primaryStage.initOwner(deleter2.getScene().getWindow());
        primaryStage.show();
    }
    public void deleteBudget(){
        String sql="DELETE FROM Budgets WHERE ID='"+deletedBudget.getBudgetID()+"';";
        try{
            rs=db.query(sql);
            budgets.remove(deletedBudget);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Stage stage = (Stage) confirmBudgetDelete.getScene().getWindow();
        stage.close();

    }
    public void searchBudgetOfCategory(Transaction transaction){
        try {
            for (int i = 0; i < budgets.size(); i++) {
                if (budgets.get(i).getCategoryForBudget() == transaction.getCategoryOfTransaction()) {
                    budgets.get(i).setCurrentSpend(transaction.getTransactionAmount());
                    String sql = " UPDATE Budgets SET CurrentSpent='" + budgets.get(i).getCurrentSpent() + "' WHERE ID='" + budgets.get(i).getBudgetID() + "' AND AccountID='" + account.getAccountID() + "';";
                    System.out.println(sql);

                    rs = db.query(sql);

                }
            }
        }
        catch(Exception e){e.printStackTrace();}


    }

        public void refresh(){
            TransactionList.clear();
            BudgetList.clear();
            for(int i =0; i <transactions.size(); i++) TransactionList.appendText(""+transactions.get(i).getTransactionName()+" "+ transactions.get(i).getTransactionAmount()+"\n");
            for(int j=0; j<budgets.size(); j++) BudgetList.appendText(""+budgets.get(j).getCategoryForBudget().toString()+" "+budgets.get(j).getSpendingLimit()+"\n");
        }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}