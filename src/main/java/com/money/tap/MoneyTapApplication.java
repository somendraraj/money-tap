package com.money.tap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.money.tap.common.Global;
import com.money.tap.control.Control;

@SpringBootApplication
public class MoneyTapApplication {

	private static final Logger log = LoggerFactory.getLogger(MoneyTapApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MoneyTapApplication.class, args);

		/**
		 * load all properties
		 */
		Global.loadClass();

		/**
		 * initialize schedule job to post bit coins prices
		 */
		Control._initialize();

		log.info("+++++++++++++++++++++++++++++++++++++++++++++++++");
		log.info("++++MoneyTap Application Started Successfully++++");
		log.info("+++++++++++++++++++++++++++++++++++++++++++++++++");

	}

}
