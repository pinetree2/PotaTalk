package Todo;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class ToDoListBringSubDAO extends ArrayList<ListDataSub> {

    public static ArrayList<ListDataSub> ToDoListBringDAO(String chat_index){
        Connection con = null;
        ArrayList<ListDataSub> datasublist = new ArrayList<ListDataSub>();
        //String chat_index = 1;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "chat"; //  접속자 id
        String password = "chat"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;

        int M_idx =0;
        int S_idx =0;
        String S_Task = null;
        Date S_Deadline =null;
        int S_Check =0;




        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");

            String sql = "SELECT * FROM messenger.chatmainsub WHERE Chat_Index = '"+chat_index+"'  and S_idx >= 1";
            //채팅방번호 조건도 추가되어야함
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()) {
                M_idx =rs.getInt("M_idx");
                S_idx = rs.getInt("S_idx");
                S_Task = rs.getString("Sub_Task");
                S_Deadline = rs.getDate("Sub_Deadline") ;
                S_Check = rs.getInt("S_check");
                chat_index =rs.getString("Chat_Index");

                ListDataSub temp = new ListDataSub(M_idx,S_idx, S_Task,S_Deadline,S_Check,chat_index);
                datasublist.add(temp);
            }

            ToDoListBringSubDTO.setMS_idx(M_idx);
            ToDoListBringSubDTO.setS_idx(S_idx);
            ToDoListBringSubDTO.setS_Task(S_Task);
            ToDoListBringSubDTO.setS_Deadline(S_Deadline);
            ToDoListBringSubDTO.setS_Check(S_Check);
            ToDoListBringSubDTO.setChat_index(chat_index);



        } catch (ClassNotFoundException|SQLException e) {

            System.out.println("[SQL Error : " + e.getMessage() + "]");

        }  finally {

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
            for (ListDataSub s : datasublist)
                System.out.println(s);
            return (datasublist);
        }



    }

}
