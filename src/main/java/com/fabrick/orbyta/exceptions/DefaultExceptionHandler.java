package com.fabrick.orbyta.exceptions;


import com.fabrick.orbyta.dto.FabrickGeneralDTO;
import com.fabrick.orbyta.pojo.FabricError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

@ControllerAdvice
public class DefaultExceptionHandler {

    @Autowired
    ObjectMapper mapper;

    @ExceptionHandler
    public ResponseEntity<FabricError> handleException(NotFoundException nfe) {
        FabricError errorResponse = new FabricError(String.valueOf(HttpStatus.NOT_FOUND.value()), nfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<FabricError> handleException(BadRequestException bre) {
        FabricError errorResponse = new FabricError(String.valueOf(HttpStatus.BAD_REQUEST.value()), bre.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<FabricError> handleException(GenericException ge) {
        FabricError errorResponse = new FabricError(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ge.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<FabricError> handleException(HttpStatusCodeException hsce) {
        try {
            String message = hsce.getResponseBodyAsString();
            FabrickGeneralDTO fabrickGeneralDTO = mapper.readValue(message, FabrickGeneralDTO.class);
            return new ResponseEntity<>(fabrickGeneralDTO.getErrors().get(0), hsce.getStatusCode());
        } catch (Exception e) {
            throw new GenericException(hsce);
        }
    }

}