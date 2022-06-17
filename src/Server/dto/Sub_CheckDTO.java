package Server.dto;

public class Sub_CheckDTO {

    int Subindex;
    int Mainindex;
    String Chatindex;
    Boolean Check;

    public int getSubindex() {
        return Subindex;
    }

    public void setSubindex(int subindex) {
        Subindex = subindex;
    }

    public int getMainindex() {
        return Mainindex;
    }

    public void setMainindex(int mainindex) {
        Mainindex = mainindex;
    }

    public String getChatindex() {
        return Chatindex;
    }

    public void setChatindex(String chatindex) {
        Chatindex = chatindex;
    }

    public Boolean getCheck() {
        return Check;
    }

    public void setCheck(Boolean check) {
        Check = check;
    }
}
