package com.quangnv.uet.exception;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.quangnv.uet.dto.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> handlerResourceNoFoundException(ResourceNotFoundException exception) {
		log.error(exception.getMessage());
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date()).message(exception.getMessage())
				.build();
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
	}
}
