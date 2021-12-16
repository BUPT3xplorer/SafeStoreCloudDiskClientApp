import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Client {
    public static String IP_ADDR = "localhost";
    public static final int PORT = 5678;
    public static final String SAVEDir = "D:/CODE/cloudDiskClient/";
    public static Socket socket;
    public static boolean signal;
    public static String userName;
    public static String userPwd;
    public static int optionNum;
    public static String myEmail;
    public static int op;
    public static boolean groupFLAG;

    public static void main(String[] args) throws Exception {


        boolean runningFLAG = true;
        getIP gip = new getIP();

        //Use signal to achieve Block&Run
        boolean firstIn = true;
        signal = false;
        while (!signal) {
            if(firstIn) {
                gip.exec();
                firstIn = false;
            }
            System.out.flush();
        }
        System.out.println(IP_ADDR);

        socket = new Socket(IP_ADDR, PORT);
        while(runningFLAG) {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            LoginPage loginPage = new LoginPage();

            System.out.println("----------- CLOUD DISK Client -----------");
            System.out.println("1) Register\n2) Login\n3) Forgot Your Password?\n4) Exit\n");
//            Scanner in = new Scanner(System.in);


//            int optionNum = in.nextInt();

            firstIn = true;
            signal = false;
            while(!signal) {
                if(firstIn) {
                    loginPage.exec();
                    firstIn = false;
                }
                System.out.flush();
            }
            System.out.println(optionNum);

            switch (optionNum) {
                case 1: {
                    dos.writeUTF("Register");
                    Register register = new Register();
                    register.exec(socket);
                    break;
                }
                case 2: {
                    dos.writeUTF("Login");
//                    System.out.println("Please Input Your User Name:");
//                    String userName = in.next();
//                    System.out.println("Please Input Your User Password:");
//                    String userPwd = in.next();
                    MD5 md5 = new MD5();
                    userPwd = md5.MD5Hash(userPwd);
                    String userNamePwd = userName + "&" + userPwd;
                    dos.writeUTF(userNamePwd);
                    boolean loginFLAG = dis.readBoolean();
                    if (loginFLAG) {
                        JOptionPane.showMessageDialog(null, "登录成功", "登录成功", JOptionPane.NO_OPTION);
                        System.out.println("Login Success.");
                        System.out.println("1) Private Directory\n2) Group Directory\n");
//                        int op = in.nextInt();

                        SwitchPage switchPage = new SwitchPage();
                        signal = false;
                        firstIn = true;
                        while(!signal) {
                            if(firstIn) {
                                switchPage.exec();
                                firstIn = false;
                            }
                            System.out.flush();
                        }

                        if (op == 1)
                            dos.writeUTF("privateDir");
                        else {
                            dos.writeUTF("groupDir");
                            groupFLAG = true;
                        }

                        boolean dirFLAG = dis.readBoolean();
                        if (dirFLAG) {
                            String info = dis.readUTF();
                            ui.devide(info);
                            ui.run();
                            System.out.println(info);
//                            System.out.println(info);
//                            int opFile = in.nextInt();
//                            int opFile = 0;
//                            switch (opFile) {
//                                case 1: { // upload file
//                                    dos.writeUTF("UploadFile");
//                                    System.out.println("Please Input the File Absolute Directory: ");
//
////                                    String fileDir = in.next();
//                                    String fileDir = "";
//                                    String[] fileDirSpilt = fileDir.split("/");
//                                    String fileName = fileDirSpilt[fileDirSpilt.length - 1];
//                                    dos.writeUTF(fileName);
//
//                                    File file = new File(fileDir);
//                                    String AESKey = "00Awn2Me+1qjQIKyWzUJrw==";
//                                    UploadFile uf = new UploadFile(socket);
//                                    uf.upload(file, AESKey);
//
//                                    dos.writeUTF(AESKey);
//
//                                    System.out.println("File Uploaded.");
//
//                                    break;
//                                }
//                                case 2: { // download file
//                                    dos.writeUTF("DownloadFile");
//
//                                    String AESKey = dis.readUTF();
//
//                                    System.out.println("Please Input the File Name: ");
////                                    String fileName = in.next();
//                                    String fileName = "";
//                                    dos.writeUTF(fileName);
//
//                                    String destFile = SAVEDir + userName + "/" + fileName;
//                                    File file = new File(destFile);
//                                    File fileDir = new File(SAVEDir + userName + "/");
//                                    if(!fileDir.exists())
//                                        fileDir.mkdirs();
//
//                                    DownloadFile df = new DownloadFile(socket);
//                                    df.download(file, AESKey);
//                                    System.out.println("File Received.");
//
//                                    break;
//                                }
//                                case 3: { // back
//                                    dos.writeUTF("Back");
//                                    break;
//                                }
//                            }

                        } else {
                            System.out.println("No Such File or Directory.");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "登录失败", "登录失败", JOptionPane.WARNING_MESSAGE);
                        System.out.println("Sorry, Login Failed.");
                    }

                    break;
                }
                case 3: {
                    GetPasswordPage getPasswordPage = new GetPasswordPage();
                    signal = false;
                    firstIn = true;
                    while (!signal) {
                        if(firstIn) {
                            getPasswordPage.exec();
                            firstIn = false;
                        }
                        System.out.flush();
                    }

//                    dos.writeUTF("ForgotYourPassword");
//                    System.out.println("Please Input Your Email:");
//                    String myEmail = in.next();
//                    dos.writeUTF(myEmail);
//                    boolean mailFLAG = dis.readBoolean();
//                    boolean mailFLAG = true;
//                    if(mailFLAG) {
//                        System.out.println("The CODE Has Been Sent to Your Email.");
//                        System.out.println("Please input the CODE you received:");
//                        String code = in.next();
//                        String code = "";
//                        dos.writeUTF(code);
//                        int tryTimes = 3;
//                        dos.writeInt(tryTimes);
//                        boolean authFLAG = dis.readBoolean();
//                        while(!authFLAG && tryTimes > 0) {
//                            System.out.println("Wrong CODE! You Have " + tryTimes + " Time(s). Try Again: ");
//                            dos.writeUTF(in.next());
//                            dos.writeUTF("!!!!!!!!!!!!!!!!!!");
//                            tryTimes--;
//                            authFLAG = dis.readBoolean();
//                        }
//                        if(authFLAG) {
//                            System.out.println("Please Input a New Password: ");
//                            String nPwd = in.next();
//                            String nPwd = "";
//                            MD5 md5 = new MD5();
//                            nPwd = md5.MD5Hash(nPwd);
//                            dos.writeUTF(nPwd);
//                            boolean resetFLAG = dis.readBoolean();
//                            if(resetFLAG)
//                                System.out.println("The New Password Has Been Set!");
//                        }
//                        else {
//                            System.out.println("Wrong CODE!");
//                        }
//                    }
//                    else {
//
//                        System.out.println("No Such Email Exists.");
//                    }
                    break;
                }
                case 4: {
                    dos.writeUTF("Exit");
                    runningFLAG = false;
                    break;
                }
            }
        }
    }
}
