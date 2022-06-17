package Server;

import Server.dao.ChatRoomDAO;

import Server.dao.LogInDAO;
import Server.dto.Database;
import Server.dto.LogInDTO;
import Server.dao.LogOutDAO;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;

import static Server.dao.DeleteTodoDAO.DeleteTodoDAO;
import static Server.dao.MainToDoDAO.Maintododao;
import static Server.dao.SubToDoDAO.Subtododao;
import static Server.dao.Main_CheckDAO.Main_Check;
import static Server.dao.Sub_CheckDAO.Sub_Check;
import static Server.dao.InviteUserDAO.InviteUser;
import static Server.dao.ChatMsgDAO.chatmsg;
public class User extends Thread {

    //Room myRoom; //채팅방
    Vector<User> Userlist; //서버에 접속해 있는 모든 user
    Vector<User> MainUser; //메인화면에 있는 user
    Vector<Room> Chatroom; //전체 서버에서 생성된 Room
    Vector<Room> myRoom; //사용자가 접속해있는 Room


    InputStream is;
    DataInputStream dis;
    OutputStream os;
    DataOutputStream dos;

    Database db = new Database();

    Socket s;
    String id;
    String nickname;

    public User(Socket s, TCPServer server){

        Userlist = server.Userlist;
        MainUser = server.MainUser;
        Chatroom = server.Chatroom;
        myRoom = new Vector<>();
        this.s = s;
        try {
            os = s.getOutputStream();
            dos = new DataOutputStream(os);
            is = s.getInputStream();
            dis = new DataInputStream(is);
            start();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        try {
            while (true) {
                String msg = dis.readUTF();
                if (msg == null) return;
                if(msg.trim().length() > 0){
                    System.out.println("from Client: "+ msg +":"+ s.getInetAddress().getHostAddress());
                }
                String[] msgs = msg.split("\\|", 10);
                String protocol = msgs[0];

                switch (protocol) {
                    case "100": //user main에 입장(로그인) (
                        Userlist.add(this);
                        MainUser.add(this);
                        id = msgs[1]; //("100|id")
                        LogInDAO.logindao(id); //status 변경
                        nickname = LogInDTO.getUser_nick();
                        for (int i = 0; i < Userlist.size(); i++) { //현재 접속중인 유저 모두에게 홈화면 친구 리스트 새로고침하라고 눈치주는 코드
                            Userlist.get(i).messageTo("100|");
                        }
                        System.out.println(this.nickname + "님이 로그인하셨습니다.\n");
                        break;

                    case "150": //새로운 방 만들기 -> db에 추가하는 과정 추가 필요
                        Room r = new Room();
                        r.title = msgs[1];
                        r.count = 1;
                        //Chatroom.add(r);
                        //r.u.add(this);
                        //myRoom.add(r);

                        ChatRoomDAO.chatroomdao(r, this.nickname);
                        List_update(this.nickname);
                        //System.out.println("이건 잘 나옴 아마도: " + this.nickname);
                        System.out.println("[" + r.title + "] 채팅방이 새로 생성되었습니다.");
                        break;
                    case "200": //채팅방 입장하기
                        int ch = 0;
                        for (int i = 0; i < Chatroom.size(); i++) {
                            Room room = Chatroom.get(i);
                            if(room.title.equals(msgs[1])) {
                                myRoom.add(room);
                                room.count++;
                                room.u.add(this);
                                ch++;
                                System.out.println(this.id + "님이 [" + room.title + "]채팅방에 입장하셨습니다.");
                                break;
                            }

                        }
                        if(ch == 0) { //현재 접속해 있는 인원 중 아무도 들어가 있지 않은 방일 경우
                            Room room = new Room();
                            room.title = msgs[1];
                            room.count = 1;
                            Chatroom.add(room);
                            room.u.add(this);
                            myRoom.add(room);
                            System.out.println(this.id + "님이 [" + room.title + "]채팅방에 입장하셨습니다.");
                        }
                        break;

                    case "250": ///채팅방 나가기 ("250|나가고자 하는 db 채팅방 이름")
                        for(int i = 0; i < Chatroom.size(); i++) { //채팅방의 유저리스트에서 사용자 삭제하기
                            Room room = Chatroom.get(i);
                            if(room.title.equals(msgs[1])) {
                                room.u.remove(this);
                                room.count--;
                                if(room.count==0) {
                                    Chatroom.remove(room);
                                }
                                System.out.println(this.id + "님이 [" + room.title + "]채팅방에서 나가셨습니다.");
                                break;
                            }

                        }
                        for(int i = 0; i < myRoom.size(); i++) { //사용자가 접속한 방 리스트에서 방을 제거
                            Room room = myRoom.get(i);
                            if(room.title.equals(msgs[1])) {
                                myRoom.remove(i);
                                break;
                            }
                        }
                        break;

                    case "300": //채팅("300|채팅방 이름|메세지 내용") //유저로부터 메세지 받아서 채팅방 내의 모든 접속자한테 뿌리기, DB 저장
                        String chat = "300|" + msgs[1] + "|" + this.nickname + "|" + msgs[2];
                        for(int i = 0; i < myRoom.size(); i++) {
                            if(myRoom.get(i).title.equals(msgs[1])) {
                                RoomMsg(myRoom.get(i), chat);
                                chatmsg(msgs[1], this.nickname, msgs[2]); //db에 채팅 내용 저장하는 메소드
                                break;
                            }
                        }//뿌릴 때 "300|채팅방 이름|보낸 사람 닉네임|메세지 내용"으로 보내줌. 클라이언트쪽에서 처리하는 코드 필요함.

                        break;
                    case "350": //초대 ("350|user_nick|chat_title")
                        //채팅방 내에 전체 메세지 보내주기
                        InviteUser(msgs[1], msgs[2]);

                        for(int i = 0; i < Userlist.size(); i++){
                            if(Userlist.get(i).nickname.equals(msgs[1])){
                                Userlist.get(i).messageTo("350|");
                                break;
                            }
                        }
                        System.out.println("900|" + msgs[2] + "|" + msgs[1]);
                        RoomMsg(msgs[2], "900|" + msgs[2] + "|" + msgs[1]);
                        break;


                    case "400": //로그아웃 (유저 id를 받아와야할듯)
                        //status 바꾸는 함수
                        for(int i = 0; i < myRoom.size(); i++) { //현재 방 안의 접속자배열에서 삭제
                            for(int j = 0; j < myRoom.get(i).u.size(); j++) {
                                if(this.id.equals(myRoom.get(i).u.get(j).id)) {
                                    myRoom.get(i).u.remove(j);
                                }
                            }
                        }
                        Userlist.remove(this);
                        MainUser.remove(this);
                        //("100|id")
                        LogOutDAO.logoutdao(msgs[1]); //로그아웃 상태변경
                        try {
                            this.dis.close();
                            this.dos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < Userlist.size(); i++) { //현재 접속중인 유저 모두에게 홈화면 친구 리스트 새로고침하라고 눈치주는 코드
                            Userlist.get(i).messageTo("400|");
                        }
                        System.out.println(this.nickname + "님이 로그아웃하셨습니다.\n");
                        break;

                    case "450": //메인 투두리스트 추가 ("450|인덱스태스크|기한날짜|채팅방인덱스")
                        Maintododao(Integer.parseInt(msgs[1]), msgs[2], msgs[3], msgs[4]);
                        RoomMsg(msgs[4], "950|" + msgs[4] + "|==================메인 투두리스트 [" + msgs[1] + "]가 생성되었습니다.=================="); //메인 생성 됐다고 알림주는 것.
                        break;

                    case "500" ://서브 투두리스트 추가 (500|메인인덱스|태스크|기한날짜|채팅방인덱스)
                        Subtododao(Integer.parseInt(msgs[1]), msgs[2], msgs[3], msgs[4]);
                        break;

                    case "550": // 매안태스크 완료 ("550|메인인덱스|채팅방인덱스|check")
                        Main_Check(Integer.parseInt(msgs[1]), msgs[2], msgs[3]);

                        break;
                    case "600": //서브테스크 완료 ("600|서브인덱스|채팅방인덱스|메인인덱스|check")
                        Sub_Check(Integer.parseInt(msgs[1]), msgs[2], Integer.parseInt(msgs[3]), msgs[4]);
                        break;
                    case "700": //메인/서브 삭제 ("700|메인인덱스|서브인덱스")
                        DeleteTodoDAO(Integer.parseInt(msgs[1]),Integer.parseInt(msgs[2]));
                        break;
                    //서버에서 1000은 따로 받지는 않지만 메세지에서 서버 알림 같은 경우는 1000으로 보낼 예정이므로 클라에서는 1000을 받아서 메세지 받아주는 코드 필요함.
                }
            }
        }catch(IOException e) {
            System.out.println("User class error");
            e.printStackTrace();
            try {
                dis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }//run
    public void messageTo(String msg) throws IOException{
        dos.writeUTF((msg));
    }
    public void RoomMsg(String Room_title, String msg) { //이 채팅방 이름을 가진 룸을 찾아서 그 룸에 있는 모든 접속자 스레드에 메세지를 보내주는 코드
        for(int i = 0; i < myRoom.size(); i++) {
            if(myRoom.get(i).title.equals(Room_title)) {
                RoomMsg(myRoom.get(i), msg);
                break;
            }
        }

    }
    public void RoomMsg(Room room, String msg) {
        for(int i = 0; i < room.u.size(); i++) { //방에 참가한 모든 유저들에게 메세지 전송
            User user = room.u.get(i);
            try {
                user.messageTo(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void List_update(String nickname) { //사람 초대했을 때, 그 사람이 접속 중이면 메인화면 새로고침하라고 눈치주는 코드
        for(int i = 0; i < Userlist.size(); i++) {
            if(Userlist.get(i).nickname == nickname) {
                String msg = "350|" ; //이 코드 받으면 새로고침하면 됨
                try {
                    messageTo(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}