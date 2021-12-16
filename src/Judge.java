/*
 * 用于IP判断
 */

import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Judge {

    public static String imgpath = "E:\\_动力网盘\\图片测试\\bg2.jpg";	//背景图片路径

    static public boolean isIP(String addr){
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))
            return false;
        String rexp = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\\." +
                "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\\." +
                "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\\." +
                "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$" ;

        // 创建 Pattern 对象
        Pattern pat = Pattern.compile(rexp);

        // 创建 matcher对象
        Matcher mat = pat.matcher(addr);

        return mat.find();
    }

    static public boolean isMail(String addr) {
        if(addr.length() < 3 || addr.length() > 20 || "".equals(addr))
            return false;
        String rexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?\r\n" +
                "^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\r\n" +
                "\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-\r\n" +
                "z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:\r\n" +
                "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4]\r\n" +
                "[0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\r\n" +
                "\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\r\n" +
                "\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        // 创建 Pattern 对象
        Pattern pat = Pattern.compile(rexp);

        // 创建 matcher对象
        Matcher mat = pat.matcher(addr);

        return mat.find();
    }

    static public boolean isPassword(String addr){
        if("".equals(addr))
            return false;
        //输入的只能是大小写字母、数字和特殊字符的组合
        String rexp ="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        //正则表达式 6-16数字字母
        // 创建 Pattern 对象
        Pattern pat = Pattern.compile(rexp);
        // 创建 matcher对象
        Matcher mat = pat.matcher(addr);

        return mat.find();
    }

    static public boolean isEqual(String a1,String a2) {
        if("".equals(a1)||"".equals(a2)||a1.equals(a2)==false)
            return false;
        else {
            return true;
        }
    }
//    static public int getRequest() {
//        int get1 = Register_page.register1;
//        int get2 =Login_page.login3;
//        System.out.println("get1="+get1);
//        System.out.println("get2="+get2);
//        if(get1==1&&get2==3) {
//            return 1;
//        }else if(get1==0 && get2==2){
//            return 2;
//        }
//        else {
//            return 3;
//        }
//        //这里的register1初始值为0，1的时候在注册
//        //login3初始值为3，2的时候在登陆
//    }


}

