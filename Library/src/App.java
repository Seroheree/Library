import java.sql.*;
import java.util.Scanner;
public class App {

    Connection conn;
    Statement st;
    ResultSet rs;
    
    public static void main(String[] args) throws Exception {
        System.out.println("What do you want to do? Enter the number..");
        System.out.println("1. Register new user");
        System.out.println("2. Show books");
        System.out.println("3. Borrow a book");
        Scanner scan=new Scanner(System.in);
        String choosenValue=scan.nextLine();

        switch(choosenValue){

            case "1":{
                Users.Register newUser= new Users.Register();
                newUser.Connection();
                break;
            }
            case "2":{
                books.ShowBooks show= new books.ShowBooks();
                show.Connection();
                break;
            }
            case "3":{
                books.BorrowBook borrow= new books.BorrowBook();
                borrow.Connection();
                break;
            }
        }
        
    }
}
