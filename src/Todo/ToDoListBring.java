package Todo;
import java.sql.*;
import java.util.*;

public class ToDoListBring {


    public static ArrayList<ListDataMain> bringMain(String chat_index) {
//        Connection con = Connect.makeConnection();
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        ArrayList<ListDataMain> datamainlist = new ArrayList<ListDataMain>();
//        //채팅방 인덱스 가져와야댐, 일단 1로 하고 테스트
//        int chat_index = 1;
//        try {
//            String printString = "SELECT * FROM project_table.chatmainsub WHERE Chat_Index ="+chat_index+" and M_idx >= 1 and M_Task is not null";
//            //채팅방번호 조건도 추가되어야함
//            pstmt = con.prepareStatement(printString);
//            rs = pstmt.executeQuery();
//            while(rs.next()) {
//                ListDataMain temp = new ListDataMain(rs.getInt("M_idx"),rs.getString("M_Task"),
//                        rs.getDate("Deadline"), rs.getInt("M_check"),
//                        rs.getInt("SubNum"), rs.getInt("Chat_Index"));
//                datamainlist.add(temp);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        ArrayList<ListDataMain> datamainlist;
        datamainlist =ToDoListBringDAO.ToDoListBringDAO(chat_index);
        Collections.sort(datamainlist, new ListData.SortByDate()); //날짜순으로 정렬
        return datamainlist;
    }
    public static ArrayList<ListDataSub> bringSub(String chat_index) {
//        Connection con = Connect.makeConnection();
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        ArrayList<ListDataSub> datasublist = new ArrayList<ListDataSub>();
//        //채팅방 인덱스 가져와야댐, 일단 1로 하고 테스트
//        int chat_index = 1;
//        try {
//            //String printString = "SELECT * FROM project_table WHERE M_idx >= 1 and M_Task is null";
//            String printString = "SELECT * FROM project_table.chatmainsub WHERE Chat_Index = "+chat_index+"  and S_idx >= 1";
//            pstmt = con.prepareStatement(printString);
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                ListDataSub temp = new ListDataSub(rs.getInt("M_idx"), rs.getInt("S_idx"),
//                        rs.getString("S_Task"), rs.getDate("S_Deadline"),
//                        rs.getInt("S_check"), rs.getInt("Chat_Index"));
//                datasublist.add(temp);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        ArrayList<ListDataSub> datasublist;
        datasublist =ToDoListBringSubDAO.ToDoListBringDAO(chat_index);
        Collections.sort(datasublist, new ListData.SortByDate());
        return datasublist;
    }
}
