import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

SQLiteConnection db = SQLiteConnection.getInstance();
ResultSet rs = null;
Account account;
ArrayList<Transaction> transactions;
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Account Display.fxml"));
                        Parent root=loader.load();
                        Controller controller= loader.getController();
                        controller.name.setText("Welcome, "+account.getFirstName()+ " "+account.getLastName());
                        primaryStage.setTitle("Stock GUI");
                        primaryStage.setScene(new Scene(root, 763, 521));
                        primaryStage.initModality(Modality.APPLICATION_MODAL);
                        primaryStage.initOwner(login.getScene().getWindow());
                        primaryStage.showAndWait();
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

    public void displayinfo(){

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}