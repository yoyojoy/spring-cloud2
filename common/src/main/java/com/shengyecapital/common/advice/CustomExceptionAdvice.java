package com.shengyecapital.common.advice;

import com.shengyecapital.common.constants.ResponseCodeConstants;
import com.shengyecapital.common.constants.ResponseMsgConstants;
import com.shengyecapital.common.dto.common.CustomResponseBody;
import com.shengyecapital.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice("com.shengyecapital.business.controller")
public class CustomExceptionAdvice {

    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<CustomResponseBody> handleCustomException(Exception e) {
        log.info(String.format("handling exception: %s.", e.getClass().getName()));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");

        CustomResponseBody responseBody = new CustomResponseBody();
        ResponseEntity<CustomResponseBody> responseEntity;
        HttpStatus httpStatus = HttpStatus.OK;

        if (e instanceof CustomException) {
            log.error("handling exception", e);
            CustomException customException = (CustomException) e;

            responseBody.setCode(customException.getErrorCode());
            responseBody.setMessage(customException.getMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            log.error("handling exception", e);
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;

            String message = validException.getBindingResult().getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            responseBody.setCode(ResponseCodeConstants.BAD_REQUEST);
            responseBody.setMessage(message);
        } else if (e instanceof DataAccessException) {
            log.error("handling exception", e);
            responseBody.setCode(ResponseCodeConstants.DATABASE_ERROR);
            Throwable throwable = e.getCause();
            if (throwable instanceof SQLException) {
                responseBody.setMessage(String.format(ResponseMsgConstants.COMMON_ERROR + "(%s)", ((SQLException) throwable).getSQLState()));
            } else {
                log.error("handling exception", e);

                responseBody.setCode(ResponseCodeConstants.INTERNAL_ERROR);
                responseBody.setMessage(ResponseMsgConstants.COMMON_ERROR);
            }
        } else {
            log.error("handling exception", e);
            responseBody.setCode(ResponseCodeConstants.INTERNAL_ERROR);
            responseBody.setMessage(e.getMessage());
        }

        responseEntity = new ResponseEntity<>(responseBody, headers, httpStatus);
        return responseEntity;
    }

}
