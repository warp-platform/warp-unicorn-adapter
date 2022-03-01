package com.warp.unicorn.utils.builder;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static com.warp.unicorn.utils.constant.Constants.Header.REQUEST_ID;

public class ResponseBuilder {

    public static <T> ResponseEntity<T> ok(T data) {
        HttpHeaders headers = defaultHeaders();
        return ResponseEntity.ok().headers(headers).body(data);
    }

    public static ResponseEntity<Void> created(Long id) {
        HttpHeaders headers = defaultHeaders();
        return ResponseEntity.created(URI.create(String.valueOf(id))).headers(headers).build();
    }

    public static <T> ResponseEntity<T> status(HttpStatus code, T data) {
        HttpHeaders headers = defaultHeaders();
        return ResponseEntity.status(code).headers(headers).body(data);
    }

    public static HttpHeaders defaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders() ;
        httpHeaders.add(REQUEST_ID, ThreadContext.get(REQUEST_ID));
        return httpHeaders;
    }

}
