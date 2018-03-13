import java.util.*;
public class Controller {


    public static void main(String[] param) {
    Account account = new Account(1, "Gobihan", "Manogarasingam","gobz", "monkeyboy10",10000,5000 );
    Budget budget= new Budget(1,200, TransactionType.Food);
    Transaction transaction= new Transaction(5,10, TransactionType.Transport);
    System.out.println(account.getFirstName());
    System.out.println(budget.getSpendingLimit());
    System.out.println(transaction.getCategoryOfTransaction());
		//Test, Can I edit remotely? - Aleks
		//Second Test, Will this work?
	}
}
