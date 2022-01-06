package books;
import java.sql.*;


public class ShowBooks implements Interfaces.InterfaceConn {
    public void Connection(){
        try{
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "12345");
            Statement st=conn.createStatement();
            String querend="select * from books";
            ResultSet rs=st.executeQuery(querend);
            while(rs.next()){
                String title=rs.getString("Title");
                int amount=rs.getInt("Amount");
                System.out.println(title + " ,sztuk    :    " + amount);
            }
            st.close();
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
}
