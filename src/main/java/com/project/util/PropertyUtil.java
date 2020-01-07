package com.project.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
	public static void loadSystemProps() {
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(new File("./src/main/resources/app.properties")));
			properties.stringPropertyNames()
					.forEach(property -> System.setProperty(property, (String) properties.get(property)));
		} catch (IOException e) {
			System.out.println("Error reading properties!!!");
		}
	}
}
