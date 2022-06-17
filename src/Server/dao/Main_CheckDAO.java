package Server.dao;

import Server.dto.Main_CheckDTO;

import java.sql.*;

public class Main_CheckDAO {


    public static void Main_Check(int idx, String Chat_index, String check){
        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;


        Main_CheckDTO main_checkDTO;
        main_checkDTO = new Main_CheckDTO();

        int Mainindex =idx;
        String Chatindex = Chat_index;
        Boolean Check;
        Check = Boolean.parseBoolean(check);

        //채팅방 인덱스하고 메인인덱스



        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");

            String sql = "UPDATE chatmainsub SET M_check =? WHERE M_idx  =? AND Chat_Index =?";
            stmt = con.prepareStatement(sql);
            stmt.setBoolean(1,Check);
            stmt.setInt(2, Mainindex);
            stmt.setString(3,Chatindex);
            stmt.executeUpdate();

            Main_CheckDTO.setMainindex(Mainindex);
            Main_CheckDTO.setChatindex(Chatindex);
            Main_CheckDTO.setCheck(Check);


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
