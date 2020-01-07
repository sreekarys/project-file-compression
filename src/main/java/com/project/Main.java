package com.project;

import java.util.Scanner;

import com.project.exceptions.InvalidRequestException;
import com.project.util.PropertyUtil;

public class Main {

	public static void main(String[] args) {
		PropertyUtil.loadSystemProps();
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println(
					"Enter 1 for Compression, 2 for Decompression and any other choice to quit the program execution");
			int choice = in.nextInt();
			Orchestrator orchestrator = new Orchestrator();
			switch (choice) {
			case 1:
				System.out.println("Enter the input directory: ");
				String input = addSuffix(in.next());
				System.out.println("Enter the output directory: ");
				String output = addSuffix(in.next());
				System.out.println("Enter the maximum file size in MB: ");
				long maxSize = in.nextLong() * 1024 * 1024;
				try {
					orchestrator.compress(input, output, maxSize);
				} catch (InvalidRequestException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				System.out.println("Enter the input directory: ");
				input = addSuffix(in.next());
				System.out.println("Enter the output directory: ");
				output = addSuffix(in.next());
				try {
					orchestrator.decompress(input, output);
				} catch (InvalidRequestException e) {
					System.out.println(e.getMessage());
				}
				break;
			default:
				System.out.println("Program terminated!!!");
				in.close();
				return;
			}
		}
	}

	private static String addSuffix(String directory) {
		if (!directory.endsWith("/")) {
			directory = directory + "/";
		}
		return directory;
	}

}
