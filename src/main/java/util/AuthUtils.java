package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.security.auth.login.LoginException;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import persistance.entity.Member;

public class AuthUtils {

	private static String salt = UUID.randomUUID().toString();

	public static String login(Member member, String password)
			throws LoginException {
		try {
			if (member.getPassword().equals(password)) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(salt.getBytes());
				md.update(member.getUsername().getBytes());

				String hex = (new HexBinaryAdapter()).marshal(md.digest());
				return hex;
			}
			throw new LoginException("Wrong login / password");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		throw new LoginException("Error while generating token");
	}

	public static boolean validate(Member member, String token)
			throws LoginException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(salt.getBytes());
			md.update(member.getUsername().getBytes());
			String hex = (new HexBinaryAdapter()).marshal(md.digest());
			return hex.equals(token);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		throw new LoginException("Error while validating token");
	}
}
