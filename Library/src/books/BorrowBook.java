package books;
import java.sql.*;
import java.util.Scanner;
import java.time.*;
public class BorrowBook implements Interfaces.InterfaceConn {
    Scanner scan=new Scanner(System.in);
    public void Connection(){
        System.out.println("Remember, one member can have maximum 3 books in one time");
        try{
            System.out.println("Put the book title");
            
            String keyboardTitle=scan.nextLine();
            String querend="Select * from books WHERE Title=" + "'" +  keyboardTitle+"'";


            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "12345");
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(querend);

            if (rs.next()){
                String title=rs.getString("Title");
                int amount=rs.getInt("Amount");
                String reguqireAge=rs.getString("RequireAge");

                if(amount>0){
                    System.out.println(title + " ,sztuk    :    " + amount);
                    boolean subtractAmountDecision=setUser(reguqireAge,title);
                    if(subtractAmountDecision==true){
                    int subtractAmount=rs.getInt("Amount")-1;
                    st.executeUpdate("UPDATE books SET Amount=" + subtractAmount
                    +" WHERE Title='" + title + "'" );}
                    }
                
            }else{
                System.out.println("We dont have book with this title");
            }
        }catch(SQLException se){
            se.printStackTrace();
        }
    }


    public boolean setUser(String reguqireAge, String Title){

        System.out.println("We have this book. Now put the name of future user");
        String name=scan.nextLine();
        System.out.println("Now put the last name of future user");
        String lastName=scan.nextLine();
        System.out.println("Almost finish, we need to check user Age. wait..");

        String findQuerend="Select * from library_users WHERE"
        +" Name='"+ name + "'"+ " AND LastName='" + lastName + "'";
        boolean returnValue=false;

        try{
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "12345");
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(findQuerend);

            if(rs.next()){
                String birthdayString=rs.getString("Birthday");
                String birthdayYear="";
                
                for(int i=6; i<10; i++){birthdayYear=birthdayYear+birthdayString.charAt(i);}
                int localtime=(LocalDate.now().getYear());
                int converted=Integer.parseInt(birthdayYear);
                int finalAge=localtime-converted;
                int convertedRequireAge=Integer.parseInt(reguqireAge);

                if(finalAge>convertedRequireAge){
                    System.out.println("Age of user allow him/her to borrow this book");
                    String BookSlot1=rs.getString("Book_1");
                    String BookSlot2=rs.getString("Book_2");
                    String BookSlot3=rs.getString("Book_3");
                    if(BookSlot1==null || BookSlot1==""){
                        st.executeUpdate("UPDATE library_users" +
                        " SET Book_1=" +"'" + Title +"'"+ " WHERE"
                        +" Name='"+ name + "'"+ " AND LastName='" + lastName + "'");
                        returnValue=true;
                    }else if(BookSlot2==null || BookSlot2==""){
                        st.executeUpdate("UPDATE library_users" +
                        " SET Book_2=" +"'" + Title +"'"+ " WHERE"
                    +" Name='"+ name + "'"+ " AND LastName='" + lastName + "'");
                    returnValue=true;
                    }else if(BookSlot3==null || BookSlot3==""){
                        st.executeUpdate("UPDATE library_users" +
                        " SET Book_3=" +"'" + Title +"'"+ " WHERE"
                    +" Name='"+ name + "'"+ " AND LastName='" + lastName + "'");
                    returnValue=true;
                    }else{
                        System.out.println("User have too many books at home."+
                        "He have to give back at least one");
                    }
                    
                }else{
                    returnValue=false;
                    System.out.println("User is too young to read this book");
                }


                
            }
            
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }
}


