package Server.dto;

import java.util.Date;

public class SubToDoDTO {
    int Mainidx; //메인인덱스
    int Subidx; //서브 인덱스
    String subTask; //서브 태스크
    Date subDate;
    String chat_index;
    int SubNum;

    public int getSubNum() {
        return SubNum;
    }

    public void setSubNum(int subNum) {
        SubNum = subNum;
    }

    public String getChat_index() {
        return chat_index;
    }

    public void setChat_index(String chat_index) {
        this.chat_index = chat_index;
    }

    public int getMainidx() {
        return Mainidx;
    }

    public void setMainidx(int mainidx) {
        Mainidx = mainidx;
    }

    public int getSubidx() {
        return Subidx;
    }

    public void setSubidx(int subidx) {
        Subidx = subidx;
    }

    public String getSubTask() {
        return subTask;
    }

    public void setSubTask(String subTask) {
        this.subTask = subTask;
    }

    public Date getSubDate() {
        return subDate;
    }

    public void setSubDate(Date subDate) {
        this.subDate = subDate;
    }
}
