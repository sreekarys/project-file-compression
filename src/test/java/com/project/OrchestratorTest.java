package com.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.project.exceptions.InvalidRequestException;
import com.project.util.PropertyUtil;

/*
 * INTEGRATION TESTS - /src/test/resources/testInput/ contains different cases - files, directories, nested directories, empty directories etc... 
 */
public class OrchestratorTest {
	private Orchestrator orchestrator;

	@Before
	public void setUp() {
		PropertyUtil.loadSystemProps();
		orchestrator = new Orchestrator();
	}

	@Test
	public void testCompressAndDecompress() throws InvalidRequestException, IOException {
		// compress
		orchestrator.compress("./src/test/resources/testInput/", "./src/test/resources/testCompressionOutput/", 5000);

		// verify compression
		verifyCompressionOutput();

		// decompress
		orchestrator.decompress("./src/test/resources/testCompressionOutput/",
				"./src/test/resources/testDecompressionOutput/");

		// verify decompression
		verifyDecompressionOutput();
	}

	@Test
	public void testCompress_WithNullInput() {
		try {
			orchestrator.compress(null, "./src/test/resources/", 5000);
			Assert.fail();
		} catch (InvalidRequestException e) {
			Assert.assertEquals("input", e.getKey());
		}
	}

	@Test
	public void testCompress_WithNullOutput() {
		try {
			orchestrator.compress("./src/test/resources/", null, 5000);
			Assert.fail();
		} catch (InvalidRequestException e) {
			Assert.assertEquals("output", e.getKey());
		}
	}

	@Test
	public void testCompress_WithInvalidInput() {
		try {
			orchestrator.compress("./src/test/resources/testInput/app.properties", "./src/test/resources/", 5000);
			Assert.fail();
		} catch (InvalidRequestException e) {
			Assert.assertEquals("input", e.getKey());
		}
	}

	@Test
	public void testCompress_WithInvalidOutput() {
		try {
			orchestrator.compress("./src/test/resources/", "./src/test/resources/testInput/app.properties", 5000);
			Assert.fail();
		} catch (InvalidRequestException e) {
			Assert.assertEquals("output", e.getKey());
		}
	}

	private void verifyCompressionOutput() throws IOException {
		File file = new File("./src/test/resources/testCompressionOutput/");
		List<String> files = new ArrayList<>();
		List<String> directories = new ArrayList<>();
		for (File zipFile : file.listFiles()) {
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				if (zipEntry.isDirectory()) {
					directories.add(zipEntry.getName());
				} else {
					files.add(zipEntry.getName());
				}
				zipEntry = zipInputStream.getNextEntry();
			}
			zipInputStream.close();
		}

		Assert.assertTrue(directories.contains("/testDirectory1/"));
		Assert.assertTrue(directories.contains("/testDirectory2/"));
		Assert.assertTrue(directories.contains("/testDirectory3/"));
		Assert.assertTrue(directories.contains("/testDirectory4_emptyDirectory/"));

		Assert.assertTrue(files.contains("/testDirectory1/file1_testDirectory1"));
		Assert.assertTrue(files.contains("/testDirectory2/file1_testDirectory2"));
		Assert.assertTrue(files.contains("/testDirectory3/file1_testDirectory3"));
		Assert.assertTrue(files.contains("/testDirectory1/inside1_testDirectory1/file1_inside1_testDirectory1"));
		Assert.assertTrue(files.contains("/testDirectory1/inside2_testDirectory1/file1_inside2_testDirectory1"));
		Assert.assertTrue(files.contains(
				"/testDirectory1/inside1_testDirectory1/inside_inside1_testDirectory1/file1_inside_inside1_testDirectory1"));
		Assert.assertTrue(files.contains("/testDirectory2/inside1_testDirectory2/file1_inside1_testDirectory2"));
	}

	private void verifyDecompressionOutput() {
		File decompressionOutputDirectory = new File("./src/test/resources/testDecompressionOutput/");
		List<String> files = new ArrayList<>();
		List<String> directories = new ArrayList<>();
		for (File f : decompressionOutputDirectory.listFiles()) {
			if (f.isDirectory()) {
				directories.add(f.getName());
			} else {
				files.add(f.getName());
			}
		}

		Assert.assertTrue(Arrays.stream(new File("./src/test/resources/testDecompressionOutput/").listFiles())
				.map(File::getName).collect(Collectors.toSet()).contains("testDirectory1"));
		Assert.assertTrue(Arrays.stream(new File("./src/test/resources/testDecompressionOutput/").listFiles())
				.map(File::getName).collect(Collectors.toSet()).contains("testDirectory2"));
		Assert.assertTrue(Arrays.stream(new File("./src/test/resources/testDecompressionOutput/").listFiles())
				.map(File::getName).collect(Collectors.toSet()).contains("testDirectory3"));
		Assert.assertTrue(Arrays.stream(new File("./src/test/resources/testDecompressionOutput/").listFiles())
				.map(File::getName).collect(Collectors.toSet()).contains("testDirectory4_emptyDirectory"));
		Assert.assertTrue(
				Arrays.stream(new File("./src/test/resources/testDecompressionOutput/testDirectory1/").listFiles())
						.map(File::getName).collect(Collectors.toSet()).contains("file1_testDirectory1"));
		Assert.assertTrue(
				Arrays.stream(new File("./src/test/resources/testDecompressionOutput/testDirectory2/").listFiles())
						.map(File::getName).collect(Collectors.toSet()).contains("file1_testDirectory2"));
		Assert.assertTrue(
				Arrays.stream(new File("./src/test/resources/testDecompressionOutput/testDirectory3/").listFiles())
						.map(File::getName).collect(Collectors.toSet()).contains("file1_testDirectory3"));
		Assert.assertTrue(Arrays
				.stream(new File("./src/test/resources/testDecompressionOutput/testDirectory1/inside1_testDirectory1/")
						.listFiles())
				.map(File::getName).collect(Collectors.toSet()).contains("file1_inside1_testDirectory1"));
		Assert.assertTrue(Arrays
				.stream(new File("./src/test/resources/testDecompressionOutput/testDirectory1/inside2_testDirectory1/")
						.listFiles())
				.map(File::getName).collect(Collectors.toSet()).contains("file1_inside2_testDirectory1"));

	}
}
