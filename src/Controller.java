import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;
public class Controller implements Initializable {


@FXML
Button login;
@FXML
TextField username;
@FXML
TextField password;
@FXML
Label loginFail;

SQLiteConnection db = SQLiteConnection.getInstance();
ResultSet rs = null;

public void login(ActionEvent event) throws Exception{

        String sql = "SELECT Username, Password FROM login";
        try {
            rs = db.query(sql);
            String user = username.getText();
            String pword = password.getText();
            while (rs.next()) {
                if (rs.getString("Username").equals(user) && rs.getString("Password").equals(pword))
                {
                    loginFail.setText("");
                    Stage primaryStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("Account Display.fxml"));
                    primaryStage.setTitle("Stock GUI");
                    primaryStage.setScene(new Scene(root, 763, 521));
                    primaryStage.initModality(Modality.APPLICATION_MODAL);
                    primaryStage.initOwner(login.getScene().getWindow());
                    primaryStage.showAndWait();

                }
                else {
                    loginFail.setText("Either your username and password is incorrect");
                }
            }
        }
        catch (Exception e)
        {e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}