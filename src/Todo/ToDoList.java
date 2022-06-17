package Todo;

import GUI.Log_in_GUI.ClientConnect;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ToDoList extends JList{
    private static JFrame toDoListFrame = new JFrame("ToDoList");
    private JButton btnAddMain;
    private JLabel toDoListLabel;
    private JPanel panel1;
    private JCheckBoxTree tree1;
    private JButton btnAddSub;
    private JButton btnDelete;
    private JButton button;
    public static ClientConnect client;
    public String room_name;
    public static void run() {
        //frame.setContentPane(new ToDoList().panel1);
        toDoListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toDoListFrame.pack();
        toDoListFrame.setVisible(true);
    }
    public static void refresh(JCheckBoxTree tree1, String chat_index) {
        ArrayList<ListDataMain> datamainlist = ToDoListBring.bringMain(chat_index);
        ArrayList<ListDataSub> datasublist = ToDoListBring.bringSub(chat_index);
        ListData rootdata = (new ListDataSub(1, 1, "To-do List", new Date(), 1, "1"));
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootdata);

        for (int i = 0; i < datamainlist.size(); i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(datamainlist.get(i));
            root.add(node);

            for (ListDataSub data : datasublist) {
                if (data.getM_idx() == datamainlist.get(i).getM_idx()) {
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode(data);
                    node.add(child);
                }
            }
        }

        tree1.setModel(new DefaultTreeModel(root), true);
        tree1.setBounds(12, 10, 135, 241);
    }

    public ToDoList(ClientConnect client, String room_name) {
        panel1.setPreferredSize(new Dimension(480, 600));
        toDoListFrame.setContentPane(panel1);
        toDoListFrame.setLocationRelativeTo(null);
        this.client = client;
        this.room_name = room_name;
        //tree1 = new JTree();
        //tree1.setCellRenderer();
        ArrayList<ListDataMain> datamainlist = ToDoListBring.bringMain(room_name);
        ArrayList<ListDataSub> datasublist = ToDoListBring.bringSub(room_name);
        if (datamainlist != null){

            ListData rootdata = (new ListDataSub(1, 1, "To-do List", new Date(), 1, "1"));
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootdata);

            for (int i = 0; i < datamainlist.size(); i++) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(datamainlist.get(i));
                root.add(node);
                button = new JButton("추가");
                for (ListDataSub data : datasublist) {
                    if (data.getM_idx() == datamainlist.get(i).getM_idx()) {
                        DefaultMutableTreeNode child = new DefaultMutableTreeNode(data);
                        node.add(child);
                    }
                }
            }


            tree1.setModel(new DefaultTreeModel(root), true);
            tree1.setBounds(12, 10, 135, 241);
        }
        else {
            ListData rootdata = (new ListDataSub(1, 1, "To-do List", new Date(), 1, "1"));
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootdata);
            tree1.setModel(new DefaultTreeModel(root), true);
            tree1.setBounds(12,10, 135,241);
        }
        // 여기가 checkbox 클릭했을 때 이벤트
        // 체크박스가 클릭됬을 경우, 그 노드만 가져온느것이 아닌 트리 전체를 가져오는 상황이 있음.
        // 최적화 할필요없으니 그냥 쓰는걸 추천함.

        btnAddMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToDoListAddMAIN a = new ToDoListAddMAIN(tree1, client, room_name); a.run();
            }
        });

        tree1.addCheckChangeEventListener(new JCheckBoxTree.CheckChangeEventListener() {
            public void checkStateChanged(JCheckBoxTree.CheckChangeEvent event) {
                //System.out.print("checkbox click => ");
                TreePath[] paths = tree1.getCheckedPaths();
                for (TreePath tp : paths) {
                    for (Object pathPart : tp.getPath()) {
                        //System.out.print(pathPart + "\n");
                    }
                    //System.out.println();
                }
            }
        });

        btnAddSub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToDoListAddSUB a = new ToDoListAddSUB(tree1, client, room_name); a.run();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToDoListDelete a = new ToDoListDelete(tree1, client, room_name); a.run();
            }
        });

    }
    public static void playCheck(int Main_Index, int Sub_index, String chat_index, boolean Check){
        boolean check = Check;
        String msg;
        //메인 메시지 : 550
        int main_index = Main_Index;
        String chatindex = chat_index;
        int sub_index = Sub_index;
        if(sub_index == 0) {

            msg = "550";
            try {
                //메시지,메인인덱스, 채팅방인덱스, 체크
                client.getDos().writeUTF(msg + "|" + String.valueOf(main_index) + "|" + String.valueOf(chatindex) + "|" + String.valueOf(check));
            } catch (IOException e) {
            }
        }else{
            msg = "600";
            try {
                //메시지,메인인덱스, 채팅방인덱스, 체크
                client.getDos().writeUTF(msg + "|" + String.valueOf(sub_index) + "|" + String.valueOf(chatindex) + "|" + String.valueOf(main_index) + "|" + String.valueOf(check));
            } catch (IOException e) {
            }
        }
    }

}
