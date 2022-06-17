package Server.dto;

public class InviteUserDTO {
    static String User_id;
    static String RoomName;
    static int Chat_index;
    static int Memnum;

    public String getRoomName() {
        return RoomName;
    }

    public static void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public String getUser_id() {
        return User_id;
    }

    public static void setUser_id(String user_id) {
        User_id = user_id;
    }

    public int getChat_index() {
        return Chat_index;
    }

    public static void setChat_index(int chat_index) {
        Chat_index = chat_index;
    }

    public int getMemnum() {
        return Memnum;
    }

    public static void setMemnum(int memnum) {
        Memnum = memnum;
    }
}
