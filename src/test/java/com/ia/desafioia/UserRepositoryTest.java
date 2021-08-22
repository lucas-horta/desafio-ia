package com.ia.desafioia;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.desafioia.configurations.TestRedisConfiguration;
import com.ia.desafioia.dto.UserDto;
import com.ia.desafioia.models.User;
import com.ia.desafioia.repositories.interfaces.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TestRedisConfiguration.class)
@AutoConfigureMockMvc

public class UserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldSaveUser_whenValidForm() throws Exception{
        UserDto user = testUser();
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isCreated());
    }
    @Test
    public void shouldNotSaveUser_whenInvalidForm() throws Exception{
        UserDto user = testUser();
        user.setLogin("");
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldNotSaveUser_whenRepeatLogin() throws Exception{
        UserDto user = testUser();
        user.setLogin("repeat");
        userRepository.save(new User(user));
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldNotSaveUser_whenRepeatEmail() throws Exception{
        UserDto user = testUser();
        user.setEmail("repeat@mail.com");
        userRepository.save(new User(user));
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @WithMockUser(username = "john")
    @Test
    public void givenUsers_whenGetUsers_thenShowList() throws Exception {

        createTestUser();
        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("testName")));
    }

    @WithMockUser(username = "john")
    @Test
    public void givenUser_whenGetUser_thenShowUser() throws Exception {

        createTestUser();
        mvc.perform(get("/user/testId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("testName")))
                .andExpect(jsonPath("$.created_date", is("2021-08-20T12:00:00.000+00:00")));
    }

    @WithMockUser(username = "john")
    @Test
    public void givenNoUsers_whenGetUsers_thenShowEmptyArray() throws Exception {

        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"));
    }

    @Test
    public void unauthenticatedCantAccess() throws Exception{
        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    private void createTestUser(){
        User user = new User();
        user.setId("testId");
        user.setName("testName");
        user.setLogin("testLogin");
        user.setPassword("testPassword");
        user.setEmail("testEmail");
        user.setGitHubProfile("lucas-horta");
        user.setCreatedDate(new Date(1629460800000L));
        userRepository.save(user);
    }

    private UserDto testUser(){
        return new UserDto("testName", "testLogin", "testPassword", "testGitHubProfile", "testEmail");
    }

    static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}