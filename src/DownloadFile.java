import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

public class DownloadFile {
	private Socket socket;
	
	// 实例化一个DownloadFile类: 参数socket为我们进行文件传输的socket连接
	public DownloadFile(Socket socket) {
		this.socket = socket;
	}
	
	// AESDecrypt方法, 对数据块进行AES解密: 参数cipherTextBytes为我们要解密的密文数据块; keyBase64为进行AES解密的密钥被Base64之后的字符串
	private byte[] AESDecrypt(byte[] cipherTextBytes, String keyBase64) throws UnsupportedEncodingException {
		byte[] keyBytes = keyBase64.getBytes("utf-8");
		
		 try {
	            // 1 获取解密密钥
	            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

	            // 2 获取Cipher实例
	            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

	            // 查看数据块位数 默认为16（byte） * 8 =128 bit
//	            System.out.println("数据块位数(byte)：" + cipher.getBlockSize());

	            // 3 初始化Cipher实例。设置执行模式以及加密密钥
	            cipher.init(Cipher.DECRYPT_MODE, keySpec);

	            // 4 执行
	            byte[] clearTextBytes = cipher.doFinal(cipherTextBytes);

	            // 5 返回明文字符集
	            return clearTextBytes;

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		// 解密错误 返回null
	        return null;
	}
	
	// download方法, 下载服务端的文件到本地: 参数file为我们客户端从服务器下载的文件将在本地保存的文件; keyBase64为进行AES解密的密钥被Base64之后的字符串
	public void download(File file, String keyBase64) throws IOException {
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		
		byte[] buffer = new byte[16];
		byte[] clearText = new byte[16];
		int len = 0;
		BufferedInputStream fileBlock = new BufferedInputStream(in);
		BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(file));
		long size = in.readLong();
		boolean state = false;
		state = in.readBoolean();
		if (state) {
			while ((size > 16) && ((len = fileBlock.read(buffer, 0, buffer.length)) != -1)) {
				clearText =  AESDecrypt(buffer, keyBase64);
				fileOutputStream.write(clearText, 0, len);
				size -= 16;
				
			}
			clearText =  AESDecrypt(buffer, keyBase64);
			fileOutputStream.write(clearText, 0, (int)size);

		}
		fileOutputStream.flush();
		fileOutputStream.close();
		
		// 传输结束,向发送端返回结束消息
		out.writeBoolean(false);
		
	}
}
