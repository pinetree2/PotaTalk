package Server.dao;

import Server.dto.DeleteTodoDTO;

import java.sql.*;

public class DeleteTodoDAO {

    public static void DeleteTodoDAO(int M_idx, int S_idx) throws ClassNotFoundException, SQLException {

        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;


        DeleteTodoDTO DeleteTodoDTO = new DeleteTodoDTO();
        int m_idx = M_idx;
        int s_idx = S_idx;

        if(S_idx == 0){
            //메인인덱스에 해당하는거 다 지움
            // delete


            try{

                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
                System.out.println("Connect Success!");

                String sql ="DELETE FROM chatmainsub WHERE M_idx = ?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1,m_idx);
                stmt.executeUpdate();

                DeleteTodoDTO.setM_idx(m_idx);
                DeleteTodoDTO.setS_idx(s_idx);

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

        }else{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");
            //메인, 서브 인덱스에 둘 다 해당하는 task 지움
            String sql = "DELETE FROM chatmainsub WHERE M_idx = ? AND S_idx =?";
            try{
                stmt = con.prepareStatement(sql);
                stmt.setInt(1,m_idx);
                stmt.setInt(2,s_idx);
                stmt.executeUpdate();
                DeleteTodoDTO.setM_idx(m_idx);
                DeleteTodoDTO.setS_idx(s_idx);

            } catch (SQLException e) {
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
}
