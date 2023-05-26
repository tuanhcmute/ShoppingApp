package com.example.demo.expection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.dto.ResponseObject;


@RestControllerAdvice
public class CustomExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseObject handlerNotFoundException(NotFoundException ex, WebRequest request) {
		logger.error(ex.getMessage());
		return ResponseObject.builder()
				.status("NOT_FOUND")
				.message(ex.getMessage())
				.data("")
				.build();
	}
	
	@ExceptionHandler(InternalServerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseObject handlerInternalServerErrorException(InternalServerException ex, WebRequest request) {
		logger.error(ex.getMessage());
		return ResponseObject.builder()
				.status("INTERNAL_SERVER_ERROR")
				.message(ex.getMessage())
				.data("")
				.build();
	}
}
