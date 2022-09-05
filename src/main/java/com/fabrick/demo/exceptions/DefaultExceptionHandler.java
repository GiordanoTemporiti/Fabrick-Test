package com.fabrick.demo.exceptions;


import com.fabrick.demo.dto.FabrickGeneralDTO;
import com.fabrick.demo.exceptions.response.DefaultErrorResponse;
import com.fabrick.demo.pojo.FabricError;
import com.fabrick.demo.utilities.Cleaner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class DefaultExceptionHandler {

    @Autowired
    ObjectMapper mapper;

    @ExceptionHandler
    public ResponseEntity<DefaultErrorResponse> handleException(NotFoundException nfe) {
        DefaultErrorResponse errorResponse = new DefaultErrorResponse(HttpStatus.NOT_FOUND, nfe.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DefaultErrorResponse> handleException(BadRequestException bre) {
        DefaultErrorResponse errorResponse = new DefaultErrorResponse(HttpStatus.BAD_REQUEST, bre.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<DefaultErrorResponse> handleException(GenericException ge) {
        DefaultErrorResponse errorResponse = new DefaultErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ge.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<FabricError> handleException(HttpServerErrorException hsee) {
        try {
            String message = Cleaner.cleanRestTemplateError(hsee);
            FabrickGeneralDTO fabrickGeneralDTO = mapper.readValue(message, FabrickGeneralDTO.class);
            return new ResponseEntity<>(fabrickGeneralDTO.getErrors().get(0), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new GenericException(e);
        }
    }

}