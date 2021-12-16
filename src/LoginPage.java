import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;

public class LoginPage {
    public String imgpath = "";

    //	@SuppressWarnings("deprecation")
    public void exec() {

        /*
         * 设置背景图片
         */

        ImageIcon icon=new ImageIcon(imgpath);  //背景图片路径

        JLabel label=new JLabel(icon);	//将图片放入label中
        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());	//设置label的大小

        JFrame frame = new JFrame("登录");
        frame.setLayout(null);

        frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));	//获取窗口的第二层，将label放入
        JPanel j=(JPanel)frame.getContentPane();  //获取frame的顶层容器,并设置为透明
        j.setOpaque(false);

        /*
         * 设置窗口组件
         */
        int x = 130;
        int x_side = 35;

        JLabel nameStr = new JLabel("账号:");
        nameStr.setBounds(x, 100, 100, 25);
        frame.add(nameStr);

        JLabel passwordStr = new JLabel("密码:");
        passwordStr.setBounds(x, 140, 100, 25);
        frame.add(passwordStr);

        JTextField userName = new JTextField();
        userName.setBounds(x+x_side, 100, 150, 25);
        frame.add(userName);

        JPasswordField passwordName = new JPasswordField();
        passwordName.setBounds(x+x_side, 140, 150, 25);
        frame.add(passwordName);

        JLabel select = new JLabel("登录");
        select.setBounds(200, 00, 100, 100);
        select.setFont(new Font("宋体", Font.BOLD, 30));
        frame.add(select);

        JButton buttonlogin = new JButton("登录");
        buttonlogin.setBounds(160, 180, 150, 30);
        frame.add(buttonlogin);

        JButton buttonregister = new JButton("没有账号？");
        buttonregister.setBounds(160, 210, 100, 25);
        frame.add(buttonregister);

        JButton buttonGetPassword = new JButton("忘记密码？");
        buttonGetPassword.setBounds(220, 210, 100, 25);
        frame.add(buttonGetPassword);

        buttonGetPassword.setContentAreaFilled(false); //去除背景色
        buttonGetPassword.setBorderPainted(false); //去除边框
        buttonregister.setContentAreaFilled(false);
        buttonregister.setBorderPainted(false);


        /*
         * 设置窗口大小及样式
         */

        int Width = 480;
        int Height = 300;
        frame.setSize(Width, Height);  //窗口大小
        frame.setLocationRelativeTo(null);  //设置窗口居中弹出
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        /*
         * 设置监听器
         */

        Judge judge = new Judge();

        buttonregister.addActionListener(new ActionListener() { //Register
            public void actionPerformed(ActionEvent e) {
                //注册
                Client.optionNum = 1;
                frame.setVisible(false);
//                Register_page ar = new Register_page();

                Client.signal = true;
            }
        });

        buttonlogin.addActionListener(new ActionListener() { //Login
            public void actionPerformed(ActionEvent e) {
                //登录
                Client.optionNum = 2;
                frame.setVisible(false);
                Client.userName = userName.getText();
                Client.userPwd = new String(passwordName.getPassword());

                Client.signal = true;
            }
        });

        buttonGetPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //找回密码页面
                Client.optionNum = 3;
                frame.setVisible(false);
                System.out.println("Here?");
                Client.signal = true;
            }
        });

    }


//    public static void main(String []args) {
//        try {
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        }catch(Exception e) {
//            System.out.println(e);
//        }  //进行样式美化
//
//        Login_page AdminRegister = new Login_page();
//    }
}
