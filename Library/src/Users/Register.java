package Users;
import java.sql.*;
import java.util.Scanner;


public  class Register implements Interfaces.InterfaceConn{
  public void Connection(){
    try{
      Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "12345");
      Statement stmt=conn.createStatement();
      Scanner scan=new Scanner(System.in);

      System.out.println("Put the name ");
        String name=scan.nextLine();

      System.out.println("Put the last name ");
        String lastName=scan.nextLine();

      System.out.println("Put the birthday "+
      "For example, 1 october 1995 you have to write like 01121995 without any spaces etc ");
      String Birthday=scan.nextLine();

      if(Birthday.length()>8){
        System.out.println("Data of your birthday is too long or you uses special signs. Try Again");
        System.exit(0);
      }else if(Birthday.matches("[a-zA-Z]+")){
        System.out.println("Letters are not a numbers. Try Again");
        System.exit(0);
      }
      String transformedBirthday=transformData(Birthday);
      
      int IdValue=0;
      ResultSet rs=stmt.executeQuery("SELECT ID FROM library_users");
      while (rs.next()){IdValue=rs.getInt("ID");}
      IdValue+=1;
      
      

      stmt.executeUpdate("INSERT INTO library_users (ID,Name, LastName, Birthday)"+
      "VALUES (" +IdValue + "," + "'" + name + "'" + "," + "'" + lastName + "'" + "," + "'" + transformedBirthday + "'" + ")");      
       
    } catch(SQLException se){
      se.printStackTrace();
    } catch (Exception e){
      e.printStackTrace();
    }

  }
  String transformData(String data){
    String newString="";
    for(int i=0;i<data.length();i++){
      if(i==2 || i==4 ){
        newString=newString+"-" + data.charAt(i);
      }else {
        newString=newString+data.charAt(i);
      } 
    }
    data=newString;  
    return data;
  }
}
