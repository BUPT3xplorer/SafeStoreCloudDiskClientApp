import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GetPasswordPage {
    public String imgpath = "";

    public void exec() throws IOException {

        /*
         * 设置背景图片
         */

        DataInputStream dis = new DataInputStream(Client.socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(Client.socket.getOutputStream());

        ImageIcon icon = new ImageIcon(imgpath);
        JLabel label = new JLabel(icon);
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        //设置label

        JFrame frame = new JFrame("找回密码");
        frame.setLayout(null);

        frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        JPanel j = (JPanel) frame.getContentPane();
        j.setOpaque(false);
        //将label放入窗口第二层并将frame顶层设为透明

        /*
         * 这里是窗口组件的相关变量
         */

        int Width = 480;
        int Height = 300;

        int x = (Width - 100) / 2; //文本框的x值
        int y = (Height - 100) / 2;
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

        JLabel select = new JLabel("找回密码");
        select.setBounds(200, 20, 150, 100);
        select.setFont(new Font("黑体", Font.BOLD, 30));
        frame.add(select);

        JLabel mailStr = new JLabel("邮箱:");
        mailStr.setBounds(x - x_side, y, text_x, text_y);
        frame.add(mailStr);

        JTextField mailName = new JTextField();
        mailName.setBounds(x, y, block_x, block_y);
        frame.add(mailName);

        JLabel checkStr = new JLabel("验证码:");
        checkStr.setBounds(x - x_side, y + y_side, text_x, text_y);
        frame.add(checkStr);

        JTextField checkName = new JTextField();
        checkName.setBounds(x, y + y_side, block_x - 90, block_y);
        frame.add(checkName);

        JButton buttongetmsg = new JButton("获取验证码");
        buttongetmsg.setBounds(x + 65, y + y_side, 90, 25);
        frame.add(buttongetmsg);

        JButton buttoncheck = new JButton("验证");
        buttoncheck.setBounds(x, y + 70, 70, 30);
        frame.add(buttoncheck);

        JButton buttonBack = new JButton("返回");
        buttonBack.setBounds(x + 80, y + 70, 70, 25);
        frame.add(buttonBack);

        buttonBack.setContentAreaFilled(false); //去除背景色
        buttonBack.setBorderPainted(false); //去除边框


        frame.setSize(Width, Height);//窗口大小
        frame.setLocationRelativeTo(null);//设置居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


//        String mail = mailName.getText(); //窗口内容参数
//        String check = checkName.getText();
        Judge judge = new Judge();

        /*
         * 设置监听器
         */
        buttongetmsg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //获取验证码

                try {
                    dos.writeUTF("ForgotYourPassword");
                    String mail = mailName.getText();
                    dos.writeUTF(mail);

                    boolean mailFLAG = false;
                    mailFLAG = dis.readBoolean();
                    if(!mailFLAG) {
                        JOptionPane.showMessageDialog(null, "没有此邮箱", "验证失败", JOptionPane.WARNING_MESSAGE);
//                    TODO
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        buttoncheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //验证
                boolean checkFLAG = false;

                frame.setVisible(false);
                String mail = mailName.getText(); //窗口内容参数
                String check = checkName.getText();

                try {
                    System.out.println("Here?");
                    dos.flush();
                    dos.writeUTF(check);
                    checkFLAG = dis.readBoolean();
                    System.out.println(checkFLAG);


                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (checkFLAG ) {
                    JOptionPane.showMessageDialog(null, "验证成功，请重置密码", "验证成功", JOptionPane.NO_OPTION);
                    System.out.println("IN?");
                    Client.myEmail = mail;
                    SetPasswordPage setPasswordPage = new SetPasswordPage();
                    try {
                        setPasswordPage.exec();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
//          			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                } else {
                    JOptionPane.showMessageDialog(null, "验证失败", "验证失败", JOptionPane.WARNING_MESSAGE);
                    Client.signal = true;
                }
            }
        });

        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //返回
                frame.setVisible(false);
            }
        });
    }

}
