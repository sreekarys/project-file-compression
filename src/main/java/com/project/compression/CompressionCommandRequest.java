package com.project.compression;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CompressionCommandRequest {

	private String directoryName;
	private List<String> files;
	private String input;
	private String output;
	private long maxSize;
	private CountDownLatch latch;

	public String getDirectoryName() {
		return directoryName;
	}

	public CompressionCommandRequest setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
		return this;
	}

	public List<String> getFiles() {
		return files;
	}

	public CompressionCommandRequest setFiles(List<String> files) {
		this.files = files;
		return this;
	}

	public String getInput() {
		return input;
	}

	public CompressionCommandRequest setInput(String input) {
		this.input = input;
		return this;
	}

	public String getOutput() {
		return output;
	}

	public CompressionCommandRequest setOutput(String output) {
		this.output = output;
		return this;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public CompressionCommandRequest setMaxSize(long maxSize) {
		this.maxSize = maxSize;
		return this;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public CompressionCommandRequest setLatch(CountDownLatch latch) {
		this.latch = latch;
		return this;
	}

	@Override
	public String toString() {
		return "CompressionCommandRequest [directoryName=" + directoryName + ", files=" + files + ", input=" + input
				+ ", output=" + output + ", maxSize=" + maxSize + ", latch=" + latch + "]";
	}

}
