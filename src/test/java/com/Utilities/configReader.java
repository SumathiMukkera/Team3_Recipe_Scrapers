package com.Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class configReader {

	public static Properties prop;

	public static Properties init_prop() {

		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\config\\config.properties");
			prop.load(ip);

			ip.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return prop;
	}

	public static String getKeyValues(String key) {
		init_prop();
		String value = prop.getProperty(key);
		if (value != null)
			return value;
		else
			throw new RuntimeException("Key" + key + "not found in config.properties file.");

	}

}