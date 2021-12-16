/*
 * 选择网盘界面
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwitchPage {
	public static String imgpath = "";
	
	public void exec() {
		
		/*
		 * 设置背景图片
		 */
		
		ImageIcon icon=new ImageIcon(imgpath);
		JLabel label=new JLabel(icon);
		label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
		//设置label
		
		JFrame frame = new JFrame("选择网盘");
        frame.setLayout(null);
        
		frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		JPanel j=(JPanel)frame.getContentPane();
		j.setOpaque(false);
		//将label放入窗口第二层并将frame顶层设为透明
		
		/*
		 * 这里是窗口组件的相关变量
		 */
		
		int Width = 480;
		int Height = 300;
		int x = (Width - 100)/2; //文本框的x值
		int y = (Height - 200)/2;
		int i = 0;
		
		/*
		 * 设置窗口组件
		 */
		
        JLabel select = new JLabel("请选择网盘");
        select.setBounds(x-30, 20, 200, 100);
        select.setFont(new Font("黑体", Font.BOLD, 30 ));
        frame.add(select);
        
        JButton buttongetin1 = new JButton("进入个人网盘");
        buttongetin1.setBounds(x, y+70, 100, 30);
        frame.add(buttongetin1);
        
        JButton buttongetin2 = new JButton("进入群组网盘");
        buttongetin2.setBounds(x, y+120, 100, 30);
        frame.add(buttongetin2);
        
//        frame.setLayout(new FlowLayout());
        frame.setSize(Width, Height);//窗口大小
        frame.setLocationRelativeTo(null);//设置居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        /*
         * 设置监听器
         */
        
        buttongetin1.addActionListener(new ActionListener() {
         	 public void actionPerformed(ActionEvent e) {
         		 //进入
         		frame.setVisible(false);
				Client.op = 1;
         		Client.signal = true;
         	 }
         });
        buttongetin2.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 //进入
        		frame.setVisible(false);
				Client.op = 2;
        		Client.signal = true;
        	 }
        });
	}

}