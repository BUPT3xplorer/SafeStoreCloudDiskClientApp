import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class UploadFile {
	public Socket socket;		// socket连接
		
	// 实例化一个UploadFile类: 参数socket为我们进行文件传输的socket连接
	public UploadFile(Socket socket) {
		this.socket = socket;
	}

	// AESEncrypt方法, 对数据块进行AES加密: 参数clearTextBytes为我们要加密的明文数据块; keyBase64为进行AES加密的密钥被Base64之后的字符串
	private byte[] AESEncrypt(byte[] clearTextBytes, String keyBase64) throws UnsupportedEncodingException {		// 
		// byte[] keyBytes = Base64.decode(keyBase64);
		byte[] keyBytes = keyBase64.getBytes("utf-8");
		// System.out.println(Arrays.toString(keyBytes));
		
		
        try {
            // 1 获取加密密钥
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            // 2 获取Cipher实例
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

            // 查看数据块位数 默认为16（byte） * 8 =128 bit
//            System.out.println("数据块位数(byte)：" + cipher.getBlockSize());

            // 3 初始化Cipher实例。设置执行模式以及加密密钥
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            // 4 执行
            byte[] cipherTextBytes = cipher.doFinal(clearTextBytes);

            // 5 返回密文字符集
            return cipherTextBytes;

        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }
		
		
	// upload方法, 将本地文件上传到服务器: 参数file为我们要上传的本地文件; keyBase64为进行AES加密的密钥被Base64之后的字符串
	public void upload(File file, String keyBase64) throws IOException {
		
		DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
		DataInputStream in = new DataInputStream(socket.getInputStream());
		byte[] buffer = new byte[16];
		byte[] cipherText = new byte[16];
		int len = 0;
		BufferedInputStream fileBlock = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream fileOutputStream = new BufferedOutputStream(out);
		long size = file.length();
		out.writeLong(size);
		out.writeBoolean(true);
		while ((len = fileBlock.read(buffer, 0, buffer.length)) != -1) {
			cipherText =  AESEncrypt(buffer, keyBase64);
			fileOutputStream.write(cipherText, 0, len);
		}
		out.writeBoolean(false);
		
		fileOutputStream.flush();
		fileBlock.close();
		
		// 判断是否传输完成
		boolean finished = true;
		while (finished) {
			finished = in.readBoolean();
		}
		
	}
	
}
