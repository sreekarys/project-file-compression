package com.project.util;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class IdGeneratorTest {

	@Test
	public void testGenerate() {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*$");
		String fileName = IdGenerator.generate();
		Assert.assertTrue(pattern.matcher(fileName).matches());
		Assert.assertEquals(20, fileName.length());
	}
}
