package testcase.T_Cipher_IV;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText plaintext;
	EditText ciphertext;
	EditText keyString;
	Button encrypt;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		plaintext = (EditText) findViewById(R.id.et1);
		keyString = (EditText) findViewById(R.id.et2);
		ciphertext = (EditText) findViewById(R.id.et3);
		encrypt = (Button) findViewById(R.id.bt1);
		plaintext.setText("this is the test");
		encrypt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ciphertext.setText(cipher());
			}
		});
	}
  
	private String cipher() {// 固定随机数  
		try {
			byte[] IV = new byte[16];
			IV = "0123456789abcdef".getBytes();// 缓冲区
			SecretKeySpec securekey = new SecretKeySpec(keyString.getText().toString().getBytes(), "AES");// 使用
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding ");// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, securekey, new IvParameterSpec(IV));
			byte[] byteContent = plaintext.getText().toString().getBytes("utf-8");
			byte[] result = cipher.doFinal(byteContent);
			return parseByte2HexStr(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
}
