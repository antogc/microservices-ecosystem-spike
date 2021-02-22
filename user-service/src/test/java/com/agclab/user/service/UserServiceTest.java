package com.agclab.user.service;

import com.agclab.user.VO.Department;
import com.agclab.user.VO.ResponseTemplateVO;
import com.agclab.user.entity.User;
import com.agclab.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Long USER_ID = 1L;
    private static final Long DEPARTMENT_ID = 1L;
    public static final String USER_NAME = "Antuan";
    public static final String USER_LAST_NAME = "Huever";
    public static final String USER_EMAIL = "antuan.huever@gmail.com";
    private static final String DEPARTMENT_NAME = "name";
    private static final String DEPARTMENT_ADDRESS = "address";
    private static final String DEPARTMENT_CODE =  "code";

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserService userService;

    @Test
    void saveUser() {
        given(userRepository.save(isA(User.class))).willReturn(anUser());

        User result = userService.saveUser(anUser());

        assertThat(result.getFirstName()).isEqualTo(USER_NAME);
        assertThat(result.getLastName()).isEqualTo(USER_LAST_NAME);
        assertThat(result.getEmail()).isEqualTo(USER_EMAIL);
    }

    @Test
    void getUserWithDepartment() {
        given(userRepository.findByUserId(anyLong())).willReturn(anUser());
        given(restTemplate.getForObject(anyString(), eq(Department.class))).willReturn(aDepartment());

        ResponseTemplateVO vo = userService.getUserWithDepartment(1L);

        assertThat(vo.getUser().getFirstName()).isEqualTo(USER_NAME);
        assertThat(vo.getUser().getLastName()).isEqualTo(USER_LAST_NAME);
        assertThat(vo.getUser().getEmail()).isEqualTo(USER_EMAIL);
        assertThat(vo.getDepartment().getName()).isEqualTo(DEPARTMENT_NAME);
        assertThat(vo.getDepartment().getAddress()).isEqualTo(DEPARTMENT_ADDRESS);
        assertThat(vo.getDepartment().getCode()).isEqualTo(DEPARTMENT_CODE);
    }

    private ResponseTemplateVO aVO() {
        return new ResponseTemplateVO(anUser(), aDepartment());
    }

    private User anUser() {
        return new User(USER_ID, USER_NAME, USER_LAST_NAME, USER_EMAIL, DEPARTMENT_ID);
    }

    private Department aDepartment() {
        return new Department(DEPARTMENT_ID, DEPARTMENT_NAME, DEPARTMENT_ADDRESS, DEPARTMENT_CODE);
    }
}