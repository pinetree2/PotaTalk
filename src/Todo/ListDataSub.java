package Todo;
import java.util.Comparator;
import java.util.Date;

public class ListDataSub extends ListData{
    int idx;

    ListDataSub(int M_idx, int S_idx, String Task, Date Deadline, int check, String chat_index) {
        super(M_idx, S_idx, Task, Deadline, check, chat_index);
        //this.idx = idx;
    }

    //public int getIdx(){
    //    return idx;
    //}

    @Override
    public String toString()
    {
        return S_idx + "   |   "+Task +"   |   ~" + Deadline+ "   |   "+ getDday();
    }
}