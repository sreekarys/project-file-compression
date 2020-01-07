package com.project.compression.zip.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import com.project.compression.CompressionCommandRequest;
import com.project.compression.CompressionRequest;
import com.project.compression.Compressor;
import com.project.compression.zip.directory.DirectoryCompressionServiceImpl;
import com.project.util.IdGenerator;

/*
 * Command issued to compress a single directory.
 * Takes CompressionCommandRequest as input and tries to compress multiple files present
 * in a given directory into a single zip file, until the size of the zip file exceeds a maxSize.
 */
public class CompressionCommand implements Runnable {
	private static final Compressor FILE_COMPRESSOR = new FileCompressionServiceImpl();
	private static final Compressor DIRECTORY_COMPRESSOR = new DirectoryCompressionServiceImpl();

	private final CompressionCommandRequest request;

	public CompressionCommand(CompressionCommandRequest request) {
		this.request = request;
	}

	@Override
	public void run() {
		try {
			long currentSize = 0;
			ZipOutputStream zipOutputStream = new ZipOutputStream(
					new FileOutputStream(request.getOutput() + IdGenerator.generate() + ".zip"));

			currentSize += compressDirectory(zipOutputStream, request);
			for (String fileName : request.getFiles()) {
				if (currentSize >= request.getMaxSize()) {
					zipOutputStream = initializeZipStream(zipOutputStream, request);
					currentSize = 0;
				}
				currentSize += compressFile(zipOutputStream, request, fileName);
			}

			zipOutputStream.close();
			request.getLatch().countDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ZipOutputStream initializeZipStream(ZipOutputStream zipOutputStream, CompressionCommandRequest request)
			throws IOException, FileNotFoundException {
		zipOutputStream.close();
		zipOutputStream = new ZipOutputStream(
				new FileOutputStream(request.getOutput() + IdGenerator.generate() + ".zip"));
		return zipOutputStream;
	}

	private long compressDirectory(ZipOutputStream zipOutputStream, CompressionCommandRequest request) {
		return DIRECTORY_COMPRESSOR.compress(new CompressionRequest().setDirectoryName(request.getDirectoryName())
				.setZipOutputStream(zipOutputStream));
	}

	private long compressFile(ZipOutputStream zipOutputStream, CompressionCommandRequest request, String fileName) {
		return FILE_COMPRESSOR.compress(new CompressionRequest().setDirectoryName(request.getDirectoryName())
				.setFileName(fileName).setFileCanonicalName(request.getInput() + request.getDirectoryName() + fileName)
				.setZipOutputStream(zipOutputStream));
	}

}
