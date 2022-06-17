package GUI.Log_in_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class LoginView extends JFrame {
    private LoginController logincontroller;

    private JButton btnLogin;
    private JButton btnInit;
    private JPasswordField passText;
    private JTextField userText;
    private JTextField nickText;
    private boolean bLoginCheck;

    public static String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public static void main(String[] args) {
        new LoginView();
    }


    public LoginView() {
        // setting
        setTitle("로그인");
        setSize(280, 180);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // panel
        JPanel panel = new JPanel();
        placeLoginPanel(panel);


        //background color 지정 (87, 108, 139)
        Color color = new Color(87, 108, 139);
        panel.setBackground(color);


        // add
        add(panel);

        // visiible
        setVisible(true);
    }


    public void placeLoginPanel(JPanel panel) {
        panel.setLayout(null);
        JLabel userLabel = new JLabel("ID : ");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(10, 40, 80, 25);
        panel.add(passLabel);

        JLabel nickLabel = new JLabel("Nick Name");
        nickLabel.setBounds(10, 70, 80, 25);
        panel.add(nickLabel);


        userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        passText = new JPasswordField(20);
        passText.setBounds(100, 40, 160, 25);
        panel.add(passText);

        nickText = new JTextField(20);
        nickText.setBounds(100, 70, 160, 25);
        panel.add(nickText);
        nickText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logincontroller = new LoginController();
                int result = logincontroller.login(userText.getText(), passText.getText(), nickText.getText());
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "로그인 성공");
                    setVisible(false);
                    setId(userText.getText());
                    new ClientConnect();

                } else {
                    JOptionPane.showMessageDialog(null, "Failed");
                }
            }
        });


        btnInit = new JButton("지우기");
        btnInit.setBounds(10, 110, 100, 25);
        //지우기 버튼색 지정
        Color color1 = new Color(255, 195, 195);
        btnInit.setBackground(color1);
        panel.add(btnInit);
        btnInit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userText.setText("");
                passText.setText("");
                nickText.setText("");
            }
        });

        btnLogin = new JButton("로그인");
        btnLogin.setBounds(160, 110, 100, 25);
        //로그인 버튼색 지정
        Color color2 = new Color(252, 157, 157);
        btnLogin.setBackground(color2);
        panel.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logincontroller = new LoginController();
                int result = logincontroller.login(userText.getText(), passText.getText(), nickText.getText());
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "로그인 성공");
                    setVisible(false);
                    setId(userText.getText());
                    new ClientConnect();

                } else if(result == 2){
                    JOptionPane.showMessageDialog(null, "이미 접속한 상태입니다.");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed");
                    userText.setText("");
                    passText.setText("");
                    nickText.setText("");
                }
            }
        });
    }
}


