package com.project.compression.zip.directory;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.project.compression.CompressionRequest;
import com.project.compression.Compressor;

/*
 * Compresses a directory using the zip algorithm.
 * The compressed directory written as a .zip file to the output directory.
 */

public class DirectoryCompressionServiceImpl implements Compressor {

	@Override
	public long compress(CompressionRequest compressionRequest) {
		try {
			String directoryName = compressionRequest.getDirectoryName();
			ZipOutputStream zipOutputStream = compressionRequest.getZipOutputStream();
			if (directoryName != null && !directoryName.isEmpty()) {
				ZipEntry zipEntry = new ZipEntry(directoryName);
				zipOutputStream.putNextEntry(zipEntry);
				zipOutputStream.closeEntry();
				return zipEntry.getCompressedSize();
			}
		} catch (Exception e) {
			System.out.println("File Compression Failed for the directory: " + compressionRequest.getDirectoryName()
					+ "with the exception: " + e.getMessage());
		}
		return 0;
	}

}
