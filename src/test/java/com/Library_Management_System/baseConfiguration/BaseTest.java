package com.Library_Management_System.baseConfiguration;

import com.Library_Management_System.LibraryManagementSystemApplication;
import com.Library_Management_System.user.dto.LoginDto;
import com.Library_Management_System.user.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.util.Map;


@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {LibraryManagementSystemApplication.class})
public class BaseTest {

    static final String AUTHORIZATION = "Authorization";
    static final String BEARER = "Bearer ";

    protected String token;
    @LocalServerPort
    protected Integer port;
    private WebClient webClient;

    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest").withExposedPorts(27017);

    static {
        mongoDBContainer.start();
        var mappedPort = mongoDBContainer.getMappedPort(27017);
        System.setProperty("mongodb.container.port", String.valueOf(mappedPort));

    }


    @BeforeAll
    public static void addAdminUser(){

    }

    @BeforeEach
    public void setUp() {
        webClient = WebClient.builder().baseUrl("http://localhost:" + port).build();
    }


    public <T, BODY> T requestOne(String uri, HttpMethod method, Map<String, String> headers, Map<String, String> params, BODY body, Class<T> responseType) {
        WebClient.RequestBodySpec requestBodySpec = webClient
                .method(method)
                .uri(addParamsToUri(uri, params))
                .accept(MediaType.APPLICATION_JSON);

        return setBodyAndHeaders(headers, body, requestBodySpec)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }


    @SuppressWarnings("unchecked")
    public <T, W, R> R requestMany(String uri, HttpMethod method, Map<String, String> headers, Map<String, String> params, Object body, Class<T> responseType, Class<W> wrapper) {
        WebClient.RequestBodySpec uri1 = webClient
                .method(method)
                .uri(addParamsToUri(uri, params))
                .accept(MediaType.APPLICATION_JSON);

        return (R) setBodyAndHeaders(headers, body, uri1)
                .retrieve()
                .bodyToMono(ParameterizedTypeReference.forType(ResolvableType.forClassWithGenerics(wrapper, responseType).getType()))
                .block();
    }


    public WebClient.RequestBodySpec setBodyAndHeaders(Map<String, String> headers, Object body, WebClient.RequestBodySpec specs) {
        if (headers != null) specs.headers((value) -> headers.forEach(value::add));
        if (body != null) specs.body(Mono.just(body), body.getClass());

        return specs;
    }

    public String addParamsToUri(String uri, Map<String, String> params) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(uri);
        if (params != null) params.forEach(uriComponentsBuilder::queryParam);

        return uriComponentsBuilder.toUriString();
    }


    public void login(LoginDto requestBody) {
        token = BEARER + requestOne("/user/login", HttpMethod.POST, null, null, requestBody, String.class);
    }

}
