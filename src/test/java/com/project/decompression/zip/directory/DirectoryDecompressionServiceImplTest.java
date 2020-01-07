package com.project.decompression.zip.directory;

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
import com.project.compression.zip.directory.DirectoryCompressionServiceImpl;
import com.project.decompression.Decompressor;

public class DirectoryDecompressionServiceImplTest {

	private Decompressor directoryDecompressionServiceImpl;
	private Compressor directoryCompressionServiceImpl;

	@Before
	public void setUp() {
		directoryDecompressionServiceImpl = new DirectoryDecompressionServiceImpl();
		directoryCompressionServiceImpl = new DirectoryCompressionServiceImpl();
	}

	@Test
	public void testDecompress_HappyPath() throws IOException {
		// compress directories
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(
				"./src/test/resources/testCompressionOutput/DirectoryDecompressionServiceImplTest_testDecompress_HappyPath.zip"));
		CompressionRequest request = new CompressionRequest().setDirectoryName("testDirectory1/")
				.setZipOutputStream(zipOutputStream);
		directoryCompressionServiceImpl.compress(request);

		request = new CompressionRequest().setDirectoryName("testDirectory4/").setZipOutputStream(zipOutputStream);
		directoryCompressionServiceImpl.compress(request);
		zipOutputStream.close();

		// verify the contents
		directoryDecompressionServiceImpl.decompress("./src/test/resources/testCompressionOutput/",
				"./src/test/resources/testDecompressionOutput/");
		File file = new File("./src/test/resources/testDecompressionOutput/");
		Set<String> files = Arrays.stream(file.listFiles()).map(File::getName).collect(Collectors.toSet());
		Assert.assertTrue(files.contains("testDirectory1"));
		Assert.assertTrue(files.contains("testDirectory4"));
		// delete the files created
		new File("./src/test/resources/testDecompressionOutput/testDirectory1/").delete();
		new File("./src/test/resources/testDecompressionOutput/testDirectory4/").delete();
		new File(
				"./src/test/resources/testCompressionOutput/DirectoryDecompressionServiceImplTest_testDecompress_HappyPath.zip")
						.delete();
	}
}
