package com.wisely.common.httpclient.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertiesUtils {
	
	private static final Logger LOGGER = LogManager.getLogger(PropertiesUtils.class);

	public static Properties loadProperties(String propertiesName) {
		Properties prop = new Properties();
		InputStream stream = null;
		try {
			stream = PropertiesUtils.class.getResourceAsStream("/"+propertiesName) ;
			prop.load(stream);
			stream.close();
			return prop;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return null;
		}
	}
}
