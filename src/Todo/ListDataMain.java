package Todo;
import java.util.Comparator;
import java.util.Date;

public class ListDataMain extends ListData{
    int SubNum;

    ListDataMain(int idx, String Task, Date Deadline, int check, int SubNum, String chat_index) {
        super(idx, Task, Deadline, check, chat_index);
        this.SubNum = SubNum;
    }

    public int getSubNum(){
        return SubNum;
    }

}