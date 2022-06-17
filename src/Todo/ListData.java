package Todo;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ListData {

    int M_idx;
    int S_idx;
    String Task;
    Date Deadline;
    int check;
    String chat_index;

    ListData(int M_idx, String Task, Date Deadline, int check, String chat_index){
        this.M_idx = M_idx;
        this.Task = Task;
        this.Deadline = Deadline;
        this.check = check;
        this.chat_index = chat_index;
        //this.S_idx = 0;
    }

    ListData(int M_idx, int S_idx, String Task, Date Deadline, int check, String chat_index){
        this.M_idx = M_idx;
        this.Task = Task;
        this.Deadline = Deadline;
        this.check = check;
        this.chat_index = chat_index;
        this.S_idx = S_idx;
    }

    public int getM_idx(){
        return M_idx;
    }
    public String getTask(){
        return Task;
    }
    public Date getDeadline(){
        return Deadline;
    }
    public int getCheck(){
        return check;
    }
    public String getChat_index(){
        return chat_index;
    }
    public int getS_idx(){ return S_idx;}

    static class SortByDate implements Comparator<ListData> {
        @Override
        public int compare(ListData a, ListData b) {
            return a.getDeadline().compareTo(b.getDeadline());
        }
    }

    public String getDday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(new Date());
        Date todayDate = null;
        try {
            todayDate = new Date(dateFormat.parse(today).getTime());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        int compare = getDeadline().compareTo(todayDate);

        if (compare > 0) {//deadline > today
            long diffSec = (getDeadline().getTime() - todayDate.getTime())/1000;
            long diffDays = diffSec / (24*60*60);
            return "[D-"+diffDays+"]";
        }
        else if (compare < 0) {
            long diffSec = (todayDate.getTime()-getDeadline().getTime())/1000;
            long diffDays = diffSec / (24*60*60);
            return "[D+"+diffDays+"]";
        }
        else
            return "D-DAY";
    }

    @Override
    public String toString()
    {
        return M_idx + "   |   "+Task +"   |   ~" + Deadline+ "   |   "+ getDday();
    }

}

//{1, aaa, 2022-00-00, 0}, {...

