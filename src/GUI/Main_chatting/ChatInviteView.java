package GUI.Main_chatting;

import GUI.Log_in_GUI.ClientConnect;
import GUI.Log_in_GUI.LoginView;
import GUI.Main_Window_GUI.ChatController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

public class ChatInviteView {
    Connection con = null;
    Statement st=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    private JButton invitebtn;
    private JList invitelist;
    private JPanel ChatInviteView;
    private ChatController chatController;
    private DefaultListModel model;
    LoginView loginView;
    private String id=loginView.id;
    private ClientConnect client;
    private String room_name;

    public ChatInviteView(ClientConnect client, String room_name) {


        this.room_name=room_name;
        this.client=client;
        createlist();
        ChatController chatcontroller= new ChatController();
        chatcontroller.showInvitelist(model);

        JFrame frame = new JFrame("메인 채팅");
        frame.setContentPane(ChatInviteView);
        frame.pack();
        frame.setLocation(900, 280);
        frame.setResizable(false);
        frame.setVisible(true);

        invitebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String invite_name= String.valueOf(invitelist.getSelectedValuesList());
                invite_name=invite_name.replace("[", "");
                invite_name=invite_name.replace("]", "");
                if(chatcontroller.friend_filter(room_name, invite_name)){
                    try {
                        client.getDos().writeUTF("350|" + invite_name + "|" + room_name);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });}

    public void createlist(){
        model = new DefaultListModel();
        invitelist.setModel(model);
        invitelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }
}

