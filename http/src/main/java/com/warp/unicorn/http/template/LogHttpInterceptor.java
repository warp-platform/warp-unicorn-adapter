package com.warp.unicorn.http.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LogHttpInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String requestString = new String(body, StandardCharsets.UTF_8);
        log.debug("sending request for url {}: {}, headers {}", request.getURI(), requestString, request.getHeaders().entrySet());

        ClientHttpResponse response = execution.execute(request, body);

        MediaType contentType = response.getHeaders().getContentType();
        Charset charset = (contentType != null && contentType.getCharset() != null) ? contentType.getCharset() : StandardCharsets.UTF_8;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), charset))) {
            String responseString = bufferedReader.lines().collect(Collectors.joining("\n"));
            log.debug("received response with intStatus {}: {}", response.getStatusCode().value(), responseString);
        }

        if (!response.getStatusCode().is2xxSuccessful())
            log.error("CONNECTION ERROR. Code: " + response.getStatusCode().value() + " ; Error text: " + response.getStatusText());

        log.debug("received headers [{}]", response.getHeaders().entrySet());

        return response;
    }

}
