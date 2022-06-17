package GUI.Log_in_GUI;

import GUI.Main_Window_GUI.FriendList_and_RoomListView;
import GUI.Main_chatting.MainChatView;
import Server.dao.LogInDAO;

import javax.swing.*;
import java.sql.*;

//로그인 화면에서 로그인 성공 시 --> 메인화면 띄우기
public class LoginController {

    JPanel friendList_and_roomListView;
    public LoginView loginView;
    // db연결
    Connection con = null;
    Statement st = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    FriendList_and_RoomListView mainFrame;
    MainChatView chatFrm;
    LogInDAO logInDAO;
    private String myid;

    public LoginController() {
        String className = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/Messenger";
        String user = "chat"; //  접속자 id
        String passwd = "chat"; // 접속자 pw

        try {
            Class.forName(className);
            con = DriverManager.getConnection(url, user, passwd);
            System.out.println("Connect Success!");
        } catch (Exception e) {
            System.out.println("Connect Failed!");
            e.printStackTrace();
        }
    }

    public void db_close() {
        try {
            if (rs != null) rs.close();
            else if (st != null) st.close();
            else if (ps != null) ps.close();
            else if (con != null) {
                con.close(); //conn = .getConnection();
            }
        } catch (Exception e) {
            System.out.println("dbclose Failed!");
        }
    }

    public int login(String user_id, String user_pw, String user_nick) {
        this.myid=user_id;
        String SQL = "SELECT user_pw, user_nick, status FROM user_table WHERE user_id = ?";
        try {
            ps = con.prepareStatement(SQL);
            ps.setString(1, user_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString(1).contentEquals(user_pw)) {
                    if(rs.getBoolean("status")){
                        return 2;
                    }
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db_close();
        }
        return 0;
    }


}