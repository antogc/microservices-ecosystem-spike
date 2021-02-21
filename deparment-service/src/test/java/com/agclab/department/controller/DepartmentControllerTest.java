package com.agclab.department.controller;

import com.agclab.department.entity.Department;
import com.agclab.department.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    private static final Long DEPARTMENT_ID = 1L;
    private static final String DEPARTMENT_NAME = "name";
    private static final String DEPARTMENT_ADDRESS = "address";
    private static final String DEPARTMENT_CODE =  "code";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean DepartmentService departmentService;

    @Test
    void saveDepartment() throws Exception {
        Department department = aDepartment();
        given(departmentService.saveDepartment(isA(Department.class))).willReturn(department);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/departments/")
                        .content(objectMapper.writeValueAsString(department))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(DEPARTMENT_NAME))
                .andExpect(jsonPath("address").value(DEPARTMENT_ADDRESS))
                .andExpect(jsonPath("code").value(DEPARTMENT_CODE));
    }

    @Test
    void findDepartmentById() throws Exception {
        given(departmentService.findDepartmentById(anyLong())).willReturn(aDepartment());

        mockMvc.perform(MockMvcRequestBuilders.get("/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(DEPARTMENT_NAME))
                .andExpect(jsonPath("address").value(DEPARTMENT_ADDRESS))
                .andExpect(jsonPath("code").value(DEPARTMENT_CODE));
    }

    private Department aDepartment() {
        return new Department(DEPARTMENT_ID, DEPARTMENT_NAME, DEPARTMENT_ADDRESS, DEPARTMENT_CODE);
    }
}