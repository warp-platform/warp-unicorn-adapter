package com.warp.unicorn.adapter.validator;

import com.warp.unicorn.utils.builder.ResponseBuilder;
import com.warp.unicorn.utils.exception.ErrorRes;
import com.warp.unicorn.utils.exception.WarpErrors;
import com.warp.unicorn.utils.exception.WarpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ValidationHandler {

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorRes> handleException(Exception ex) {
        log.error("error in service", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorRes body = new ErrorRes(1000, WarpErrors.INTERNAL_ERROR.getMessage());
        WarpErrors err = WarpErrors.MISSING_REQUIRED_PARAMETERS;
        if (ex instanceof WarpException) {
            WarpException e = (WarpException) ex;
            err = WarpErrors.getByCode(e.getCode());
            status = err.getStatus();
            body = new ErrorRes(err.getCode(), err.getMessage());
        } else if (ex instanceof MethodArgumentNotValidException || ex instanceof MissingServletRequestParameterException || ex instanceof MissingRequestHeaderException) {
            body = new ErrorRes(err.getCode(), err.getMessage());
        } else if (ex instanceof DataIntegrityViolationException) {
            err = WarpErrors.DB_ERROR;
            status = err.getStatus();
            body = new ErrorRes(err.getCode(), err.getMessage());
        }
        return ResponseBuilder.status(status, body);
    }

}
