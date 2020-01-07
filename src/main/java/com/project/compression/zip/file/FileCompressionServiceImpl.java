package com.project.compression.zip.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.project.compression.CompressionRequest;
import com.project.compression.Compressor;

/*
 * Compresses a file in a give n directory using the zip algorithm.
 * The compressed file written as a .zip file to the output directory.
 */
public class FileCompressionServiceImpl implements Compressor {

	private static final int CHUNK_SIZE = Integer.parseInt(System.getProperty("chunk.size"));

	@Override
	public long compress(CompressionRequest compressionRequest) {

		try {
			File file = new File(compressionRequest.getFileCanonicalName());
			FileInputStream fileInputStream = new FileInputStream(file);
			ZipEntry zipEntry = new ZipEntry(compressionRequest.getDirectoryName() + compressionRequest.getFileName());

			ZipOutputStream zipOutputStream = compressionRequest.getZipOutputStream();
			zipOutputStream.putNextEntry(zipEntry);

			byte[] bytes = new byte[CHUNK_SIZE];
			int length;
			while ((length = fileInputStream.read(bytes)) >= 0) {
				zipOutputStream.write(bytes, 0, length);
			}
			fileInputStream.close();
			zipOutputStream.closeEntry();
			return zipEntry.getCompressedSize();
		} catch (IOException e) {
			System.out.println("File Compression Failed for the file: " + compressionRequest.getFileName()
					+ " with the exception: " + e.getMessage());
			return 0;
		}
	}

}
