package com.agclab.department.service;

import com.agclab.department.entity.Department;
import com.agclab.department.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    private static final Long DEPARTMENT_ID = 1L;
    private static final String DEPARTMENT_NAME = "name";
    private static final String DEPARTMENT_ADDRESS = "address";
    private static final String DEPARTMENT_CODE =  "code";

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void saveDepartment() {
        Department departmentToSave = aDepartment();
        Department expectedDepartment = aDepartment();
        given(departmentRepository.save(departmentToSave)).willReturn(expectedDepartment);

        Department result = departmentService.saveDepartment(departmentToSave);

        assertThat(result.getDepartmentName()).isEqualTo(DEPARTMENT_NAME);
        assertThat(result.getDepartmentAddress()).isEqualTo(DEPARTMENT_ADDRESS);
        assertThat(result.getDepartmentCode()).isEqualTo(DEPARTMENT_CODE);
    }

    @Test
    void findDepartmentById() {
        given(departmentRepository.findByDepartmentId(DEPARTMENT_ID)).willReturn(aDepartment());

        Department department = departmentService.findDepartmentById(DEPARTMENT_ID);

        assertThat(department.getDepartmentName()).isEqualTo(DEPARTMENT_NAME);
        assertThat(department.getDepartmentAddress()).isEqualTo(DEPARTMENT_ADDRESS);
        assertThat(department.getDepartmentCode()).isEqualTo(DEPARTMENT_CODE);
    }

    private Department aDepartment() {
        return new Department(DEPARTMENT_ID, DEPARTMENT_NAME, DEPARTMENT_ADDRESS, DEPARTMENT_CODE);
    }
}