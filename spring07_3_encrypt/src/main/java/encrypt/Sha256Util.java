package encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Sha256Util {		//암호화 하려는 작업
	
	public static String getEncrypt(String source, String salt) {
		return getEncrypt(source, salt.getBytes());
	}
	
						//해싱을 리턴 			평문			솔트 		합칠 값
	public static String getEncrypt(String source, byte[] salt) {	//해싱할겨..
		String resultValue = "";
		
		byte[] src = source.getBytes();
		byte[] bytes = new byte[src.length + salt.length];
		
		System.arraycopy(src, 0, bytes, 0, src.length);	//arraycopy = 배열 복사
		System.arraycopy(salt, 0, bytes, src.length, salt.length);
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(bytes);
			byte[] byteData = md.digest();	//해시 결과 가져오기
			
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));	//비트를 양수로 만드는 공식
				
			}
			resultValue = sb.toString();	//결과
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return resultValue;
	}
	
	public static String genSalt() {
		Random random = new Random();
		
		byte[] salt = new byte[8];	//랜덤하게 1바이트 값 넣기
		random.nextBytes(salt);
		System.out.println("salt" + salt);
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < salt.length; i++) {
			sb.append(String.format("%02x", salt[i]));	//%02x = 바이트들을 헥사단위로?
		}
		System.out.println("salt ret : " + sb.toString());
		return sb.toString();
						
	}

}
