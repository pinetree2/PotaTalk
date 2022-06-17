package Server.dao;

import Server.dto.Sub_CheckDTO;

import java.sql.*;

public class Sub_CheckDAO {


    public static void Sub_Check(int idx,String Chat_index, int Main_index,String check) {

        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;


        Sub_CheckDTO sub_checkDTO;
        sub_checkDTO = new Sub_CheckDTO();

        int Subindex = idx;
        int Mainindex = Main_index;
        String Chatindex = Chat_index;
        Boolean Check;
        Check = Boolean.parseBoolean(check);

        String sql = "UPDATE chatmainsub SET S_check =? WHERE M_idx  =? AND Chat_Index =? AND S_idx =?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");
            stmt = con.prepareStatement(sql);
            stmt.setBoolean(1,Check);
            stmt.setInt(2,Mainindex);
            stmt.setString(3,Chatindex);
            stmt.setInt(4,Subindex);
            stmt.executeUpdate();

            sub_checkDTO.setSubindex(idx);
            sub_checkDTO.setMainindex(Mainindex);

        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        } finally {
            // DB close 필수!
            // 접속이 된 것
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
}
