package com.agclab.department.service;

import com.agclab.department.entity.Department;
import com.agclab.department.exception.DepartmentNotFoundException;
import com.agclab.department.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department) {
        log.info("Inside saveDepartment method of DepartmentController");
        return departmentRepository.save(department);
    }

    public Department findDepartmentById(Long departmentId) {
        log.info("Inside findDepartmentById method of DepartmentController");
        Department department = departmentRepository.findByDepartmentId(departmentId);
        if (department == null) {
            throw new DepartmentNotFoundException();
        }
        return department;
    }
}
