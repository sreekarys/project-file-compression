package com.project.util;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

/*
 * Builds the directory structure from the given input directory. returns a Map
 * with key as directory name and value as list of files in that directory
 */
public class DirectoryStructureBuilder {

	public static Map<String, List<String>> buildDirectoryStructure(String input) {
		String inputAbsolutePath = new File(input).getAbsolutePath();
		Map<String, List<String>> directoryStructure = new HashMap<>();

		Queue<String> directoryQueue = new LinkedList<>();
		directoryQueue.add("");
		while (!directoryQueue.isEmpty()) {
			String directoryName = directoryQueue.poll();
			File currentDirectory = new File(input + directoryName);
			List<String> files = currentDirectory.listFiles() != null ? Arrays.stream(currentDirectory.listFiles())
					.filter(file -> file.isFile() && !file.isHidden()).map(File::getName).collect(Collectors.toList())
					: Collections.EMPTY_LIST;
			directoryStructure.put(directoryName + "/", files);
			if (currentDirectory.listFiles() != null) {
				Arrays.stream(currentDirectory.listFiles()).filter(file -> file.isDirectory() && !file.isHidden())
						.map(File::getAbsolutePath).forEach(directory -> {
							directoryQueue.add(directory.substring(inputAbsolutePath.length()));
						});

			}
		}
		return directoryStructure;
	}
}
