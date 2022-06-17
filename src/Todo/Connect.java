package Todo;
import java.sql.*;
public class Connect {
    public static Connection makeConnection(){
        String url = "jdbc:mysql://localhost:3306/project_table";
        String id = "chat";
        String password = "chat";
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, id, password);
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }
}