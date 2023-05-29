package com.example.demo.util;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(IdUtil.class);

	public static String generatedId() {
		String uniqueID = UUID.randomUUID().toString().replace("-", "");
		LOGGER.info("Id generated: " + uniqueID);
		return uniqueID;
	}
}
