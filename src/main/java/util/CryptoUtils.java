package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class CryptoUtils {
	public static String hash(String message) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
			md.update(message.getBytes());
			String hex = (new HexBinaryAdapter()).marshal(md.digest());
			return hex;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
