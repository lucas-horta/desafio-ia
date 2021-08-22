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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TestRedisConfiguration.class)
@AutoConfigureMockMvc

public class ApplicationUnitTests {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    private void createTestUser() {
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

    private UserDto testUserDto() {
        return new UserDto("testName", "testLogin", "testPassword", "testGitHubProfile", "testEmail");
    }

    @Test
    public void unauthenticatedCantAccess() throws Exception {
        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldSaveUser_whenValidForm() throws Exception {
        UserDto user = testUserDto();
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotSaveUser_whenInvalidForm() throws Exception {
        UserDto user = testUserDto();
        user.setLogin("");
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldNotSaveUser_whenRepeatLogin() throws Exception {
        UserDto user = testUserDto();
        user.setLogin("repeat");
        userRepository.save(new User(user));
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldNotSaveUser_whenRepeatEmail() throws Exception {
        UserDto user = testUserDto();
        user.setEmail("repeat@mail.com");
        userRepository.save(new User(user));
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username = "testLogin")
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

    @Test
    @WithMockUser(username = "testLogin")
    public void givenUsers_whenGetUsers_thenShowList() throws Exception {
        createTestUser();
        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("testName")));
    }

    @Test
    @WithMockUser(username = "testLogin")
    public void givenNoUsers_whenGetUsers_thenShowEmptyArray() throws Exception {
        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"));
    }

    @Test
    @WithMockUser(username = "testLogin")
    public void givenUser_whenDeleteUser_thenReturnTrue() throws Exception {
        createTestUser();
        mvc.perform(delete("/user/testId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testLogin")
    public void givenInvalidUser_whenDeleteUser_thenReturnFalse() throws Exception {
        mvc.perform(delete("/user/invalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testLogin")
    public void givenValidGitHubProfile_thenReturnGitHubProfileInformation() throws Exception {
        createTestUser();
        mvc.perform(get("/user/testId/github"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("87447977")));
    }

    @Test
    @WithMockUser(username = "testLogin")
    public void givenInvalidUser_thenReturnEmptyGitHubProfile() throws Exception {
        mvc.perform(get("/user/invalidId/github"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.repositories", hasSize(0)));
    }
}
