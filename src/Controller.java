    import javafx.beans.Observable;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.event.Event;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
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
    public class Controller {
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
    TextField budgetLimit;
    @FXML
    Button addBudget;
    @FXML
    Button Confirm2;
    @FXML
    Button close2;
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
    @FXML
    TableView TransactionTable;
    @FXML
    TableView BudgetTable;
    @FXML
    TableColumn names;
    @FXML
    TableColumn amounts;
    @FXML
    TableColumn categories;
    @FXML
    TableColumn categories2;
    @FXML
    TableColumn limits;
    @FXML
    TableColumn spends;
    @FXML
    Label exists;
    @FXML
    Button deleteAccount;
    @FXML
    Button close3;
    @FXML
    TextField changepword;
    @FXML
    TextField changeuname;
    @FXML
    TextField changeName;
    @FXML
    TextField changelastname;
    @FXML
    TextField changeamount;
    @FXML
    TextField changeincome;
    @FXML
    Button cpi;
    @FXML
    Button change;
    @FXML
    Button refreshTable;
    @FXML
    Button close4;

    private static Account account;
    private static ArrayList<Transaction> transactions= new ArrayList<Transaction>();
    private static Transaction transaction;
    private static Budget budget;
    private static Transaction deletedTransaction;
    private static Budget deletedBudget;
    private static ArrayList<Budget> budgets= new ArrayList<Budget>();
    ObservableList data= FXCollections.observableList(transactions);
    ObservableList data2= FXCollections.observableList(budgets);
    Admin admin=Admin.getInstance();

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
            boolean flag=admin.addAccount(firstname.getText(),secondname.getText(),username2.getText(),password2.getText(),amount.getText(),income.getText());
            if(flag) exists.setText("Username already exists");
            else {
                Stage stage = (Stage) register.getScene().getWindow();
                stage.close();
            }
        }

        public void login(ActionEvent event) throws Exception{
            account=admin.searchAccount(username.getText(),password.getText());
                        if(account!=null){
                            loginFail.setText("");
                            account=admin.searchAccount(username.getText(),password.getText());
                            Stage primaryStage = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountDisplay.fxml"));
                            Parent root=loader.load();
                            Controller controller= loader.getController();
                            transactions.removeAll(data);
                            budgets.removeAll(data2);
                            controller.displayTransaction();
                            controller.displayBudgets();
                            controller.name.setText("Welcome, "+account.getFirstName()+ " "+account.getLastName());
                            int counter=0;
                            for(int i=0; i<budgets.size();i++) if(budgets.get(i).getCurrentSpent()>budgets.get(i).getSpendingLimit()) counter++;
                            if(counter==0) controller.moneyinfo.setText("You have £"+account.getAccountAmount()+" in your account\nYour income is £"+account.getIncome()+ " per month.\nYou haven't gone over any budgets.");
                            else if(counter==1) controller.moneyinfo.setText("You have £"+account.getAccountAmount()+" in your account\nYour income is £"+account.getIncome()+ " per month.\nYou have gone over 1 budget.");
                            else controller.moneyinfo.setText("You have £"+account.getAccountAmount()+" in your account\nYour income is £"+account.getIncome()+ " per month.\nYou have gone over "+ counter+ " budgets.");
                            controller.refresh();
                            primaryStage.setTitle("Stock GUI");
                            primaryStage.setScene(new Scene(root));
                            primaryStage.initModality(Modality.NONE);
                            primaryStage.initOwner(login.getScene().getWindow());
                            primaryStage.show();
                        }
                        else{
                            loginFail.setText("Incorrect credentials");
                            loginFail.setTextFill(Color.valueOf("red"));
                        }

                }

        public void opendeleteAccount() throws IOException{
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("confirmDeleteAccount.fxml"));
            primaryStage.setTitle("Register");
            primaryStage.setScene(new Scene(root));
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.initOwner(deleteAccount.getScene().getWindow());
            primaryStage.showAndWait();
        }

        public void confirmDeleteAccount(){
            admin.deleteAccount(account);
            Stage stage= (Stage) close4.getScene().getWindow();
            stage.close();
        }

        public void cancelDeleteAccount(){
            Stage stage = (Stage) close3.getScene().getWindow();
            stage.close();
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
            Date date = Date.valueOf(transDate.getValue());

            if (transType.getText().equals("Transport")) {
                transaction=new Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date, TransactionType.Transport);
            } else if (transType.getText().equals("Food")) {
                transaction= new Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date, TransactionType.Food);
            } else if (transType.getText().equals("Accomodation")) {
                transaction=new Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date, TransactionType.Accomodation);
            } else if (transType.getText().equals("Leisure")) {
                transaction=new  Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date, TransactionType.Leisure);
            } else if (transType.getText().equals("Debt")) {
                transaction=new  Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date, TransactionType.Debt);
            } else if (transType.getText().equals("Savings")) {
                transaction=new Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date, TransactionType.Savings);
            } else if (transType.getText().equals("Other") ) {
                transaction=new Transaction(transactions.size(),account.getAccountID(),transName.getText(),Integer.parseInt(transAmount.getText()),date, TransactionType.Other);
            }

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
            admin.addTransaction(account,transactions);
            data=FXCollections.observableList(transactions);
            searchBudgetOfCategory(transaction);

            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();

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
            admin.deleteTransaction(deletedTransaction);
            transactions.remove(deletedTransaction);
            data=FXCollections.observableList(transactions);
            Stage stage = (Stage) confirmDelete.getScene().getWindow();
            stage.close();

        }

        public void displayTransaction(){
                admin.getTransactions(account,transactions);
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
                else if (budgetType.getText().equals("General") ) {
                    budget=new Budget(budgets.size(),account.getAccountID(),0,Double.parseDouble(budgetLimit.getText()), TransactionType.General);
                }
                System.out.println(budget.getBudgetID());

                boolean flag=admin.checkBudget(account,budget);

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
                else budgetExists.setText("You already have a budget for this category");
            }
            public void confirmBudget(ActionEvent event){
                budgets.add(budget);
                admin.addBudget(account,budgets);

                Stage stage = (Stage) close2.getScene().getWindow();
                stage.close();
            }
            public void refreshTable(){
                int counter=0;
                for(int i=0; i<budgets.size();i++) if(budgets.get(i).getCurrentSpent()>budgets.get(i).getSpendingLimit()) counter++;
                if(counter==0) moneyinfo.setText("You have £"+account.getAccountAmount()+" in your account\nYour income is £"+account.getIncome()+ " per month.\nYou haven't gone over any budgets.");
                else if(counter==1) moneyinfo.setText("You have £"+account.getAccountAmount()+" in your account\nYour income is £"+account.getIncome()+ " per month.\nYou have gone over 1 budget.");
                else moneyinfo.setText("You have £"+account.getAccountAmount()+" in your account\nYour income is £"+account.getIncome()+ " per month.\nYou have gone over "+ counter+ " budgets.");
                TransactionTable.refresh();
                BudgetTable.refresh();
            }
            public void cancelBudget(ActionEvent event){
                Stage stage = (Stage) close2.getScene().getWindow();
                stage.close();
            }
        public void displayBudgets(){
            admin.getBudgets( account, budgets );
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
            admin.deleteBudget(deletedBudget);
            budgets.remove(deletedBudget);
            Stage stage = (Stage) confirmBudgetDelete.getScene().getWindow();
            stage.close();

        }
        public void searchBudgetOfCategory(Transaction transaction){
            try {
                for (int i = 0; i < budgets.size(); i++) {
                    if (budgets.get(i).getCategoryForBudget() == transaction.getCategoryOfTransaction() || budgets.get(i).getCategoryForBudget()==TransactionType.General) {
                        budgets.get(i).setCurrentSpend(transaction.getTransactionAmount());
                        admin.updateBudget(account, budgets.get(i));

                    }
                }
            }
            catch(Exception e){e.printStackTrace();}
        }

        public void openChange(ActionEvent event)throws IOException{
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("changeInfo.fxml"));
            Parent root=loader.load();
            primaryStage.setTitle("Stock GUI");
            primaryStage.setScene(new Scene(root));
            primaryStage.initModality(Modality.NONE);
            primaryStage.initOwner(cpi.getScene().getWindow());
            primaryStage.show();
        }

        public void confirmChange(ActionEvent event)throws IOException{

            String newPassword=changepword.getText();
            String newAmount=changeamount.getText();
            String newIncome=changeincome.getText();
            admin.updateAccount(account,newPassword,newAmount,newIncome);
            Stage stage = (Stage) change.getScene().getWindow();
            stage.close();

        }


          public void refresh(){
                TransactionTable.setItems(data);
                names.setCellValueFactory(new PropertyValueFactory<Transaction,String>("transactionName"));
                amounts.setCellValueFactory(new PropertyValueFactory<Transaction,String>("transactionAmount"));
                categories.setCellValueFactory(new PropertyValueFactory<Transaction,String>("categoryOfTransaction"));

                BudgetTable.setItems(data2);
                categories2.setCellValueFactory(new PropertyValueFactory<Transaction,String>("categoryForBudget"));
                limits.setCellValueFactory(new PropertyValueFactory<Transaction,String>("spendingLimit"));
                spends.setCellValueFactory(new PropertyValueFactory<Transaction,String>("currentSpent"));
            }

            public void logout(){
                Stage stage = (Stage) refresh.getScene().getWindow();
                stage.close();
            }
    }