package com.bazlur.eshoppers.service;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberRegexTest {

	@Test
	public void testValidPhoneNumber() {
		//^(?:\+88|01)?\d{11}$
		final Pattern pattern = Pattern.compile("^(?:\\+88|01)?\\d{11}");
		final Matcher matcher = pattern.matcher("+8812345678901");

		while (matcher.find()) {
			System.out.print("Start index: " + matcher.start());
			System.out.print(" End index: " + matcher.end() + " ");
			System.out.println(" group: " + matcher.group());
		}

	}
}
