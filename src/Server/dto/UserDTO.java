package Server.dto;

import java.util.Vector;

public class UserDTO extends Vector<UserDTO> {
    //테이블의 컬럼을 private으로 선언 후 getter/setter 메소드 세팅
    private int index;
    private String user_id;
    private String user_pw;
    private String user_nick;
    private int status;

    //생성자
    public UserDTO(){

    }

    //생성자2
    public UserDTO(String user_id, String user_pw){


        this.user_id = user_id;
        this.user_pw = user_pw;

    }

    public String getUser_id(){
        return user_id;
    }

    public String getUser_pw(){
        return user_pw;
    }

    public void setUser_pw(String user_pw){
        this.user_pw = user_pw;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

//