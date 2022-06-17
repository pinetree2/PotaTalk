package Todo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class CheckController {

    //onclick했을때 db로 메시지 넘기는거
    // 0 -> 1 , check 값을 TRUE로
    // 1 -> 0 , check 값을 FALSE로


    public static void CheckController(int Main_Index, int Sub_index,int Chatindex,boolean Check) throws IOException {


        boolean check = Check;
        String msg;
        //메인 메시지 : 550
        int main_index = Main_Index;
        int chatindex = Chatindex;
        int sub_index = Sub_index;


        String port = "7777";
        BufferedReader in;
        Socket socket = new Socket();
        OutputStream out;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = socket.getOutputStream();
        socket = new Socket("127.0.0.1", 7777);

//sub == 0
        if(sub_index == 0) {

            msg = "550";
            try {
                //메시지,메인인덱스, 채팅방인덱스, 체크
                out.write((msg + "|" + String.valueOf(main_index) + "|" + String.valueOf(chatindex) + "|" + String.valueOf(check) + "\n").getBytes());
                in.close();
                out.close();
            } catch (IOException e) {
            }
        }else{
            msg = "600";
            try {
                //메시지,메인인덱스, 채팅방인덱스, 체크
                out.write((msg + "|" + String.valueOf(sub_index) + "|" + String.valueOf(chatindex) + "|" + String.valueOf(main_index) + "|" + String.valueOf(check) + "\n").getBytes());
                in.close();
                out.close();
            } catch (IOException e) {
            }
        }



    }
    }

//
//JOptionPane.showMessageDialog(null, "일정 내용을 입력하세요");





