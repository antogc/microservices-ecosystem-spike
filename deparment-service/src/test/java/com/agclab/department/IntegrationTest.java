package com.agclab.department;

import com.agclab.department.entity.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTest {
    private static final Long DEPARTMENT_ID = 1L;
    private static final String DEPARTMENT_NAME = "name";
    private static final String DEPARTMENT_ADDRESS = "address";
    private static final String DEPARTMENT_CODE =  "code";

    @LocalServerPort
    int randomServerPort;

    @TestConfiguration
    static class DepartmentServiceTestConfiguration {
        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void integrationTest(){
        final String postUrl = String.format("http://localhost:%d/departments/", randomServerPort);
        ResponseEntity<String> postResponse =  restTemplate.postForEntity(postUrl, aDepartment(), String.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        String getUrl = String.format("http://localhost:%d/departments/%d", randomServerPort, DEPARTMENT_ID);
        ResponseEntity<Department> response = restTemplate.getForEntity(getUrl, Department.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getDepartmentName()).isEqualTo(DEPARTMENT_NAME);
        assertThat(response.getBody().getDepartmentAddress()).isEqualTo(DEPARTMENT_ADDRESS);
        assertThat(response.getBody().getDepartmentCode()).isEqualTo(DEPARTMENT_CODE);
    }

    private Department aDepartment() {
        return new Department(DEPARTMENT_ID, DEPARTMENT_NAME, DEPARTMENT_ADDRESS, DEPARTMENT_CODE);
    }
}
