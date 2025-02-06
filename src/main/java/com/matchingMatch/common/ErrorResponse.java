package com.matchingMatch.common;

import java.time.LocalDateTime;

public class ErrorResponse {
	private String message;
	private int statusCode;
	private LocalDateTime timestamp;

	public ErrorResponse(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
		this.timestamp = LocalDateTime.now();
	}
}
