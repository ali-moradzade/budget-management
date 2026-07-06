package project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import project.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_create_user_successfully() throws Exception {
        mockMvc.perform(post("/users")
                        .param("username","ali_" + System.nanoTime()))
                .andExpect(status().isCreated())
                .andExpect(content().string(org.hamcrest.Matchers.matchesRegex("\\d+")));
    }

    @Test
    void should_fail_when_username_is_blank() throws Exception {
        mockMvc.perform(post("/users")
                        .param("username", " "))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_fail_when_username_missing() throws Exception {
        mockMvc.perform(post("/users"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void should_fail_when_user_already_exists() throws Exception {
        String username = "ali_" + System.nanoTime();

        mockMvc.perform(post("/users")
                        .param("username",username))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/users")
                        .param("username",username))
                .andExpect(status().isNotAcceptable());
    }
}