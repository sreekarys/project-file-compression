package com.project.compression;

import java.util.zip.ZipOutputStream;

public class CompressionRequest {

	private String fileName;
	private String directoryName;
	private String fileCanonicalName;
	private ZipOutputStream zipOutputStream;

	public String getFileName() {
		return fileName;
	}

	public CompressionRequest setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public CompressionRequest setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
		return this;
	}

	public String getFileCanonicalName() {
		return fileCanonicalName;
	}

	public CompressionRequest setFileCanonicalName(String fileCanonicalName) {
		this.fileCanonicalName = fileCanonicalName;
		return this;
	}

	public ZipOutputStream getZipOutputStream() {
		return zipOutputStream;
	}

	public CompressionRequest setZipOutputStream(ZipOutputStream zipOutputStream) {
		this.zipOutputStream = zipOutputStream;
		return this;
	}

	@Override
	public String toString() {
		return "CompressionRequest [fileName=" + fileName + ", directoryName=" + directoryName + ", fileCanonicalName="
				+ fileCanonicalName + ", zipOutputStream=" + zipOutputStream + "]";
	}

}
