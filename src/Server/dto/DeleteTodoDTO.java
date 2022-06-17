package Server.dto;

public class DeleteTodoDTO {

    static int M_idx;
    static int S_idx;

    public int getM_idx() {
        return M_idx;
    }

    public static void setM_idx(int m_idx) {
        M_idx = m_idx;
    }

    public int getS_idx() {
        return S_idx;
    }

    public static void setS_idx(int s_idx) {
        S_idx = s_idx;
    }
}
