package Server.dto;

public class Main_CheckDTO {

    static int Mainindex;
    static String Chatindex;
    static Boolean Check;

    public int getMainindex() {
        return Mainindex;
    }

    public static void setMainindex(int mainindex) {
        Mainindex = mainindex;
    }

    public String getChatindex() {
        return Chatindex;
    }

    public static void setChatindex(String chatindex) {
        Chatindex = chatindex;
    }

    public Boolean getCheck() {
        return Check;
    }

    public static void setCheck(Boolean check) {
        Check = check;
    }
}
