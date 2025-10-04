package com.Fintech.User_Service.advice;

import com.Fintech.User_Service.exceptions.BadRequestException;
import com.Fintech.User_Service.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(Exception ex){
        ApiError error = ApiError
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getLocalizedMessage())
                .build();
        return buildApiResponse(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequestException(Exception ex){
        ApiError error = ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getLocalizedMessage())
                .build();
        return buildApiResponse(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAnyOtherError(Exception ex){
        ApiError error = ApiError
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getLocalizedMessage())
                .build();
        return buildApiResponse(error);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException MANVE){
//        List<String> errors = MANVE
//                .getBindingResult()
//                .getAllErrors()
//                .stream()
//                .map(objectError -> objectError.getDefaultMessage())
//                .toList();
//        ApiError error = ApiError
//                .builder()
//                .status(HttpStatus.BAD_REQUEST)
//                .message("Input Validation Failed")
//                .subErrors(errors)
//                .build();
//        return buildApiResponse(error);
//    }

    public ResponseEntity<ApiResponse<?>> buildApiResponse(ApiError error){
        return new ResponseEntity<>(new ApiResponse<>(error),error.getStatus());
    }
}
