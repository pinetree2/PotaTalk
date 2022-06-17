package Todo;
import java.util.Date;

public class ToDoListBringSubDTO {

    static int MS_idx;
    static int S_idx;
    static String S_Task;
    static Date S_Deadline;
    static int S_Check;
    static String chat_index;

    public static int getMS_idx() {
        return MS_idx;
    }

    public static String getChat_index() {
        return chat_index;
    }

    public static void setChat_index(String chat_index) {
        ToDoListBringSubDTO.chat_index = chat_index;
    }

    public int getM_idx() {
        return MS_idx;
    }

    public static void setMS_idx(int m_idx) {
        MS_idx = m_idx;
    }

    public int getS_idx() {
        return S_idx;
    }

    public static void setS_idx(int s_idx) {
        S_idx = s_idx;
    }

    public String getS_Task() {
        return S_Task;
    }

    public static void setS_Task(String s_Task) {
        S_Task = s_Task;
    }

    public Date getS_Deadline() {
        return S_Deadline;
    }

    public static void setS_Deadline(Date s_Deadline) {
        S_Deadline = s_Deadline;
    }

    public int getS_Check() {
        return S_Check;
    }

    public static void setS_Check(int s_Check) {
        S_Check = s_Check;
    }
}
