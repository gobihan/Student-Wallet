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
import java.util.*;
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

SQLiteConnection db = SQLiteConnection.getInstance();
ResultSet rs = null;
static Account account;
static ArrayList<Transaction> transactions= new ArrayList<Transaction>();
ArrayList<Budget> budgets;

    public void openRegister(ActionEvent event)throws Exception{
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        primaryStage.setTitle("Register");
        primaryStage.setScene(new Scene(root, 500, 400));
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
                        for(int i =0; i <transactions.size(); i++) controller.TransactionList.appendText(""+transactions.get(i).getTransactionName()+" "+ transactions.get(i).getTransactionAmount()+"\n");
                        primaryStage.setTitle("Stock GUI");
                        primaryStage.setScene(new Scene(root, 763, 521));
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
        Controller controller= loader.getController();
        primaryStage.setTitle("Stock GUI");
        primaryStage.setScene(new Scene(root, 281, 242));
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
    public void inputTransaction(ActionEvent event){
        String sql= "INSERT INTO Transactions ( AccountID, Amount , Name, Category, Date) "+"VALUES('"+account.getAccountID()+"','"+transAmount.getText()+"','"+transName.getText()+"','"+transType.getText()+"','2017-03-17');";
        System.out.println(sql);
        try {
            rs=db.query(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayTransaction(){

            String sql = "SELECT ID, AccountID, Amount, Name, Category FROM Transactions";

            try {
                rs = db.query(sql);
                while (rs.next()) {
                    if(rs.getInt("AccountID") == account.getAccountID()) {
                        if (rs.getString("Category").equals("Transport")) {
                            transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), new Date(), TransactionType.Transport));
                        } else if (rs.getString("Category").equals("Food")) {
                            transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), new Date(), TransactionType.Food));
                        } else if (rs.getString("Category").equals("Accomodation")) {
                            transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), new Date(), TransactionType.Accomodation));
                        } else if (rs.getString("Category").equals("Leisure")) {
                            transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), new Date(), TransactionType.Leisure));
                        } else if (rs.getString("Category").equals("Debt")) {
                            transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), new Date(), TransactionType.Debt));
                        } else if (rs.getString("Category").equals("Savings")) {
                            transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), new Date(), TransactionType.Savings));
                        } else if (rs.getString("Category").equals("Other")) {
                            transactions.add(new Transaction(rs.getInt("ID"), rs.getInt("AccountID"), rs.getString("Name"), rs.getInt("Amount"), new Date(), TransactionType.Other));
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}