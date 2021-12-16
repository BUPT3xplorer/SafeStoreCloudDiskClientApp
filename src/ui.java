import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;


public class ui extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private static JTable table;
    private static int row;
    private static String[] filename;
    public static String filenamereturn = "";

//    public static void main(String[] args)
//    {
//        run();//运行登录后界面
//    }
    public static void run()
    {

//	    	try {
//	            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//	        } catch (Throwable e) {
//	            e.printStackTrace();
//	        }
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try {
                    ui frame = new ui();//打印ui界面
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ui() throws IOException
    {
        //设置输入输出流
        DataInputStream dis = new DataInputStream(Client.socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(Client.socket.getOutputStream());

        //设置标题
        setTitle("还真行网盘");


        //动态展示网盘文件
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowActivated(WindowEvent arg0)
            {
                do_this_windowActivated(arg0);
                ShowFile();
            }
        });

        //设置相关页面大小属性
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        setLocationRelativeTo(contentPane);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);

        //页面滚动条
        scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        //表格
        table = new JTable();
        scrollPane.setViewportView(table);

        //上传按钮
        JButton upload = new JButton("上传");
        panel.add(upload);

        //监听上传事件
        upload.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                try {

                    //向服务器传递上传命令
                    dos.writeUTF("UploadFile");
                    System.out.println("Please Input the File Absolute Directory: ");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {

                    //实例化上传类
                    new upload();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        //实例化右键下载菜单
        final JPopupMenu jpm = new JPopupMenu();
        JMenuItem download = new JMenuItem("下载");
        jpm.add(download);

        //监听下载事件
        download.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                try {

                    //向服务器传递下载命令
                    dos.writeUTF("DownloadFile");

                } catch (IOException e1) {

                    e1.printStackTrace();
                }

                //获取下载文件名
                String fileName = table.getValueAt(row,1).toString();

                try {

                    //向服务器传递下载文件名
                    dos.writeUTF(fileName);

                } catch (IOException e1) {

                    e1.printStackTrace();

                }

                //指定下载绝对路径
                String destFile = "D:/CODE/cloudDiskClient/" + fileName;
                System.out.println(destFile);
                File file = new File(destFile);
                File fileDir = new File("D:/CODE/cloudDiskClient/");

                //创建目录
                if(!fileDir.exists())
                    fileDir.mkdirs();

                //下载加密
                String AESKey = null;
                try {
                    AESKey = dis.readUTF();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                DownloadFile df = new DownloadFile(Client.socket);
                try {

                    //实例化下载类
                    df.download(file, AESKey);

                } catch (IOException e1) {

                    e1.printStackTrace();
                }

                //下载完成
                System.out.println("File Received.");

                //弹窗提示下载完成
                JOptionPane.showMessageDialog(null, "下载完成", "【你完啦】",JOptionPane.CLOSED_OPTION);

            }
        });

        //监听鼠标右键事件
        table.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e){

                if (e.getButton() == MouseEvent.BUTTON3){
                    //获取当前鼠标点击位置所在的行号
                    row = table.rowAtPoint(e.getPoint());
                    filenamereturn = (String) table.getValueAt(row,1);

                    //在鼠标点击处展示右键菜单
                    jpm.show(table, e.getX(), e.getY());
                }
            }
        });
    }


    protected void ShowFile()
    {

        //新建表格
        final DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        new Thread()
        {
            public void run()
            {
                for (int i = 0; i < filename.length; i++)
                {

                    //分别展示文件序号以及文件名
                    Object[] property = new Object[2];
                    property[0] = i + 1;
                    property[1] = filename[i];

                    //向表格中添加行
                    model.addRow(property);
                    table.setModel(model);

                    try
                    {
                        //线程刷新
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }
                }
            };
        }.start();
    }

    //绝对路径截取文件名
    public static void devide(String s)
    {
        filename = s.split("\n");
        for (int i = 0;i < filename.length;i++)
            System.out.println(filename[i]);
    }

    //表格中展示文件序号及文件名
    protected void do_this_windowActivated(WindowEvent arg0)
    {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(new Object[] { "序号", "文件名" });
    }


}