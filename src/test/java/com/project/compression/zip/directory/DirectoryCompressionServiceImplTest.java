package com.project.compression.zip.directory;

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

public class DirectoryCompressionServiceImplTest {

	private Compressor directoryCompressionServiceImpl;

	@Before
	public void setUp() {
		directoryCompressionServiceImpl = new DirectoryCompressionServiceImpl();
	}

	@Test
	public void testCompress_HappyPath() throws IOException {
		// create .zip using a couple of directories
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(
				"./src/test/resources/testCompressionOutput/DirectoryCompressionServiceImplTest_testCompress_HappyPath.zip"));
		CompressionRequest request = new CompressionRequest()
				.setDirectoryName("./src/test/resources/testInput/testDirectory1/").setZipOutputStream(zipOutputStream);
		Assert.assertTrue(directoryCompressionServiceImpl.compress(request) > 0);

		request = new CompressionRequest().setDirectoryName("./src/test/resources/testInput/testDirectory4/")
				.setZipOutputStream(zipOutputStream);
		Assert.assertTrue(directoryCompressionServiceImpl.compress(request) > 0);
		zipOutputStream.close();

		// unzip and verify the contents
		Set<String> fileNames = Arrays.stream(new String[] { "./src/test/resources/testInput/testDirectory4/",
				"./src/test/resources/testInput/testDirectory1/" }).collect(Collectors.toSet());
		File zipFile = new File(
				"./src/test/resources/testCompressionOutput/DirectoryCompressionServiceImplTest_testCompress_HappyPath.zip");
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
	public void testCompress_returnsZeroForInvalidDirectory() throws IOException {
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(
				"./src/test/resources/testCompressionOutput/DirectoryCompressionServiceImplTest_testCompress_returnsZeroForInvalidDirectory.zip"));

		CompressionRequest request = new CompressionRequest().setDirectoryName("").setZipOutputStream(zipOutputStream);
		Assert.assertEquals(0, directoryCompressionServiceImpl.compress(request));

		request = new CompressionRequest().setDirectoryName(null).setZipOutputStream(zipOutputStream);
		Assert.assertEquals(0, directoryCompressionServiceImpl.compress(request));
		zipOutputStream.close();

		// delete the file
		new File(
				"./src/test/resources/testCompressionOutput/DirectoryCompressionServiceImplTest_testCompress_returnsZeroForInvalidDirectory.zip")
						.delete();
	}
}
