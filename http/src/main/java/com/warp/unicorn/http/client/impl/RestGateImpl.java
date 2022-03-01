package com.warp.unicorn.http.client.impl;

import com.warp.unicorn.http.client.RestGate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RestGateImpl implements RestGate {

    private static final ConversionService conversionService = ApplicationConversionService.getSharedInstance();
    private final RestTemplate restTemplate;

    @Override
    public <T> T call(Class<T> answerClass,
                      String url,
                      String method,
                      Map<String, Object> queryParams,
                      Object body,
                      HttpMethod action,
                      HttpHeaders headers,
                      MediaType contentType) {
        ResponseEntity<T> exchange = restTemplate.exchange(makeCallUrl(url, method, queryParams),
                action,
                request(headers, contentType, body),
                answerClass);
        return exchange.getBody();
    }

    @Override
    public <T> T call(ParameterizedTypeReference<T> typeReference,
                      String url,
                      String method,
                      Map<String, Object> queryParams,
                      Object body,
                      HttpMethod action,
                      HttpHeaders headers,
                      MediaType contentType) {
        ResponseEntity<T> exchange = restTemplate.exchange(makeCallUrl(url, method, queryParams),
                action,
                request(headers, contentType, body),
                typeReference);
        return exchange.getBody();
    }

    private HttpEntity<Object> request(HttpHeaders headers, MediaType contentType, Object body) {
        HttpHeaders callHeaders;
        if (headers == null)
            callHeaders = new HttpHeaders();
        else
            callHeaders = new HttpHeaders(headers);
        callHeaders.setContentType(contentType);
        return new HttpEntity<>(body, callHeaders);
    }

    private String makeCallUrl(String url, String method, Map<String, Object> queryParams) {
        return UriComponentsBuilder.fromHttpUrl(url).path(method).queryParams(setQueryParams(queryParams)).toUriString();
    }

    private MultiValueMap<String, String> setQueryParams(Map<String, Object> queryParams) {
        MultiValueMap<String, String> qp = new LinkedMultiValueMap<>();
        if (queryParams != null) {
            for (Map.Entry<String, Object> e : queryParams.entrySet()) {
                if (e.getKey() != null && e.getValue() != null) {
                    if (e.getValue() instanceof List) {
                        List<?> valueList = (List<?>) e.getValue();
                        Stream<String> values = valueList.stream().map(v -> conversionService.convert(v, String.class));
                        qp.addAll(e.getKey(), values.collect(Collectors.toList()));
                    } else {
                        qp.add(e.getKey(), conversionService.convert(e.getValue(), String.class));
                    }
                }
            }
        }
        return qp;
    }
}
