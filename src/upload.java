import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;

public class upload extends JFrame
{

    private static final long serialVersionUID = 1L;
    private JTextField field;
    public static String filepath;

    public upload() throws IOException{

        //设置输入输出流
        DataInputStream dis = new DataInputStream(Client.socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(Client.socket.getOutputStream());

        //设置窗体相关参数
        this.setTitle("拖拽文件至此进行上传");
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //设置面板
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(panel);
        panel.setLayout(new BorderLayout(0,0));

        //设置文本框
        field = new JTextField();
        field.setBounds(0, 0, 500, 200);
        panel.add(field,BorderLayout.NORTH);

        //设置按钮
        JButton confirm = new JButton("确认");

        //添加按钮监听器
        confirm.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {

                    //获取上传文件绝对路径
                    String fileDir = filepath;

                    //向服务器传递路径
                    dos.writeUTF(fileDir);

                    //设置上传参数
                    File file = new File(fileDir);
                    DataBaseConn db = new DataBaseConn();
                    String sql;
                    String AESKey = null;
                    if(Client.groupFLAG) {
                        sql = "SELECT groupName FROM user WHERE userName=\"" + Client.userName + "\"";
                        db.rs = db.stmt.executeQuery(sql);
                        String groupName = "";
                        if(db.rs.next())
                            groupName = db.rs.getString("groupName");
                        sql = "SELECT groupKey FROM groupKey WHERE groupName=\"" + groupName + "\"";
                        db.rs = db.stmt.executeQuery(sql);
                        if(db.rs.next())
                            AESKey = db.rs.getString("groupKey");
                    }
                    else {
                        sql = "SELECT userKey FROM userKey WHERE userName=\"" + Client.userName + "\"";
                        System.out.println(sql);
                        db.rs = db.stmt.executeQuery(sql);
                        if(db.rs.next())
                            AESKey = db.rs.getString("userKey");
                    }

                    UploadFile uf = new UploadFile(Client.socket);
                    uf.upload(file, AESKey);
                    dos.writeUTF(AESKey);

                    //上传成功
                    System.out.println("File Uploaded.");
                    Thread.sleep(3000);

                    //上传成功弹窗提示
                    JOptionPane.showMessageDialog(null, "上传完成", "【你完啦】",JOptionPane.CLOSED_OPTION);

                    //动态加载网盘文件
                    String info = dis.readUTF();
                    ui.devide(info);

                } catch (InterruptedException | IOException | SQLException e1) {

                    e1.printStackTrace();

                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                ;

            }

        });

        //添加按钮
        panel.add(confirm,BorderLayout.SOUTH);

        //添加上传句柄
        field.setTransferHandler(new TransferHandler()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean importData(JComponent comp, Transferable t)
            {
                try {
                    Object o = t.getTransferData(DataFlavor.javaFileListFlavor);

                    //获取上传文件在本地绝对路径
                    filepath = o.toString();
                    if (filepath.startsWith("["))
                    {
                        filepath = filepath.substring(1);
                    }
                    if (filepath.endsWith("]"))
                    {
                        filepath = filepath.substring(0, filepath.length() - 1);
                    }

                    field.setText(filepath);
                    return true;
                }
                catch (Exception e)
                {

                    e.printStackTrace();

                }
                return false;
            }

            @Override
            public boolean canImport(JComponent comp, DataFlavor[] flavors)
            {
                for (int i = 0; i < flavors.length; i++) {
                    if (DataFlavor.javaFileListFlavor.equals(flavors[i]))
                    {
                        return true;
                    }
                }
                return false;
            }
        });

        this.add(field);
        this.setVisible(true);
    }
}


