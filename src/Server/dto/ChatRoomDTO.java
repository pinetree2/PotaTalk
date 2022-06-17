package Server.dto;

public class ChatRoomDTO  {
    public int Num_Members;
    private String Roomname;

    public ChatRoomDTO(){
    }
    public ChatRoomDTO(String Roomname){
        this.Roomname = Roomname;
    }

    public int getNum_Members() {
        return Num_Members;
    }

    public void setNum_Members(int Num_Memebers) {
        this.Num_Members = Num_Memebers;
    }

    public String getRoomname() {
        return Roomname;
    }

    public void setRoomname(String roomname) {
        Roomname = roomname;
    }
}
