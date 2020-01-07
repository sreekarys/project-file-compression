package com.project.decompression.zip.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.project.compression.CompressionRequest;
import com.project.compression.Compressor;
import com.project.compression.zip.file.FileCompressionServiceImpl;
import com.project.decompression.Decompressor;
import com.project.util.PropertyUtil;

public class FileDecompressionServiceImplTest {

	private Decompressor fileDecompressionServiceImpl;
	private Compressor fileCompressionServiceImpl;

	@Before
	public void setUp() {
		PropertyUtil.loadSystemProps();
		fileDecompressionServiceImpl = new FileDecompressionServiceImpl();
		fileCompressionServiceImpl = new FileCompressionServiceImpl();
	}

	@Test
	public void testDecompress_HappyPath() throws IOException {
		// create a .zip with a couple of files
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(
				"./src/test/resources/testCompressionOutput/FileDecompressionServiceImplTest_testCompress_HappyPath.zip"));
		CompressionRequest request = new CompressionRequest().setDirectoryName("/").setFileName("app.properties")
				.setZipOutputStream(zipOutputStream)
				.setFileCanonicalName(new File("./src/test/resources/testInput/app.properties").getAbsolutePath());
		fileCompressionServiceImpl.compress(request);
		request = new CompressionRequest().setDirectoryName("/").setFileName("file1_testDirectory1")
				.setZipOutputStream(zipOutputStream)
				.setFileCanonicalName(new File("./src/test/resources/testInput/testDirectory1/file1_testDirectory1")
						.getAbsolutePath());
		fileCompressionServiceImpl.compress(request);
		zipOutputStream.close();

		// decompress the .zip
		fileDecompressionServiceImpl.decompress("./src/test/resources/testCompressionOutput/",
				"./src/test/resources/testDecompressionOutput/");

		// verify the contents
		File file = new File("./src/test/resources/testDecompressionOutput/");
		Set<String> files = Arrays.stream(file.listFiles()).map(File::getName).collect(Collectors.toSet());
		Assert.assertTrue(files.contains("app.properties"));
		Assert.assertTrue(files.contains("file1_testDirectory1"));

		// delete the creates files
		new File("./src/test/resources/testDecompressionOutput/app.properties").delete();
		new File("./src/test/resources/testDecompressionOutput/file1_testDirectory1").delete();
		new File(
				"./src/test/resources/testCompressionOutput/FileDecompressionServiceImplTest_testCompress_HappyPath.zip")
						.delete();
	}

	@Test
	public void testDecompress_WithoutZipFiles() throws IOException {
		try {
			fileDecompressionServiceImpl.decompress("./src/test/resources/testCompressionOutput/",
					"./src/test/resources/testDecompressionOutput/");
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
