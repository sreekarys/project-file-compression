package com.project.util;

/*
 * Generates random strings for compressed file names
 */
public class IdGenerator {
	private static final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int LENGTH = 20;

	public synchronized static String generate() {
		StringBuilder sb = new StringBuilder(LENGTH);
		for (int i = 0; i < LENGTH; i++) {
			int index = (int) (ALLOWED_CHARS.length() * Math.random());
			sb.append(ALLOWED_CHARS.charAt(index));
		}
		return sb.toString();
	}
}
