package Server.dao;

import Server.Room;
import Server.dto.ChatRoomDTO;

import java.sql.*;

public class ChatRoomDAO {


    public static void chatroomdao(Room myRoom, String User_nick) throws SQLException {

        Connection con = null;
        int Nummem =1;
        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;

        ChatRoomDTO chatRoomDTO;
        int chatindex =0;
        String User_id = null;

//시연전에 ALTER TABLE 테이블 이름 AUTO_INCREMENT=변경할 숫자; 로 쿼리돌려줘야..

        try {
            chatRoomDTO = new ChatRoomDTO();
            String Roomname = myRoom.title;
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");
//sql
            String sql = "INSERT INTO chatroom SET Room_Name =? , Num_Members =?";
            stmt = con.prepareStatement(sql);
            // 데이터를 넣기
            stmt.setString(1, Roomname);
            stmt.setInt(2,Nummem);
            stmt.executeUpdate();

            chatRoomDTO.setRoomname(Roomname);
            chatRoomDTO.setNum_Members(1);

//채팅방 인덱스도 받아옴
            String sql2 = "SELECT chat_index FROM chatroom WHERE Room_Name = ?";
            stmt = con.prepareStatement(sql2);
            stmt.setString(1,Roomname);
            ResultSet rs2 = stmt.executeQuery();
            while(rs2.next()){
                chatindex = rs2.getInt("chat_index");
            }

            //유저아이디 받아옴
            String sqlid = "SELECT user_id FROM user_table WHERE user_nick =?";
            stmt = con.prepareStatement(sqlid);
            stmt.setString(1,User_nick);
            ResultSet rsid = stmt.executeQuery();
            while(rsid.next()){
                User_id = rsid.getString("user_id");
            }

            //유저별 채팅방에 현재 유저값 저장
            String sql3 = "INSERT INTO userchatroominfo(Chat_index,ChatUser_id,ChatRoomName,User_nick) VALUE(?,?,?,?)";
            stmt = con.prepareStatement(sql3);
            stmt.setInt(1,chatindex);
            stmt.setString(2,User_id);
            stmt.setString(3,Roomname);
            stmt.setString(4,User_nick);
            stmt.executeUpdate();
            //채팅방별 유저에 현재 유저값 저장

//채팅방별 유저에도 추가해줘야함
//            String sql4 ="INSERT INTO chatroomuserinfo(chat_index,user_id,chat_name) VALUE(?,?,?)";
//            stmt = con.prepareStatement(sql4);
//            stmt.setInt(1,chatindex);
//            stmt.setString(2,User_id);
//            stmt.setString(3,Roomname);
//            stmt.executeUpdate();



        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        } finally {
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