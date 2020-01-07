package com.project.compression.zip.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.project.compression.CompressionRequest;
import com.project.compression.Compressor;
import com.project.util.PropertyUtil;

public class FileCompressionServiceImplTest {

	private Compressor fileCompressionServiceImpl;

	@Before
	public void setUp() {
		PropertyUtil.loadSystemProps();
		fileCompressionServiceImpl = new FileCompressionServiceImpl();
	}

	@Test
	public void testCompress_HappyPath() throws IOException {
		// create .zip using a couple of files
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(
				"./src/test/resources/testCompressionOutput/FileCompressionServiceImplTest_testCompress_HappyPath.zip"));
		CompressionRequest request = new CompressionRequest().setDirectoryName("./src/test/resources/testInput/")
				.setFileName("app.properties").setZipOutputStream(zipOutputStream)
				.setFileCanonicalName(new File("./src/test/resources/testInput/app.properties").getAbsolutePath());
		Assert.assertTrue(fileCompressionServiceImpl.compress(request) > 0);

		request = new CompressionRequest().setDirectoryName("./src/test/resources/testInput/testDirectory1/")
				.setFileName("file1_testDirectory1").setZipOutputStream(zipOutputStream)
				.setFileCanonicalName(new File("./src/test/resources/testInput/testDirectory1/file1_testDirectory1")
						.getAbsolutePath());
		Assert.assertTrue(fileCompressionServiceImpl.compress(request) > 0);
		zipOutputStream.close();

		// unzip and verify the contents
		Set<String> fileNames = Arrays
				.stream(new String[] { "./src/test/resources/testInput/app.properties",
						"./src/test/resources/testInput/testDirectory1/file1_testDirectory1" })
				.collect(Collectors.toSet());
		File zipFile = new File(
				"./src/test/resources/testCompressionOutput/FileCompressionServiceImplTest_testCompress_HappyPath.zip");
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		int numFiles = 0;
		while (zipEntry != null) {
			numFiles++;
			Assert.assertTrue(fileNames.contains(zipEntry.getName()));
			zipEntry = zipInputStream.getNextEntry();
		}
		Assert.assertEquals(2, numFiles);
		zipInputStream.close();

		// delete the created zip file
		zipFile.delete();
	}

	@Test
	public void testCompress_returnsZeroForInvalidFile() throws IOException {
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(
				"./src/test/resources/testCompressionOutput/FileCompressionServiceImplTest_testCompress_returnsZeroForInvalidFile.zip"));
		CompressionRequest request = new CompressionRequest().setDirectoryName("./src/test/resources/testInput/")
				.setFileName("invalidfile").setZipOutputStream(zipOutputStream)
				.setFileCanonicalName("./src/test/resources/testInput/invalidfile");
		Assert.assertEquals(0, fileCompressionServiceImpl.compress(request));
		zipOutputStream.close();

		// delete the file
		new File(
				"./src/test/resources/testCompressionOutput/FileCompressionServiceImplTest_testCompress_returnsZeroForInvalidFile.zip")
						.delete();
	}
}
