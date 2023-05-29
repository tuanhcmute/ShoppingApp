package com.example.demo.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.dto.ResponseObject;
import com.example.demo.util.HttpStatusUtil;


@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseObject handlerNotFoundException(NotFoundException ex, WebRequest request) {
		return ResponseObject.builder()
				.status(HttpStatus.NOT_FOUND.toString())
				.message(ex.getMessage())
				.data("")
				.build();
	}
	
	@ExceptionHandler(InternalServerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseObject handlerInternalServerErrorException(InternalServerException ex, WebRequest request) {
		return ResponseObject.builder()
				.status(HttpStatusUtil.INTERNAL_SERVER_ERROR.toString())
				.message(ex.getMessage())
				.data("")
				.build();
	}
}
