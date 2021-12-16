import javax.xml.datatype.DatatypeFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public String MD5Hash(String input) throws NoSuchAlgorithmException {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] digest = md.digest();
//        return digest.toString();
        int len = digest.length;
        char[] str = new char[len * 2];
        int k = 0;
        for (byte byte_ : digest) {
            str[k++] = hexDigits[byte_ >>> 4 & 0xf];
            str[k++] = hexDigits[byte_ & 0xf];
        }
        return new String(str);
    }

//    public static void main(String[] args) throws NoSuchAlgorithmException {
//        MD5 md5 = new MD5();
//        System.out.println(md5.MD5Hash("ILoveJava"));
//    }
}
