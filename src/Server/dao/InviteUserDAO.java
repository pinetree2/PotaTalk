package Server.dao;

import Server.dto.InviteUserDTO;

import java.sql.*;

public class InviteUserDAO {




    public static void InviteUser(String user_nick,String Roomname){
        InviteUserDTO inviteUserDTO;
        inviteUserDTO = new InviteUserDTO();

        Connection con = null;
        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;
        String User_id = null;

        int Memnum =0; //채팅방인원수
        int chatindex =0;
        String RoomName;



        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");

            //chatroom 에서 Roomname에 해당하는 채팅방인원수 가져옴 select로
            String sql0 = "SELECT Num_Members FROM chatroom WHERE Room_Name =?";


            stmt = con.prepareStatement(sql0);
            stmt.setString(1,Roomname);
            rs = stmt.executeQuery();
            while(rs.next()){
                Memnum = rs.getInt("Num_Members");

            }
            Memnum +=1;

            //그다음에 update로 +1 업데이트
            String sql1 = "UPDATE  chatroom SET Num_Members = ? WHERE Room_Name = ?";
            stmt = con.prepareStatement(sql1);
            stmt.setInt(1,Memnum);
            stmt.setString(2,Roomname);
            stmt.executeUpdate();


            //채팅방 인덱스도 받아옴
            String sql2 = "SELECT chat_index FROM chatroom WHERE Room_Name = ?";
            stmt = con.prepareStatement(sql2);
            stmt.setString(1,Roomname);
            ResultSet rs2 = stmt.executeQuery();
            while(rs2.next()){
                chatindex = rs2.getInt("chat_index");
                InviteUserDTO.setChat_index(chatindex);
            }

            //유저아이디 받아옴
            String sqlid = "SELECT user_id FROM user_table WHERE user_nick =?";
            stmt = con.prepareStatement(sqlid);
            stmt.setString(1,user_nick);
            ResultSet rsid = stmt.executeQuery();
            while(rsid.next()){
                User_id = rsid.getString("user_id");
                InviteUserDTO.setUser_id(User_id);
            }



            //초대받은사람 id, 채팅방 인덱스를 받아오면 그 사람의 유저별채팅방에 채팅방 추가해주고
            String sql3 = "INSERT INTO userchatroominfo(Chat_index,ChatUser_id,ChatRoomName,User_nick) VALUE(?,?,?,?)";
            stmt = con.prepareStatement(sql3);
            stmt.setInt(1,chatindex);
            stmt.setString(2,User_id);
            stmt.setString(3,Roomname);
            stmt.setString(4,user_nick);
            stmt.executeUpdate();


            //채팅방별 유저에도 추가해줘야함
//            String sql4 ="INSERT INTO chatroomuserinfo(chat_index,user_id,chat_name) VALUE(?,?,?)";
//            stmt = con.prepareStatement(sql4);
//            stmt.setInt(1,chatindex);
//            stmt.setString(2,User_id);
//            stmt.setString(3,Roomname);
//            stmt.executeUpdate();
            InviteUserDTO.setUser_id(User_id);
            InviteUserDTO.setChat_index(chatindex);
            InviteUserDTO.setRoomName(Roomname);
            InviteUserDTO.setMemnum(Memnum);


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
