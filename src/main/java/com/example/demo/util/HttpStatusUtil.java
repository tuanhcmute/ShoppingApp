package com.example.demo.util;

public enum HttpStatusUtil {
	// 200 --> 300
	OK(200, "OK"),
	CREATED(201, "CREATED"),
	NO_CONTENT(203, "NO_CONTENT"),
	// 400 --> 500
	BAD_REQUEST(400, "BAD_REQUEST"),
	UNAUTHORIZED(401, "UNAUTHORIZED"),
	NOT_FOUND(404, "NOT_FOUND"),
	// 500
	INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");
	
	private final int value;
    private final String description;

    HttpStatusUtil(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
