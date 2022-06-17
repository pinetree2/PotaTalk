package Server.dao;

import Server.dto.LogOutDTO;

import java.sql.*;

import static java.lang.Boolean.FALSE;

public class LogOutDAO {


    //쿼리문으로 인덱스에 해당하는 유저 id 찾아가지고 status변경하는 코드 짜야함.




    public static void logoutdao(String UserID){

        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;


        LogOutDTO logOutDTO;
        logOutDTO = new LogOutDTO();

        //user_id 행의 열 값중 user_id와 일치하는것을 찾고, 그것과 같은 행의 status값 변경



        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");

            String sql ="UPDATE user_table SET status = ? WHERE user_id =?";
            stmt = con.prepareStatement(sql);
            stmt.setBoolean(1,FALSE);
            stmt.setString(2,UserID);
            stmt.executeUpdate();

            LogOutDTO.setUser_id(UserID);



        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        }finally {
            // DB close 필수!
            // 접속이 된 것
            try {
                if(con != null) {
                    con.close();
                }
                if(stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }

    }





}
