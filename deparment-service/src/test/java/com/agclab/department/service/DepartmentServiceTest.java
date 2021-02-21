package com.agclab.department.service;

import com.agclab.department.entity.Department;
import com.agclab.department.exception.DepartmentNotFoundException;
import com.agclab.department.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
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
        given(departmentRepository.save(isA(Department.class))).willReturn(aDepartment());

        Department result = departmentService.saveDepartment(aDepartment());

        assertThat(result.getName()).isEqualTo(DEPARTMENT_NAME);
        assertThat(result.getAddress()).isEqualTo(DEPARTMENT_ADDRESS);
        assertThat(result.getCode()).isEqualTo(DEPARTMENT_CODE);
    }

    @Test
    void findDepartmentById() {
        given(departmentRepository.findByDepartmentId(DEPARTMENT_ID)).willReturn(aDepartment());

        Department department = departmentService.findDepartmentById(DEPARTMENT_ID);

        assertThat(department.getName()).isEqualTo(DEPARTMENT_NAME);
        assertThat(department.getAddress()).isEqualTo(DEPARTMENT_ADDRESS);
        assertThat(department.getCode()).isEqualTo(DEPARTMENT_CODE);
    }

    @Test
    void shouldThrowException() {
        given(departmentRepository.findByDepartmentId(DEPARTMENT_ID)).willReturn(null);

        assertThrows(DepartmentNotFoundException.class,
                () -> departmentService.findDepartmentById(DEPARTMENT_ID));
    }


    private Department aDepartment() {
        return new Department(DEPARTMENT_ID, DEPARTMENT_NAME, DEPARTMENT_ADDRESS, DEPARTMENT_CODE);
    }
}