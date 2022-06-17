package GUI.Main_Window_GUI;

import GUI.Log_in_GUI.LoginController;
import GUI.Log_in_GUI.LoginView;
import GUI.Log_in_GUI.ClientConnect;
import GUI.Main_chatting.MainChatView;
import Server.Room;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class FriendList_and_RoomListView {
    public JPanel FriendList_and_RoomListView;
    private JTabbedPane tab;
    private JButton btnLogout1;
    private JButton btnCreateRoom1;
    private JButton btnCreateRoom2;
    private JButton btnLogout2;
    private JTable friend_table;
    private JPanel friendPanel;
    private JPanel roomPanel;

    private JScrollPane scrollpane;
    private JScrollPane scrollpane2;
    private JTable room_table;
    public DefaultTableModel model;
    public DefaultTableModel model2;
    private ArrayList<String> rooms = new ArrayList<>(); // 현재 들어간 방들 저장하는 리스트
    private ClientConnect clientConnect;
    private ChatController chatcontroller;
    private LoginController loginController;
    private Room room;
    private boolean isopened;
    LoginView loginView;
    private String id = loginView.id;

    public FriendList_and_RoomListView(ClientConnect client) {
        this.clientConnect = client;
        createTable();
        createTable2();
        tableCellCenter(friend_table);

        chatcontroller = new ChatController(); // chatcontroller 객체 생성 --> db 연결됨 여기서 생성하지 마까?
        chatcontroller.showfriend(model); // 친구list 띄워주기
        chatcontroller.showchatroom(model2); //방list 띄워주기

        JFrame frame = new JFrame("메인 화면");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(FriendList_and_RoomListView);
        frame.pack();
        frame.setSize(350, 550);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        msgSummit("100|" + id);


        btnLogout1.addActionListener(new ActionListener() {  //친구list의 로그아웃 버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                chatcontroller.logout(clientConnect);
            }
        });

        btnLogout2.addActionListener(new ActionListener() {  //방list의 로그아웃 버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                chatcontroller.logout(clientConnect);
            }
        });

        btnCreateRoom1.addActionListener(new ActionListener() {  //친구list의 방만들기 버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                chatcontroller.createroom(clientConnect);
            }
        });

        btnCreateRoom2.addActionListener(new ActionListener() {  //방list의 방만들기 버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                chatcontroller.createroom(clientConnect);
            }
        });
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                chatcontroller.logout(clientConnect);
            }
        });

        room_table.addMouseListener((new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  //방 클릭시 채팅방 입장 이벤트
                super.mouseClicked(e);
                int row = room_table.getSelectedRow();
                TableModel rt = room_table.getModel();
                String room_name = (String) rt.getValueAt(row, 0);
                if (e.getClickCount() == 2) { // 마우스 두번 클릭시 채팅방 입장
                    int i = 0;
                    for(; i < client.myRoom.size(); i++){
                        if(client.myRoom.get(i).title.equals(room_name)){
                            break;
                        }
                    }
                    if(i != client.myRoom.size()){
                        return;
                    }
                    MainChatView mainChatView = new MainChatView(room_name, client);
                    getIn(room_name, mainChatView);
                    mainChatView.showmainchatview(room_name);
                    chatcontroller.show_prechat(room_name, mainChatView.chattingArea);
                }
            }
        }));

    }

    public void createTable() {
        model = new DefaultTableModel(null, new String[]{"NickName", "Status"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            } // 셀 변경 못하게 막음
        };
        friend_table.setModel(model);
        friend_table.getColumn("NickName").setPreferredWidth(150);
        friend_table.getColumn("Status").setPreferredWidth(10);
        friend_table.setRowHeight(40);
    }

    public void createTable2() {
        model2 = new DefaultTableModel(null, new String[]{"ChatRoom"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            } // 셀 변경 못하게 막음
        };
        room_table.setModel(model2);
        room_table.setRowHeight(60);
        room_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    public void tableCellCenter(JTable friend_table) {
        // 테이블 내용 가운데 정렬하기
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로

        TableColumnModel tcm = friend_table.getColumnModel(); // 정렬할 테이블의 컬럼모델을 가져옴

        //전체 열에 지정
        for (int i = 0; i < tcm.getColumnCount(); i++) {
            tcm.getColumn(i).setCellRenderer(dtcr);
            // 컬럼모델에서 컬럼의 갯수만큼 컬럼을 가져와 for문을 이용하여
            // 각각의 셀렌더러를 아까 생성한 dtcr에 set해줌
        }
    }
    public void updatefriendDisplayed() {
        createTable();
        tableCellCenter(friend_table);
        model.fireTableDataChanged();
        chatcontroller.showfriend(model);

    }
    public void updateroomDisplayed(){
        createTable2();
        model2.fireTableDataChanged();
        chatcontroller.showchatroom(model2);
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
