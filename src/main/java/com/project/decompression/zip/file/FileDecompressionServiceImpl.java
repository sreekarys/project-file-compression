package com.project.decompression.zip.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.project.decompression.Decompressor;

/*
 * Decompresses all the files in the zip files present in the input directory.
 * The decompressed files are written to the output directory
 */
public class FileDecompressionServiceImpl implements Decompressor {

	private static final int CHUNK_SIZE = Integer.parseInt(System.getProperty("chunk.size"));

	@Override
	public void decompress(String input, String output) {
		try {
			for (File file : new File(input).listFiles()) {
				String zipFile = input + file.getName();
				if (!zipFile.endsWith(".zip"))
					continue;
				ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
				decompressFiles(zipInputStream, output);
				zipInputStream.close();
			}
		} catch (Exception e) {
			System.out.println("File Decompression Failed with the exception: " + e.getMessage());
		}
	}

	private void decompressFiles(ZipInputStream zipInputStream, String output) throws IOException {
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		while (zipEntry != null) {
			decompressFile(zipEntry, zipInputStream, output);
			zipEntry = zipInputStream.getNextEntry();
		}
		zipInputStream.closeEntry();
	}

	private void decompressFile(ZipEntry zipEntry, ZipInputStream zipInputStream, String output) throws IOException {
		if (!zipEntry.isDirectory()) {
			byte[] buffer = new byte[CHUNK_SIZE];
			File newFile = new File(output + zipEntry.getName());
			FileOutputStream fos = new FileOutputStream(newFile);
			int len;
			while ((len = zipInputStream.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
		}
	}
}
