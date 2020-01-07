package com.project.decompression.zip.directory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.project.decompression.Decompressor;

/*
 * De-compresses all the directories in the zip files present in the input directory.
 * The directories are sorted in lexicographically, to create parent directories before creating child directories
 * The decompressed directories are written to the output directory
 */
public class DirectoryDecompressionServiceImpl implements Decompressor {

	@Override
	public void decompress(String input, String output) {
		List<String> directories = new ArrayList<>();
		try {
			for (File file : new File(input).listFiles()) {
				String zipFile = input + file.getName();
				if (!zipFile.endsWith(".zip"))
					continue;
				ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
				directories.addAll(getDirectories(zipInputStream, output));
				zipInputStream.close();
			}
			Collections.sort(directories);
			for (String dir : directories) {
				new File(dir).mkdir();
			}
		} catch (Exception e) {
			System.out.println("Directory Decompression Failed with the exception: " + e.getMessage());
		}
	}

	private List<String> getDirectories(ZipInputStream zipInputStream, String output) throws IOException {
		List<String> directories = new ArrayList<>();
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		while (zipEntry != null) {
			if (zipEntry.isDirectory())
				directories.add(output + zipEntry.getName());
			zipEntry = zipInputStream.getNextEntry();
		}
		zipInputStream.closeEntry();
		return directories;
	}

}
