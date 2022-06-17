package Server.dto;

public class LogInDTO {

    static String User_id;
    static String User_nick;

    public static String getUser_id() {
        return User_id;
    }
    public static String getUser_nick() {
        return User_nick;
    }

    public static void setUser_id(String user_id) {
        User_id = user_id;
    }

    public static void setUser_nick(String user_nick) {
        User_nick = user_nick;
    }
}