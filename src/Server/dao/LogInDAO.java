package Server.dao;

import Server.dto.LogInDTO;

import java.sql.*;

import static java.lang.Boolean.TRUE;

public class LogInDAO{

    private static String Usernick;

    public static String logindao(String UserId){

        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");

            String sql = "UPDATE user_table SET status = ? WHERE user_id = ? ";
            stmt = con.prepareStatement(sql);

            stmt.setBoolean(1,TRUE);
            stmt.setString(2,UserId);
            stmt.executeUpdate();

//            System.out.println("변경된 result"+ result);
            LogInDTO.setUser_id(UserId);


        } catch (ClassNotFoundException|SQLException e) {

            System.out.println("[SQL Error : " + e.getMessage() + "]");

        }  finally {

            //사용순서와 반대로 close 함
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        try {
            String sql2 = "SELECT user_nick FROM user_table WHERE user_id = ?";
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");
            stmt = con.prepareStatement(sql2);
            stmt.setString(1,UserId);

            rs = stmt.executeQuery();
            while(rs.next()){
                Usernick = rs.getString("user_nick");
                LogInDTO.setUser_nick(Usernick);
            }

            System.out.println("유저이름출력@@@@@@@@"+ Usernick);
            stmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("JDBC 드라이버를 로드하는데에 문제 발생" + e.getMessage());
            e.printStackTrace();
        }

        return LogInDTO.getUser_nick();
    }


}