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

SQLiteConnection db = SQLiteConnection.getInstance();
ResultSet rs = null;
static Account account;
static ArrayList<Transaction> transactions= new ArrayList<Transaction>();
ArrayList<Budget> budgets;

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
                    loginFail.setText("Either your username and password is incorrect");
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
        Transaction transaction=null;
        if (transType.getText().equals("Transport")) {
            transaction=new Transaction(transactions.size()+1,account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Transport);
        } else if (transType.getText().equals("Food")) {
            transaction= new Transaction(transactions.size()+1,account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Food);
        } else if (transType.getText().equals("Accomodation")) {
            transaction=new Transaction(transactions.size()+1,account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Accomodation);
        } else if (transType.getText().equals("Leisure")) {
            transaction=new  Transaction(transactions.size()+1,account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Leisure);
        } else if (transType.getText().equals("Debt")) {
            transaction=new  Transaction(transactions.size()+1,account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Debt);
        } else if (transType.getText().equals("Savings")) {
            transaction=new Transaction(transactions.size()+1,account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Savings);
        } else if (transType.getText().equals("Other") ) {
            transaction=new Transaction(transactions.size()+1,account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date2, TransactionType.Other);
        }
        System.out.println(transaction.getTransactionName());
        transactions.add(transaction);

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
        String sql = "INSERT INTO Transactions ( AccountID, Amount , Name, Category, Date) " + "VALUES('" + account.getAccountID() + "','" + transactions.get(transactions.size() - 1).getTransactionAmount() + "','" + transactions.get(transactions.size() - 1).getTransactionName() + "','" + transactions.get(transactions.size() - 1).getCategoryOfTransaction().toString() + "','"+transactions.get(transactions.size()-1).getTransactionDate().toString()+"');";
        System.out.println(sql);
        try {
            rs = db.query(sql);
        } catch (NullPointerException npe) {
            System.out.println("Well done");
        }
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void cancelTransaction(ActionEvent event){
        Stage stage = (Stage) close.getScene().getWindow();
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

        public void refresh(){
            TransactionList.clear();
            for(int i =0; i <transactions.size(); i++) TransactionList.appendText(""+transactions.get(i).getTransactionName()+" "+ transactions.get(i).getTransactionAmount()+"\n");
        }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}