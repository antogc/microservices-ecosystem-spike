package com.agclab.department;

import com.agclab.department.entity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTest {
    private static final Long DEPARTMENT_ID = 1L;
    private static final String DEPARTMENT_NAME = "name";
    private static final String DEPARTMENT_ADDRESS = "address";
    private static final String DEPARTMENT_CODE =  "code";

    @LocalServerPort
    int randomServerPort;

    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void integrationTest(){
        final String postUrl = String.format("http://localhost:%d/departments/", randomServerPort);
        ResponseEntity<String> postResponse =  restTemplate.postForEntity(postUrl, aDepartment(), String.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        String getUrl = String.format("http://localhost:%d/departments/%d", randomServerPort, DEPARTMENT_ID);
        ResponseEntity<Department> response = restTemplate.getForEntity(getUrl, Department.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo(DEPARTMENT_NAME);
        assertThat(response.getBody().getAddress()).isEqualTo(DEPARTMENT_ADDRESS);
        assertThat(response.getBody().getCode()).isEqualTo(DEPARTMENT_CODE);
    }

    private Department aDepartment() {
        return new Department(DEPARTMENT_ID, DEPARTMENT_NAME, DEPARTMENT_ADDRESS, DEPARTMENT_CODE);
    }
}
