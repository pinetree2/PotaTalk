package Server.dao;

import Server.dto.SubToDoDTO;

import java.sql.*;

public class SubToDoDAO {


    public static void Subtododao(int Mainindex, String SubTask, String SubDate, String Chat_index) throws SQLException {
        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        int SubNum =0;
        ResultSet rs = null;

        SubToDoDTO subToDoDTO;
        int Mainidx = Mainindex; //메인인덱스
        int Subidx = 0; //서브 인덱스
        String subTask = SubTask; //서브 태스크
//        Date subDate = null;
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일");
//        try {
//            subDate = formatter.parse(SubDate);
//        } catch (ParseException e1) {
//            e1.printStackTrace();
//        }
        String chat_index = Chat_index; //서브에 해당하는 채팅방인덱스
        // 총 서브 태스크 개수 이거는 User 통합 코드에서 계산해서 전달받아야되는디요

        //sql





        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");

            String sql0 = "SELECT S_idx,SubNum FROM chatmainsub WHERE M_idx = ?";
            stmt = con.prepareStatement(sql0);
            stmt.setInt(1,Mainidx);
            rs = stmt.executeQuery();
            while(rs.next()){
                Subidx = rs.getInt("S_idx");
                SubNum = rs.getInt("SubNum");

            }
            Subidx +=1;
            SubNum +=1;

            String sql = "INSERT INTO chatmainsub(M_idx,S_idx,Sub_Task,Sub_Deadline,Chat_index,SubNum,S_check) VALUE(?,?,?,?,?,?,?)";
            subToDoDTO = new SubToDoDTO();

            stmt = con.prepareStatement(sql);
            stmt.setInt(1,Mainidx);
            stmt.setInt(2,Subidx);
            stmt.setString(3,subTask);
            stmt.setString(4,SubDate);
            stmt.setString(5,chat_index);
            stmt.setInt(6,SubNum);
            stmt.setBoolean(7, false);
            stmt.executeUpdate();

            subToDoDTO.setMainidx(Mainidx);
            subToDoDTO.setSubidx(Subidx);
            subToDoDTO.setSubTask(subTask);
            subToDoDTO.setChat_index(chat_index);
            subToDoDTO.setSubNum(SubNum);

        }
        catch (ClassNotFoundException|SQLException e) {
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