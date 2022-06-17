package Server.dto;

import java.sql.*;

public class Database {

    public Connection con;

    public Database() {

        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;
        // JDBC 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");
        } catch (SQLException e) {

            System.out.println("[SQL Error : " + e.getMessage() + "]");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 접속
    }


}
