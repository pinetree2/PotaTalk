package Server;

import GUI.Main_chatting.MainChatView;

import java.util.Vector;
public class Room {
    public String title;
    public int count;
    public MainChatView rUI;

    Vector<User> u;
    public Room() {
        u = new Vector<>();
    }
    public Vector<User> getUserArray(){
        return u;
    }
    public String getRoomTitle() {
        return title;
    }
    public void setrUI(MainChatView rui){this.rUI = rui;}
}
