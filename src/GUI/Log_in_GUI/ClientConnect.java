package GUI.Log_in_GUI;
import GUI.Main_Window_GUI.FriendList_and_RoomListView;
import GUI.Main_chatting.*;
import Server.Room;

import java.io.*;
import java.net.*;
import java.rmi.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

public class ClientConnect implements Runnable {
    MainChatView mainChatView;

    private static int PORT = 7777;
    private static String IP = "127.0.0.1";
    private Socket socket;

    private FriendList_and_RoomListView friendList_and_roomListView;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private boolean ready = false;
    public Vector<Room> myRoom;

    public ClientConnect() {
        System.out.println("Client start...");
        friendList_and_roomListView = new FriendList_and_RoomListView(this);
        Thread thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] args) {
        System.out.println("Client start...");
        new ClientConnect();
    }


    public void run() {
        while (!ready) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        myRoom = new Vector<>();
        while (true) {
            try {
                String rMsg = dis.readUTF();
                if (rMsg == null) return;
                if (rMsg.trim().length() > 0) {
                    System.out.println("from Server : " + rMsg);
                }
                dataParsing(rMsg);
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    dis.close();
                    dos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public boolean Access() {
        if (!ready) {
            socket = null;
            try {
                socket = new Socket("127.0.0.1", 7777);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                System.out.println("UnKnownHost");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (socket.isBound()) {
                try {
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();

                }
                ready = true;
            }
        }
        return ready;
    }

    public synchronized void dataParsing(String data) {
        StringTokenizer token = new StringTokenizer(data, "\\|");
        String protocol = token.nextToken();

        switch (protocol) {
            case "100", "400": //로그인한 사람이 있음
                friendList_and_roomListView.updatefriendDisplayed();
                break;
            case "300": //다른 클라가 보낸 메세지 받는 (300|채팅방 이름|보낸 사람 닉네임|메세지 내용)
                echoMsgToRoom(token.nextToken(), "[" + token.nextToken() + "] " + token.nextToken());
                break;
            case "350": //채팅방에 초대됨
                friendList_and_roomListView.updateroomDisplayed(); //초대된 사람이 받는 코드
                break;
            case "900":
                echoMsgToRoom(token.nextToken(), "==================[" + token.nextToken() + "]님이 초대되었습니다=================="); //초대 한 사람이 받는 코드
                break;
            case "950":
                echoMsgToRoom(token.nextToken(), token.nextToken());
        }
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public DataInputStream getDIs() {
        return dis;
    }

    /* private void echoMsgToRoom(String title, String msg) { // [사람]내용
         for (int i = 0; i < myRoom.size(); i++) {
             if ( myRoom.get(i).title.equals(title) ) {

                 // 사용자 -> 방배열 -> 유아이 -> 텍스트에어리어
                 // 커서 위치 조정
                 myRoom.get(i).rUI.chattingArea
                         .setCaretPosition(myRoom.get(i).rUI.chattingArea.getText().length());
                 // 에코
                 System.out.println(myRoom.get(i).title + ": " + msg);
                 //mainChatView.chattingArea.append(msg+"\n");
                 myRoom.get(i).rUI.chattingArea.append(msg + "\n");
             }
         }

     }*/
    private void echoMsgToRoom(String TITLE, String msg) { //dataParsing에서 호출됨
        // 사용자 -> 방배열 -> ui -> TextArea
        for (int i = 0; i < myRoom.size(); i++) {
            if (myRoom.get(i).title.equals(TITLE)) {// start_from_login cmd에서 에코
                System.out.println(myRoom.get(i).title + ") 켜지는거 맞니" + msg); //msg=[user1] 뭐가 살쪄

                //JTextArea에 append하는 부분
                System.out.print("APPENDTEST => " + msg);
                //myRoom.get(i).rUI.appendTest(msg + "\n");
                myRoom.get(i).rUI.chattingArea.append(msg + "\n");
                //myRoom.get(i).rUI.appendTest("AAAAAA");

                //setCaretPosition : 맨 아래로 스크롤
                myRoom.get(i).rUI.chattingArea
                        .setCaretPosition(myRoom.get(i).rUI.chattingArea.getText().length());




            }
        }
    }
}