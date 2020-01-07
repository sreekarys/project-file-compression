package com.project;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.project.compression.CompressionCommandRequest;
import com.project.compression.zip.file.CompressionCommand;
import com.project.decompression.Decompressor;
import com.project.decompression.zip.directory.DirectoryDecompressionServiceImpl;
import com.project.decompression.zip.file.FileDecompressionServiceImpl;
import com.project.exceptions.InvalidRequestException;
import com.project.util.DirectoryStructureBuilder;
import com.project.util.InputValidator;

public class Orchestrator {

	private static final ExecutorService EXECUTOR = Executors
			.newFixedThreadPool(Integer.parseInt(System.getProperty("thread.pool.size")));
	private static final Decompressor FILE_DECOMPRESSOR = new FileDecompressionServiceImpl();
	private static final Decompressor DIRECTORY_DECOMPRESSOR = new DirectoryDecompressionServiceImpl();
	private static final InputValidator VALIDATOR = new InputValidator();

	public void compress(String input, String output, long maxSize) throws InvalidRequestException {
		validate(input, output);
		Map<String, List<String>> directoryStructure = DirectoryStructureBuilder.buildDirectoryStructure(input);
		CountDownLatch latch = new CountDownLatch(directoryStructure.size());
		for (Entry<String, List<String>> entry : directoryStructure.entrySet()) {
			EXECUTOR.execute(new CompressionCommand(new CompressionCommandRequest().setDirectoryName(entry.getKey())
					.setFiles(entry.getValue()).setMaxSize(maxSize).setInput(input).setOutput(output).setLatch(latch)));
		}
		wait(latch);
	}

	public void decompress(String input, String output) throws InvalidRequestException {
		validate(input, output);
		DIRECTORY_DECOMPRESSOR.decompress(input, output);
		FILE_DECOMPRESSOR.decompress(input, output);
	}

	private void wait(CountDownLatch latch) {
		try {
			latch.await();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	private void validate(String input, String output) throws InvalidRequestException {
		VALIDATOR.validateInput(input);
		VALIDATOR.validateOutput(output);
	}

	public static ExecutorService getExecuctor() {
		return EXECUTOR;
	}
}
