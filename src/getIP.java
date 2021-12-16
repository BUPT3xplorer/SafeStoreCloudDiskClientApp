import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class getIP {
    public static String iP;
    public static int what_way;
    public static String imgpath = "";
    public static String IP = "localhost";
    public static final int PORT = 10000;
    boolean flag = false;

    public void exec() {
        ImageIcon icon = new ImageIcon(imgpath);
        //Image im=new Image(icon);
        //将图片放入label中
        //../
        JLabel label = new JLabel(icon);

        //设置label的大小
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

        JFrame frame = new JFrame("获取IP");
        frame.setLayout(null);

        //获取窗口的第二层，将label放入
        frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

        //获取frame的顶层容器,并设置为透明
        JPanel j = (JPanel) frame.getContentPane();
        j.setOpaque(false);

        JLabel ipStr = new JLabel("IP:");
        //请输入服务器IP:
        ipStr.setBounds(100, 100, 100, 60);
        ipStr.setFont(new Font("宋体", Font.BOLD, 60));
        frame.add(ipStr);

        JTextField serverIP = new JTextField();
        serverIP.setBounds(200, 100, 150, 25);
        frame.add(serverIP);

        JButton buttonIP = new JButton("发送");
        buttonIP.setBounds(175, 200, 70, 25);
//        buttonIP.setContentAreaFilled(false); //去除背景色
//        buttonIP.setBorderPainted(false); //去除边框
        frame.add(buttonIP);

        frame.setBounds(200, 50, 400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);//设置居中
        /*
         * 界面的大小和位置回头再调整
         * 还有看看能否插入写图片、整点花活
         */
        //为按钮添加监听器
        buttonIP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iP = serverIP.getText();
                //登录
                Judge IPjudge = new Judge();

                /*
                 * 这里还需要改的，IP是在服务器判断，是否成功我需要一个参数
                 */
                if (IPjudge.isIP(iP) == false) {
                    //弹出账号或密码错误的窗口
                    flag = false;
                    JOptionPane.showMessageDialog(null, "IP错误", "IP错误", JOptionPane.WARNING_MESSAGE);
                    //清除IP框中的信息
                    serverIP.setText("");
//                get_IP get_IP = new get_IP();
                    //System.out.println("Ip失败");
                } else {
                    flag = true;
                    Client.IP_ADDR = iP;
                    if (true) {//这里需要一个返回值
//            		   what_way = 2;
                        //弹出登录成功的窗口
//            			Login login = new Login();;
//            			try {
//							login.exec(socket);
//						} catch (Exception e1) {
//							e1.printStackTrace();
//						}

                        JOptionPane.showMessageDialog(null, "get_IP连接成功", "get_IP连接成功", JOptionPane.NO_OPTION);
                        //点击确定后会跳转到主窗口
                        frame.setVisible(false);
                        frame.dispose();
//                        new LoginPage();
                    } else {
                        JOptionPane.showMessageDialog(null, "get_IP连接失败", "get_IP连接失败", JOptionPane.WARNING_MESSAGE);
                    }
                }
                Client.signal = true;
            }
        });

    }


//    public static void main(String[] args) {
//
//        try {
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        getIP getIP = new getIP(); //建立窗口
//    }

}
