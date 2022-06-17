package Server;

import java.io.*;
import java.net.*;
import java.util.*;
//import Server.User;
//import Server.Room;
public class TCPServer implements Runnable{
    public static final int PORT = 7777;
    Vector<User> Userlist;
    Vector<User> MainUser;
    Vector<Room> Chatroom;

    public TCPServer() {
        Userlist = new Vector<>();
        MainUser = new Vector<>();
        Chatroom = new Vector<>();
        new Thread(this).start();
    }
    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(PORT);
            System.out.println("Start Server......");
            while(true) {
                Socket s = socket.accept();
                User c = new User(s, this);
            }
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("[Server] 서버 소켓 오류 > " + e.toString());
        }
    }
    public static void main(String[] args) {
        new TCPServer();
    }
}