package com.warp.unicorn.http.template;

import com.google.common.io.ByteStreams;
import com.warp.unicorn.utils.exception.WarpErrors;
import com.warp.unicorn.utils.exception.WarpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Configuration
@Slf4j
public class HttpClientConfiguration {

    @Value("${application.http.timeout:50}")
    private String timeout;
    @Value("${application.http.poolsize:5}")
    private String poolsize;
    @Value("${application.ssl.enable:false}")
    private Boolean ssl;
    @Value("${application.ssl.keypwd:0}")
    private String keypwd;
    @Value("${application.ssl.keystore:0}")
    private String keystore;
    @Value("${application.ssl.keystorepwd:0}")
    private String keystorepwd;
    @Value("${application.ssl.truststore:0}")
    private String truststore;
    @Value("${application.ssl.truststorepwd:0}")
    private String truststorepwd;
    @Value("${application.proxy.enable:false}")
    private Boolean proxy;
    @Value("${application.proxy.host:localhost}")
    private String proxyhost;
    @Value("${application.proxy.port:8080}")
    private String proxyport;

    private final LogHttpInterceptor logHttpInterceptor;

    public HttpClientConfiguration(
            LogHttpInterceptor logHttpInterceptor) {
        this.logHttpInterceptor = logHttpInterceptor;
    }



    @PostConstruct
    public void doPostConstruct(){
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
    }

    @Bean
    RestTemplate dboRestTemplate(RestTemplateBuilder builder) {
        log.info("Start initialize Rest Template");
        return createRestTemplate(builder);
    }

    private Duration getDuration(String value){
        return Duration.ofSeconds(Long.parseLong(value.replaceAll("s","")));
    }

    private RestTemplate createRestTemplate(RestTemplateBuilder builder)
    {
        ClientHttpRequestFactory requestFactory = createRequestFactory();

        builder = builder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(requestFactory))
                .setConnectTimeout(getDuration(timeout))
                .setReadTimeout(getDuration(timeout));


        RestTemplate restTemplate = builder.errorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(@NonNull ClientHttpResponse clientHttpResponse) throws IOException {
                return !clientHttpResponse.getStatusCode().is2xxSuccessful();
            }

            @Override
            public void handleError(@NonNull ClientHttpResponse clientHttpResponse) throws IOException {
                String responseString = new String(ByteStreams.toByteArray(clientHttpResponse.getBody()), StandardCharsets.UTF_8);
                log.info("REST CALL ERROR: {}", responseString);
                throw new WarpException(WarpErrors.INTERNAL_ERROR);
            }
        }).build();
        restTemplate.getInterceptors().add(logHttpInterceptor);
        return restTemplate;
    }

    private ClientHttpRequestFactory createRequestFactory() {

        int poolSize = Integer.parseInt(poolsize);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create()
                .setMaxConnTotal(poolSize)
                .setMaxConnTotal(poolSize)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);

        if (Boolean.TRUE.equals(ssl)) {
            clientBuilder
                    .setSSLContext(createSSLContext());
        } else {
            clientBuilder.setSSLContext(createInsecureSSLContext());
        }

        if (Boolean.TRUE.equals(proxy)){
            clientBuilder.setProxy(new HttpHost(proxyhost, Integer.parseInt(proxyport)));
        }

        return new HttpComponentsClientHttpRequestFactory(clientBuilder.build());
    }

    private SSLContext createSSLContext() {
        log.info("*** SSL initialize");
        try {
            return SSLContextBuilder.create()
                    .loadKeyMaterial(new File(keystore),
                            keystorepwd.toCharArray(),
                            keypwd.toCharArray())
                    .loadTrustMaterial(new File(truststore),
                            truststorepwd.toCharArray())
                    .build();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to initialize SSL context", ex);
        }
    }

    private SSLContext createInsecureSSLContext() {
        log.info("*** Insecure SSL initialize");
        try {
            return SSLContextBuilder.create()
                    .loadTrustMaterial(null, TrustAllStrategy.INSTANCE)
                    .build();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to initialize SSL context", ex);
        }
    }

}

