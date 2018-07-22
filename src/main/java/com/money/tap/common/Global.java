package com.money.tap.common;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class Global {

	private static final Logger log = LoggerFactory.getLogger(Global.class);

	private static Properties propConfig;

	public enum Currency {
		USD("USD"), EUR("EUR"), JPY("JPY"), BRL("BRL"), AUD("AUD");

		private final String value;

		public String getValue() {
			return value;
		}

		Currency(String value) {
			this.value = value;
		}
	}

	public static final class HeaderType {
		public static final String CONTENT_TYPE = "Content-Type";
	}

	public static void loadClass() {
		log.info("**********Loading all Properties ***********");
	}

	public static String TIMER;
	public static String WEB_URL;

	/**
	 * static block called at class loading time
	 */
	static {
		try {
			propConfig = PropertiesLoaderUtils.loadAllProperties("application.properties");

			TIMER = propConfig.getProperty("cron.timer");
			WEB_URL = propConfig.getProperty("web.url");

		} catch (IOException e) {
			log.error("Error in loading application.properties file", e);
		}
	}

}
