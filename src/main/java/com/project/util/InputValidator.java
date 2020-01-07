package com.project.util;

import java.io.File;

import com.project.exceptions.InvalidRequestException;

public class InputValidator {

	public void validateInput(String dir) throws InvalidRequestException {
		try {
			File file = new File(dir);
			if (!file.isDirectory())
				throw new InvalidRequestException("input", dir);
		} catch (Exception e) {
			throw new InvalidRequestException("input", dir);
		}
	}

	public void validateOutput(String dir) throws InvalidRequestException {
		try {
			File file = new File(dir);
			if (!file.isDirectory())
				throw new InvalidRequestException("output", dir);
		} catch (Exception e) {
			throw new InvalidRequestException("output", dir);
		}
	}
}
