package com.warp.unicorn.http.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Map;

public interface RestGate {

    <T> T call(Class<T> answerClass,
               String url,
               String method,
               Map<String, Object> queryParams,
               Object body,
               HttpMethod action,
               HttpHeaders headers,
               MediaType contentType);

    <T> T call(ParameterizedTypeReference<T> typeReference,
               String url,
               String method,
               Map<String, Object> queryParams,
               Object body,
               HttpMethod action,
               HttpHeaders headers,
               MediaType contentType);

}
