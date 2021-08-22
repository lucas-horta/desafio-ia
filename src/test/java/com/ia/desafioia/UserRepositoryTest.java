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
@WithMockUser(username = "john")

public class UserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldSaveUser_toRedis() throws Exception{
        UserDto user = testUser();
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotSaveUser_Repeat_Login() throws Exception{
        UserDto user = testUser();
        user.setLogin("repeat");
        userRepository.save(new User(user));
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUsers_whenGetUsers_thenStatus200() throws Exception {

        createTestUser("1", "É Amigão?");
        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("É Amigão?")))
                .andExpect(jsonPath("$[0].id", is("1")));
    }

    private void createTestUser(String name, String id){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setLogin("testLogin");
        user.setPassword("testPassword");
        user.setEmail("testEmail");
        user.setGitHubProfile("testGitHubProfile");
        user.setCreatedDate(new Date(1629460800000L));
        userRepository.save(user);
    }

    @Test
    public void testUnauthenticatedCantAccess() {

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