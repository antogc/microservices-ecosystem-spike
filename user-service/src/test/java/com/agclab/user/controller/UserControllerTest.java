package com.agclab.user.controller;

import com.agclab.user.VO.Department;
import com.agclab.user.VO.ResponseTemplateVO;
import com.agclab.user.entity.User;
import com.agclab.user.service.UserService;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final Long USER_ID = 1L;
    private static final Long DEPARTMENT_ID = 1L;
    public static final String USER_NAME = "Antuan";
    public static final String USER_LAST_NAME = "Huever";
    public static final String USER_EMAIL = "antuan.huever@gmail.com";
    private static final String DEPARTMENT_NAME = "name";
    private static final String DEPARTMENT_ADDRESS = "address";
    private static final String DEPARTMENT_CODE =  "code";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    void saveUser() throws Exception {
        User user = anUser();
        given(userService.saveUser(isA(User.class))).willReturn(user);

        mockMvc.perform(
                MockMvcRequestBuilders
                    .post("/users/")
                    .content(objectMapper.writeValueAsString(user))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("firstName").value(USER_NAME))
                .andExpect(jsonPath("lastName").value(USER_LAST_NAME))
                .andExpect(jsonPath("email").value(USER_EMAIL));
    }

    @Test
    void getUserWithDepartment() throws Exception {
        ResponseTemplateVO vo = aVO();
        given(userService.getUserWithDepartment(anyLong())).willReturn(vo);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("user.firstName").value(USER_NAME))
                .andExpect(jsonPath("user.lastName").value(USER_LAST_NAME))
                .andExpect(jsonPath("user.email").value(USER_EMAIL))
                .andExpect(jsonPath("department.name").value(DEPARTMENT_NAME))
                .andExpect(jsonPath("department.address").value(DEPARTMENT_ADDRESS))
                .andExpect(jsonPath("department.code").value(DEPARTMENT_CODE));
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