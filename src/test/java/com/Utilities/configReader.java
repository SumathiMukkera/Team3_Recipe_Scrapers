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
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "\\src\\test\\resources\\config\\config.properties");
			//FileInputStream ip = new FileInputStream("src\\test\\resources\\config\\config.properties");
				prop.load(ip);
				
				ip.close();
			}
		catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {    
			
			e.printStackTrace();
		}      
		return prop;
	}
	
	// retriving url value from config file	
	public static String getUrl() {
		init_prop();
		String URL = prop.getProperty("URL");
		return URL;
	}

	public static String getexcelfilepath() {
		init_prop();
	  return prop.getProperty("excelFilePath");
	}	
	
	public static String getSheetName() {
		init_prop();
		return prop.getProperty("LFV_sheet");
	}


}