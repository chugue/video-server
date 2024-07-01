package com.project.videodemo.error;


import com.project.videodemo._core.ApiUtil;
import com.project.videodemo.error.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiUtil<String> handleRuntimeException(RuntimeException ex) {
        return new ApiUtil<>(ex.getMessage());
    }

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> ex400(Exception400 e){
        log.warn("400 : " + e.getMessage());
        ApiUtil<?> apiUtil = new ApiUtil<>(400, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> ex401(Exception401 e, HttpServletRequest request){
        log.warn("401 : " + e.getMessage());
        log.warn("IP : " + request.getRemoteAddr());
        log.warn("Agent : " + request.getHeader("User-Agent"));
        ApiUtil<?> apiUtil = new ApiUtil<>(401, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> ex403(Exception403 e){
        log.warn("403 : " + e.getMessage());

        ApiUtil<?> apiUtil = new ApiUtil<>(403, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> ex404(Exception404 e){
        log.warn("404 : " + e.getMessage());

        ApiUtil<?> apiUtil = new ApiUtil<>(404, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> ex500(Exception500 e){
        log.error("500 : " + e.getMessage());
        ApiUtil<?> apiUtil = new ApiUtil<>(500, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> unknownServerError(Exception500 e, HttpServletRequest request){
        request.setAttribute("msg", e.getMessage());
        log.error("500 : " + e.getMessage());
        ApiUtil<?> apiUtil = new ApiUtil(500, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
