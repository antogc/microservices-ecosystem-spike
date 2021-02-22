package com.agclab.user;

import com.agclab.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(profiles = {"test"})
public class IntegrationTest {

    public static final String USER_NAME = "Antuan";
    public static final String USER_LAST_NAME = "Huever";
    public static final String USER_EMAIL = "antuan.huever@gmail.com";

    @LocalServerPort
    int serverPort;

    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void integrationTest() throws JsonProcessingException {
        String postUrl = String.format("http://localhost:%d/users/", serverPort);
        ResponseEntity<User> postResponse = restTemplate.postForEntity(postUrl, anUser(), User.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    private User anUser() {
        return new User(1L, USER_NAME, USER_LAST_NAME, USER_EMAIL, 1L);
    }
}
