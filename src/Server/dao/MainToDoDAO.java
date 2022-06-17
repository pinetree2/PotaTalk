package Server.dao;


import Server.dto.MainToDoDTO;

import java.sql.*;
import java.util.Date;


public class MainToDoDAO {

    private static Date MainDate;
    public static void Maintododao(int idx, String Maintask, String Maindate, String chat_index) throws SQLException {

        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;
        MainToDoDTO mainToDoDTO;

        int Mainindex = idx; //메인 인덱스

        String MainTask = Maintask; //메인 태스크
        //메인 데드라인
        String Chat_index = chat_index; //메인에 해당하는 채팅방인덱스
        int MainNum;
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            MainDate = new Date(formatter.parse(Maindate).getTime());
//        } catch (ParseException e1) {
//            e1.printStackTrace();
//        }



        //채팅방 인덱스에 맞는 메인태스크 개수를 chatroom테이블에 MainNum해서추가해줘야함

        mainToDoDTO = new MainToDoDTO();
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");
            //sql
            String sql = "INSERT INTO chatmainsub(M_idx,M_Task,Deadline,Chat_index,S_idx,M_Check) VALUE(?,?,?,?,?,?)";


            stmt = con.prepareStatement(sql);
            stmt.setInt(1,Mainindex);
            stmt.setString(2,MainTask);
            //Object MainDate;
            stmt.setString(3, Maindate);
            stmt.setString(4,Chat_index);
            stmt.setInt(5,0);
            stmt.setBoolean(6, false);
            stmt.executeUpdate();


//            MainNum = Mainindex;
//            String sql2 = "UPDATE chatroom SET Main_Num ="+MainNum+"WHERE (chat_index REGEXP '^["+Chat_index+"]+$')";
//            stmt =con.prepareStatement(sql2);
//            stmt.executeUpdate();
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