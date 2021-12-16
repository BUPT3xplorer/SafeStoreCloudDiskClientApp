import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.DataOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AESinit {
    public static SecretKey generatorKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    public static IvParameterSpec generatorIV() {
        byte[] IV = new byte[16];
        new SecureRandom().nextBytes(IV);
        return new IvParameterSpec(IV);
    }

    public static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec IV)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, IV);
        byte[] cipherTxt = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherTxt);
    }

    public static String decrypt(String algorithm, String cipherTxt, SecretKey key, IvParameterSpec IV)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, IV);
        byte[] plainTxt = cipher.doFinal(Base64.getDecoder().decode(cipherTxt));
        return new String(plainTxt);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        String str = "Hello, World!";
        SecretKey key = generatorKey(128);
        IvParameterSpec iv = generatorIV();

        str = encrypt("CFB8", str, key, iv);
        System.out.println(str);
        str = decrypt("CFB8", str, key, iv);
        System.out.println(str);
    }

}
