package util;

import java.util.UUID;

import javax.security.auth.login.LoginException;

import persistance.entity.Member;

public class AuthUtils {

	private static String salt = UUID.randomUUID().toString();

	public static String login(Member member, String password)
			throws LoginException {
		if (member.getPassword().equals(CryptoUtils.hash(password))) {
			return CryptoUtils.hash(salt + member.getUsername());
		}
		throw new LoginException("Wrong login / password");
	}

	public static boolean validate(Member member, String token) {

		return CryptoUtils.hash(salt + member.getUsername()).equals(token);

	}
}
