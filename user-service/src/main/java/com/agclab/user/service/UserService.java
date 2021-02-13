package com.agclab.user.service;

import com.agclab.user.VO.Department;
import com.agclab.user.VO.ResponseTemplateVO;
import com.agclab.user.entity.User;
import com.agclab.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Inside saveUser of UserService");
        return userRepository.save(user);
    }

    public User findUserById(Long userId) {
        log.info("Inside findUserById of UserService");
        return userRepository.findByUserId(userId);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("Inside getUserWithDepartment of UserService");
        User user = userRepository.findByUserId(userId);
        Department department = restTemplate
                .getForObject("http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId(),
                        Department.class);
        return new ResponseTemplateVO(user, department);
    }
}
