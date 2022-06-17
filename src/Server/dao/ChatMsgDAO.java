package Server.dao;

import Server.dto.ChatMsgDTO;

import java.sql.*;

public class ChatMsgDAO {


    public static void chatmsg(String RoomName, String nickname, String Msg) throws SQLException {
        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt = null;
        // Statement statement = null;
        ResultSet rs = null;

        ChatMsgDTO chatMsgDTO;
        chatMsgDTO = new ChatMsgDTO();
        String roomname = RoomName;
        String Nickname = nickname;
        String msg = Msg;
//        LocalTime msgtime = MsgTime;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");
            String sql = "INSERT INTO chatmsg(chat_index,userNick,msg) VALUES(?,?,?)";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomname);
            stmt.setString(2, Nickname);
            stmt.setString(3, msg);
            stmt.executeUpdate();
//            pstmt.setTime(4, Time.valueOf(msgtime)); //mysql 의 시간 형식지정자

        } catch (ClassNotFoundException e1) {

            System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");

        } finally {

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


    }
}
