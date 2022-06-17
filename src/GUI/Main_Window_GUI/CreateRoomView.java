package GUI.Main_Window_GUI;

import GUI.Log_in_GUI.ClientConnect;
import Server.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateRoomView {
    FriendList_and_RoomListView friendList_and_roomListView;
    ChatController chatController;
    Connection con = null;
    Statement st=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    private JPanel CreateRoomView;
    private JTextField inputRoomName;
    private JButton btnCreate;
    private JLabel RoomNameInput;
    JFrame frame = new JFrame("방만들기");

    ClientConnect clientConnect;
    Room room;

    public CreateRoomView(ClientConnect clientConnect) {
        this.clientConnect=clientConnect;
        frame.setSize(280, 120);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(CreateRoomView);
        frame.setResizable(false);
        frame.setVisible(true);

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//              frame.dispose();
                String new_room=inputRoomName.getText();
                if(new_room.length()==0){
                    JOptionPane.showMessageDialog(null, "채팅방 이름을 입력해주세요.");
                }
                else{
                    chatController = new ChatController();
                    chatController.room_filter(new_room, frame, clientConnect);
                }
            }
        });
    }



}