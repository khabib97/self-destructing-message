package com.old.school.selfdestructingmessage.common;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;

import com.google.common.hash.Hashing;

public class Utils {

	public static Timestamp getCurrentTime() {
		Date date = new Date();
		return new Timestamp(date.getTime());
	}

	public static String textToSHA(String originalString) {
		return Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
	}

}
