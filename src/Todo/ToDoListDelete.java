package Todo;

import GUI.Log_in_GUI.ClientConnect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

import static java.lang.Thread.sleep;

public class ToDoListDelete {
    private static JFrame frame = new JFrame("ToDoListDelete");
    private JButton button1;
    private JSpinner m_Idx;
    private JSpinner s_Idx;
    private JPanel deleteToDo;

    private static int M_Idx;
    private static int S_Idx;
    private ClientConnect client;
    private String room_name;
    public void run() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public ToDoListDelete(JCheckBoxTree tree1, ClientConnect client, String room_name){
        frame.setContentPane(deleteToDo);
        frame.setLocationRelativeTo(null);
        this.client = client;
        this.room_name = room_name;
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                M_Idx = (Integer)m_Idx.getValue();
                S_Idx = (Integer)s_Idx.getValue();
                frame.dispose();
                frame.setVisible(false);

                int current_main =0;
                int current_sub =0;


                Connection con = null;
                String url = "127.0.0.1:3306"; // 서버 주소
                String user_name = "chat"; //  접속자 id
                String password = "chat"; // 접속자 pw
                PreparedStatement stmt =null;
                // Statement statement = null;
                ResultSet rs = null;

                //입력받은 메인 인덱스의 서브 인덱스 값을 찾고 그것보다 클경우에 예외처리

                try {
                    String sql0 = "SELECT S_idx FROM chatmainsub  WHERE M_idx = ?";
                    String sql1 = "SELECT M_idx FROM chatmainsub";
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://" + url + "/messenger?serverTimezone=UTC", user_name, password);
                    System.out.println("Connect Success!");

                    stmt = con.prepareStatement(sql0);
                    stmt.setInt(1,M_Idx);

                    rs = stmt.executeQuery();
                    while(rs.next()){
                        current_sub = rs.getInt("S_idx");
                    }

                    stmt = con.prepareStatement(sql1);
                    rs = stmt.executeQuery();
                    while(rs.next()){
                        current_main = rs.getInt("M_idx");
                    }


                    stmt.close();
                    con.close();
                } catch (ClassNotFoundException | SQLException ex) {
                    System.err.println("JDBC 드라이버를 로드하는데에 문제 발생" + ex.getMessage());
                    ex.printStackTrace();
                }

                if(M_Idx > current_main) {
                    JOptionPane.showMessageDialog(null, "유효하지 않은 인덱스입니다.");
                    return;
                }
                else if(S_Idx > current_sub){
                    JOptionPane.showMessageDialog(null, "유효하지 않은 인덱스입니다.");
                    return;
                }
                else if(M_Idx <0 | S_Idx <0){
                    JOptionPane.showMessageDialog(null, "유효하지 않은 인덱스입니다.");
                    return;
                }

                    //입력받은 서브 인덱스값이 현재 있는 값보다 클경우
                    //ToDoListDeleteController.deleteController(getM_Idx(),getS_Idx());
                try {
                    client.getDos().writeUTF("700" + "|" + String.valueOf(getM_Idx()) + "|" + String.valueOf(getS_Idx()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                ToDoList.refresh(tree1, room_name);

            }
        });
    }

    public int getM_Idx() { return this.M_Idx; }
    public int getS_Idx() { return this.S_Idx; }

}
