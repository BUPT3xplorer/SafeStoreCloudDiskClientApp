import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Register {
    public static String userName;
    public static String userPwd;
    public static String rePwd;
    public static String groupName;
    public static String email;


    public void exec(Socket socket) throws Exception {
        try {
            Scanner in = new Scanner(System.in);
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            RegisterPage registerPage = new RegisterPage();

            Client.signal = false;
            Boolean firstIn = true;
            while (!Client.signal) {
                if (firstIn) {
                    registerPage.exec();
                    firstIn = false;
                }
                System.out.flush();
            }

            boolean validUsername = false;// 用户名有效判定
            boolean registerSuccess = false;// 注册成功判定
            try {
                // 用户名
//                    System.out.println("请输入用户名：");
                output.writeUTF(userName);// 传送用户名
                validUsername = input.readBoolean();// 读取用户名有效判定结果
                if (!validUsername) {
                    // 用户名无效
                    System.out.println("用户名无效。");
//                        continue;
                }
                // 密码
//                    System.out.println("请输入密码：");
//                    String password = in.next();// 输入密码
//                    System.out.println("请确认密码：");
//                    String confirm = in.next();// 确认密码
                if (!userPwd.equals(rePwd)) {
                    // 两次输入的密码不一致
                    System.out.println("两次输入的密码不一致。");
                }
                MD5 md5 = new MD5();
                userPwd = md5.MD5Hash(userPwd);
                output.writeUTF(userPwd);// 传送密码
                // 邮箱
//                    System.out.println("请输入邮箱：");
//                    String email = in.next();// 输入邮箱
                output.writeUTF(email);// 传送邮箱

                // 完成注册

                registerSuccess = input.readBoolean();// 读取注册成功判定结果
//                    System.out.println("Over!");
                if (!registerSuccess) {
                    // 注册失败
                    System.out.println("注册失败。");
                } else {
                    // 注册成功
                    System.out.println("注册成功。");
                }

                System.out.println("Pleas Input Your Group Name: ");
//                String groupName = in.next();
                output.writeUTF(groupName);
                boolean flag = input.readBoolean();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
