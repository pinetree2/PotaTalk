package Todo;
import java.util.Date;

public class ToDoListBringDTO {
    static int M_idx;
    static String M_Task;
    static Date Deadline;
    static int M_Check;
    static int SubNum;
    static String chat_index;

    public String getChat_index() {
        return chat_index;
    }

    public static void setChat_index(String chat_index) {
        chat_index = chat_index;
    }

    public int getM_idx() {
        return M_idx;
    }

    public static void setM_idx(int m_idx) {
        M_idx = m_idx;
    }

    public String getM_Task() {
        return M_Task;
    }

    public static void setM_Task(String m_Task) {
        M_Task = m_Task;
    }

    public Date getDeadline() {
        return Deadline;
    }

    public static void setDeadline(Date deadline) {
        Deadline = deadline;
    }

    public int getM_Check() {
        return M_Check;
    }

    public static void setM_Check(int m_Check) {
        M_Check = m_Check;
    }

    public int getSubNum() {
        return SubNum;
    }

    public static void setSubNum(int subNum) {
        SubNum = subNum;
    }
}
