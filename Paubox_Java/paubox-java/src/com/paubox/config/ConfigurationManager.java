package com.paubox.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.paubox.common.Constants;

public class ConfigurationManager {

	private static Properties properties;
	private static void readProperties(String fileAndPath){
		try {
			File file = new File(fileAndPath);
			FileInputStream fileInput = new FileInputStream(file);
			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			
			setConstants(properties);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void setConstants(Properties properties){
		
		Constants.API_KEY=properties.getProperty("APIKEY");
		Constants.API_USER=properties.getProperty("APIUSER");
		
	}

	public static void getProperties(String fileAndPath){
		readProperties(fileAndPath);
	}
	public static String getProperty(String propertyName){
		return properties.getProperty(propertyName);
	}

}
