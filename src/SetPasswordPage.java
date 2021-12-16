import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SetPasswordPage {
    private static String imgpath = "";
    /*
     * 设置背景图片
     */

    public void exec() throws IOException {
        DataInputStream dis = new DataInputStream(Client.socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(Client.socket.getOutputStream());

        ImageIcon icon=new ImageIcon(imgpath);
        JLabel label=new JLabel(icon);
        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
        //设置label

        JFrame frame = new JFrame("重置密码");
        frame.setLayout(null);

        frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        JPanel j=(JPanel)frame.getContentPane();
        j.setOpaque(false);

        /*
         * 这里是窗口组件的相关变量
         */

        int Width = 480;
        int Height = 300;

        int x = (Width - 100)/2; //文本框的x值
        int y = (Height - 100)/2;
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

        JLabel select = new JLabel("重置密码");
        select.setBounds(200, 20, 150, 100);
        select.setFont(new Font("黑体", Font.BOLD, 30 ));
        frame.add(select);

        JLabel passwordStr = new JLabel("密码:");
        passwordStr.setBounds(x-x_side, y+0*y_side, text_x, text_y);
        frame.add(passwordStr);

        JPasswordField passwordName = new JPasswordField();
        passwordName.setBounds(x, y+0*y_side, block_x, block_y);
        frame.add(passwordName);

        JLabel repasswordStr = new JLabel("再次输入密码:");
        repasswordStr.setBounds(x-x_side, y+1*y_side, text_x, text_y);
        frame.add(repasswordStr);

        JPasswordField repasswordName = new JPasswordField();
        repasswordName.setBounds(x, y+1*y_side, block_x, block_y);
        frame.add(repasswordName);

        JButton buttonregister = new JButton("重置");
        buttonregister.setBounds(x, y+70, 100, 30);
        frame.add(buttonregister);

        frame.setSize(Width, Height);//窗口大小
        frame.setLocationRelativeTo(null);//设置居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Judge judge = new Judge();

        /*
         * 设置监听器
         */

        /*
         * 关于这里的判断还有问题
         * 还要加一个返回？
         */
        buttonregister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //重置
                frame.setVisible(false);
                String password=new String(passwordName.getPassword());
                String repassword=new String(repasswordName.getPassword()); //相关的变量

//         		System.out.println(password);
                if(password!=""&&repassword!="") {
                    try {
                        MD5 md5 = new MD5();
                        password = md5.MD5Hash(password);
                        dos.writeUTF(password);
                    } catch (IOException | NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }
//                    if(judge.isPassword(password)) {
//                        JOptionPane.showMessageDialog(null, "密码重置成功", "密码重置成功", JOptionPane.NO_OPTION);
////                        Login_page AdminRegister = new Login_page();
//                    }else {
//                        JOptionPane.showMessageDialog(null, "密码重置失败", "密码重置失败", JOptionPane.WARNING_MESSAGE);
////                        Setpassword_page Setpassword_page = new Setpassword_page();
//                    }
                }else {
                    JOptionPane.showMessageDialog(null, password, "密码重置失败", JOptionPane.WARNING_MESSAGE);
                }
                Client.signal = true;
            }
        });
    }
}
