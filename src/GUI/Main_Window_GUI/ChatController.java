package GUI.Main_Window_GUI;

import GUI.Log_in_GUI.ClientConnect;
import GUI.Log_in_GUI.LoginView;
import GUI.Main_chatting.MainChatView;
import Server.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.*;

public class ChatController { //채팅방 목록 추가 및 조회

    Connection con = null;
    Statement st=null;
    PreparedStatement ps=null;
    ResultSet rs=null;

    FriendList_and_RoomListView mainFrm ;
    LoginView loginView;
    private String id=loginView.id;
    private ClientConnect clientConnect;
    Room room;


    public ChatController() {
        String className = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/Messenger";
        String user = "chat"; //  접속자 id
        String passwd = "chat"; // 접속자 pw

        try {
            Class.forName(className);
            con = DriverManager.getConnection(url, user, passwd);
            System.out.println("Connect Success!");
        } catch (Exception e) {
            System.out.println("Connect Failed!");
            e.printStackTrace();
        }
    }
    public void db_close() {
        try {
            if (rs != null) rs.close();
            else if (st != null) st.close();
            else if (ps != null) ps.close();
            else if (con != null) {
                con.close(); //conn = .getConnection();
            }
        } catch (Exception e) {
            System.out.println("dbclose Failed!");
        }
    }

    public void showfriend(DefaultTableModel model) {  // 친구list 보여주기
        String SQL = "SELECT * FROM user_table WHERE user_id !='" + id + "';";
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                String user_nick = rs.getString("user_nick");
                boolean status = rs.getBoolean("status");
                String sta;
                if(status){
                    sta="ON";
                } else sta="OFF";

                Object data[] = {user_nick, sta};
                model.addRow(data);
            }
        } catch (Exception e) {
            System.out.println("select 실행 오류 : " + e);
            e.printStackTrace();
        } finally {
            db_close();
        }
    }

    public void showchatroom(DefaultTableModel model) {  // 방list 보여주기
        String SQL = "SELECT * FROM userchatroominfo WHERE ChatUser_id ='" + id + "';";
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                String Chatroom = rs.getString("ChatRoomName");
                Object data[] = {Chatroom};
                model.addRow(data);
            }
        } catch (Exception e) {
            System.out.println("select 실행 오류 : " + e);
            e.printStackTrace();
        } finally {
            db_close();
        }
    }

    public void showInvitelist(DefaultListModel model){  // 초대 친구list 보여주기
        String SQL = "SELECT * FROM user_table WHERE user_id !='" + id + "';";
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                String user_nick = rs.getString("user_nick");
                String data = user_nick;
                model.addElement(data);
            }
        } catch (Exception e) {
            System.out.println("select 실행 오류 : " + e);
            e.printStackTrace();
        } finally {
            db_close();
        }
    }

    public void logout(ClientConnect clientConnect){  // 로그아웃
        this.clientConnect=clientConnect;
        int answer = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "타이틀", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
        if (answer == JOptionPane.YES_OPTION) {
            msgSummit("400|" + id);
               System.exit(0);
            }
    }

    public void createroom(ClientConnect clientConnect){  // 방만들기
        CreateRoomView createRoomView = new CreateRoomView(clientConnect);
 //       createRoomView.showCreateRoomView();
    }

    public void msgSummit(String str) {
        new Thread(new Runnable() {
            public void run() {

                // 소켓생성
                if (clientConnect.Access()) {
                    try {
                        // 로그인정보(아이디+패스워드) 전송
                        clientConnect.getDos().writeUTF(str);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

    }

    public void room_filter(String new_room, JFrame frame, ClientConnect clientConnect){ // 중복되는 방 있는지 검사
        this.clientConnect = clientConnect;
        String SQL = "SELECT * FROM chatroom"; // 지금 있는 방 이름들 다 선택
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                String chatroom=rs.getString("Room_Name");
                if(chatroom.equals(new_room)) {
                    JOptionPane.showMessageDialog(null, "이미 존재하는 채팅방입니다.");
                    return;
                }
            }
            clientConnect.getDos().writeUTF("150|" + new_room);
            MainChatView mainChatView=new MainChatView(new_room, clientConnect);
            getIn(new_room, mainChatView);
            mainChatView.showmainchatview(new_room);
            frame.setVisible(false);
        } catch (Exception e) {
            System.out.println("room_filter 실행 오류 : " + e);
            e.printStackTrace();
        } finally {
            db_close();
        }
    }

    public boolean friend_filter(String room_name, String select_name){  // 방에 있는 사용자와 중복되는 인간 초대하려고 하는지 검사
        String SQL = "SELECT * FROM userchatroominfo WHERE ChatRoomName ='" + room_name + "';";
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                String get_name=rs.getString("User_nick");
                if(select_name.equals(get_name)) {
                    JOptionPane.showMessageDialog(null, "이미 채팅방에 있는 사용자입니다.");
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("room_filter 실행 오류 : " + e);
            e.printStackTrace();
        } finally {
            db_close();
        }
        return true;
    }

    public void show_prechat(String room_name, JTextArea chattingArea){
        String SQL = "SELECT * FROM chatmsg WHERE chat_index ='" + room_name + "';";
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                String chat = rs.getString("msg");
                String nick = rs.getString("userNick");
                System.out.println(chat);
                chattingArea.append("[" + nick + "] " + chat + "\n");
            }
        } catch (Exception e) {
            System.out.println("select 실행 오류 : " + e);
            e.printStackTrace();
        } finally {
            db_close();
        }
    }
    private void getIn(String Room_title, MainChatView mainchatview){
        Room room = new Room();
        room.title = Room_title;
        room.setrUI(mainchatview);
        this.room = room;
        clientConnect.myRoom.add(room);
        try{
            clientConnect.getDos().writeUTF(
                    "200|" + Room_title);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}


