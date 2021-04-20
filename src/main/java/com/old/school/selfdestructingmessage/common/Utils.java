package com.old.school.selfdestructingmessage.common;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.hash.Hashing;

public class Utils {
	
	private final static String algorithm = "AES/CBC/PKCS5Padding";
	private final static String salt = "QzWxEc0!";

	public static Timestamp getCurrentTime() {
		Date date = new Date();
		return new Timestamp(date.getTime());
	}

	public static String textToSHA(String originalString) {
		return Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
	}
	
	public static String encrypt(String message, String password ) throws Exception {
		SecretKey key = getSecretKey(password, salt);
		IvParameterSpec iv = generateIv();
		System.out.println("Print:-"+ iv + ", "+ iv.toString());
		return encrypt(algorithm, message, key, iv);
		
	}

	private static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv) throws Exception {

		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		byte[] cipherText = cipher.doFinal(input.getBytes());
		return Base64.getEncoder().encodeToString(cipherText);
	}
	
	public static String decrypt(String cipherText, String password ) throws Exception {
		SecretKey key = getSecretKey(password, salt);
		IvParameterSpec iv = generateIv();
		
		return decrypt(algorithm, cipherText, key, iv);
		
	}

	private static String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv)
			throws Exception {

		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
		return new String(plainText);
	}

	private static IvParameterSpec generateIv() {
		byte[] iv =  { 6, 1, 4, 2, 6, 7, 1, 9, 8, 5, 2, 1, 3, 4, 3, 7 };
		return new IvParameterSpec(iv);
	}

	private static SecretKey getSecretKey(String password, String salt) throws Exception {

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
		return secret;
	}

}
