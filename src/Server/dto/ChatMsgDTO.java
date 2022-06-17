package Server.dto;

public class ChatMsgDTO {
    int chatidx;
    String Msgsender ;
    String msg;

    public int getChatidx() {
        return chatidx;
    }

    public void setChatidx(int chatidx) {
        this.chatidx = chatidx;
    }

    public String getMsgsender() {
        return Msgsender;
    }

    public void setMsgsender(String msgsender) {
        Msgsender = msgsender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
