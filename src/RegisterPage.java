import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegisterPage {
    public static String username;
    public static String password;
    public static String repassword;
    public static String key;
    public static String rekey;
    public static String mail;
    public static String name;
    public static String imgpath = "";
    public static int register1 = 0;

    public void exec() throws IOException {
        DataInputStream dis = new DataInputStream(Client.socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(Client.socket.getOutputStream());

        /*
         * 背景图片
         */
        ImageIcon icon = new ImageIcon(imgpath);
        JLabel label = new JLabel(icon);
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

        JFrame frame = new JFrame("注册");
        frame.setLayout(null);

        frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        JPanel j = (JPanel) frame.getContentPane();
        j.setOpaque(false);


        /*
         * 这里是窗口组件的相关变量
         */

        int Width = 480;
        int Height = 480;

        int x = (Width - 100) / 2; //文本框的x值
        int y = (Height - 300) / 2;
        int y_side = 40;
        int x_side = 100;
        int block_x = 150;
        int block_y = 25;
        int text_x = 100;
        int text_y = 25;
        int i = 0;

        /*
         * 设置窗口组件
         */

        JLabel select = new JLabel("注册");
        select.setBounds(200, 20, 100, 100);
        select.setFont(new Font("黑体", Font.BOLD, 30));
        frame.add(select);

        JLabel nameStr = new JLabel("用户名:");
        nameStr.setBounds(x - x_side, y, text_x + i * x_side, text_y);
        frame.add(nameStr);

        JTextField userName = new JTextField();
        userName.setBounds(x, y, block_x, block_y);
        frame.add(userName);

        JLabel passwordStr = new JLabel("密码:");
        passwordStr.setBounds(x - x_side, y + 1 * y_side, text_x, text_y);
        frame.add(passwordStr);

        JPasswordField passwordName = new JPasswordField();
        passwordName.setBounds(x, y + 1 * y_side, block_x, block_y);
        frame.add(passwordName);

        JLabel repasswordStr = new JLabel("再次输入密码:");
        repasswordStr.setBounds(x - x_side, y + 2 * y_side, text_x, text_y);
        frame.add(repasswordStr);

        JPasswordField repasswordName = new JPasswordField();
        repasswordName.setBounds(x, y + 2 * y_side, block_x, block_y);
        frame.add(repasswordName);

        JLabel keyStr = new JLabel("组名:");
        keyStr.setBounds(x - x_side, y + 3 * y_side, text_x, text_y);
        frame.add(keyStr);

        JTextField keyName = new JPasswordField();
        keyName.setBounds(x, y + 3 * y_side, block_x, block_y);
        frame.add(keyName);

//        JLabel rekeyStr = new JLabel("再次输入密钥:");
//        rekeyStr.setBounds(x - x_side, y + 4 * y_side, text_x, text_y);
//        frame.add(rekeyStr);
//
//        JPasswordField rekeyName = new JPasswordField();
//        rekeyName.setBounds(x, y + 4 * y_side, block_x, block_y);
//        frame.add(rekeyName);

        JLabel mailStr = new JLabel("邮箱:");
        mailStr.setBounds(x - x_side, y + 5 * y_side, text_x, text_y);
        frame.add(mailStr);

        JTextField mailName = new JTextField();
        mailName.setBounds(x, y + 5 * y_side, block_x, block_y);
        frame.add(mailName);

        JButton buttonregister = new JButton("注册");
        buttonregister.setBounds(175, 340, 70, 25);
        frame.add(buttonregister);

        JButton buttonBack = new JButton("返回");
        buttonBack.setBounds(240, 340, 70, 25);
        frame.add(buttonBack);

        buttonBack.setContentAreaFilled(false); //去除背景色
        buttonBack.setBorderPainted(false); //去除边框


        frame.setSize(Width, Height);//窗口大小
        frame.setLocationRelativeTo(null);//设置居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        /*
         * 输入框内容参数
         */

        /*
         * 设置监听器
         */

        buttonregister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //注册
//        		register1 = 1;
                frame.setVisible(false);
                Register register = new Register();
                register.userName = userName.getText();
                register.userPwd = new String(passwordName.getPassword());
                register.rePwd = new String(repasswordName.getPassword());
                register.groupName = new String(keyName.getText());
                register.email = mailName.getText();

                Client.signal = true;

            }
        });

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //返回
                frame.setVisible(false);
            }
        });
    }
}
